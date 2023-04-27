package net.mimieye.model.txdata.nerve.dex;

import io.nuls.base.basic.AddressTool;
import io.nuls.base.basic.NulsByteBuffer;
import io.nuls.base.basic.NulsOutputStreamBuffer;
import io.nuls.base.data.Address;
import io.nuls.base.data.BaseNulsData;
import io.nuls.base.data.NulsHash;
import io.nuls.core.crypto.HexUtil;
import io.nuls.core.exception.NulsException;
import io.nuls.core.parse.SerializeUtils;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;

/**
 * 币对交易委托挂单协议
 */
public class TradingOrder extends BaseNulsData {
    //币对hash
    private byte[] tradingHash;
    //委托人
    private byte[] address;
    //1买入 2卖出
    private byte type;
    //委托交易币种数量
    private BigInteger amount;
    //委托价格
    private BigInteger price;
    //参与分红地址
    private byte[] feeAddress;
    //参与分红比例，取值0-5
    private byte feeScale;

    @Override
    public int size() {
        int size = 0;
        size += NulsHash.HASH_LENGTH;
        size += Address.ADDRESS_LENGTH;
        size += 1;
        size += SerializeUtils.sizeOfBigInteger();
        size += SerializeUtils.sizeOfBigInteger();
        size += SerializeUtils.sizeOfBytes(feeAddress);
        size += 1;
        return size;
    }

    @Override
    protected void serializeToStream(NulsOutputStreamBuffer stream) throws IOException {
        stream.write(tradingHash);
        stream.write(address);
        stream.write(type);
        stream.writeBigInteger(amount);
        stream.writeBigInteger(price);
        stream.writeBytesWithLength(feeAddress);
        stream.write(feeScale);
    }

    @Override
    public void parse(NulsByteBuffer byteBuffer) throws NulsException {
        tradingHash = byteBuffer.readBytes(NulsHash.HASH_LENGTH);
        address = byteBuffer.readBytes(Address.ADDRESS_LENGTH);
        type = byteBuffer.readByte();
        amount = byteBuffer.readBigInteger();
        price = byteBuffer.readBigInteger();
        feeAddress = byteBuffer.readByLengthByte();
        feeScale = byteBuffer.readByte();
    }

    public byte[] getTradingHash() {
        return tradingHash;
    }

    public void setTradingHash(byte[] tradingHash) {
        this.tradingHash = tradingHash;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public BigInteger getAmount() {
        return amount;
    }

    public void setAmount(BigInteger amount) {
        this.amount = amount;
    }

    public BigInteger getPrice() {
        return price;
    }

    public void setPrice(BigInteger price) {
        this.price = price;
    }

    public byte[] getFeeAddress() {
        return feeAddress;
    }

    public void setFeeAddress(byte[] feeAddress) {
        this.feeAddress = feeAddress;
    }

    public byte getFeeScale() {
        return feeScale;
    }

    public void setFeeScale(byte feeScale) {
        this.feeScale = feeScale;
    }

    public byte[] getAddress() {
        return address;
    }

    public void setAddress(byte[] address) {
        this.address = address;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"tradingHash\":")
                .append('\"').append(HexUtil.encode(tradingHash)).append('\"');
        sb.append(",\"address\":")
                .append('\"').append(address == null ? "" : AddressTool.getStringAddressByBytes(address)).append('\"');
        sb.append(",\"type\":")
                .append(type);
        sb.append(",\"amount\":")
                .append('\"').append(amount).append('\"');
        sb.append(",\"price\":")
                .append('\"').append(price).append('\"');
        sb.append(",\"feeAddress\":")
                .append('\"').append(feeAddress == null ? "" : AddressTool.getStringAddressByBytes(feeAddress)).append('\"');
        sb.append(",\"feeScale\":")
                .append(feeScale);
        sb.append('}');
        return sb.toString();
    }
}
