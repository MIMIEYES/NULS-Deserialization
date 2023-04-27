package net.mimieye.model.txdata.nerve.dex;

import io.nuls.base.basic.NulsByteBuffer;
import io.nuls.base.basic.NulsOutputStreamBuffer;
import io.nuls.base.data.BaseNulsData;
import io.nuls.base.data.NulsHash;
import io.nuls.core.exception.NulsException;
import io.nuls.core.parse.SerializeUtils;

import java.io.IOException;
import java.math.BigInteger;

/**
 * 修改币种交易协议
 */
public class EditCoinTrading extends BaseNulsData {

    private NulsHash tradingHash;

    //交易币种允许最小交易小数位
    private byte scaleBaseDecimal;

    //计价币种允许最小交易小数位
    private byte scaleQuoteDecimal;
    //最小交易量
    private BigInteger minBaseAmount;

    private BigInteger minQuoteAmount;


    @Override
    public int size() {
        int size = 0;
        size += tradingHash.getBytes().length;
        size += 2;
        size += SerializeUtils.sizeOfBigInteger() * 2;
        return size;
    }

    @Override
    protected void serializeToStream(NulsOutputStreamBuffer stream) throws IOException {
        stream.write(tradingHash.getBytes());
        stream.write(scaleBaseDecimal);
        stream.write(scaleQuoteDecimal);
        stream.writeBigInteger(minBaseAmount);
        stream.writeBigInteger(minQuoteAmount);
    }

    @Override
    public void parse(NulsByteBuffer byteBuffer) throws NulsException {
        tradingHash = byteBuffer.readHash();
        scaleBaseDecimal = byteBuffer.readByte();
        scaleQuoteDecimal = byteBuffer.readByte();
        minBaseAmount = byteBuffer.readBigInteger();
        minQuoteAmount = byteBuffer.readBigInteger();
    }


    public NulsHash getTradingHash() {
        return tradingHash;
    }

    public void setTradingHash(NulsHash tradingHash) {
        this.tradingHash = tradingHash;
    }

    public byte getScaleBaseDecimal() {
        return scaleBaseDecimal;
    }

    public void setScaleBaseDecimal(byte scaleBaseDecimal) {
        this.scaleBaseDecimal = scaleBaseDecimal;
    }

    public byte getScaleQuoteDecimal() {
        return scaleQuoteDecimal;
    }

    public void setScaleQuoteDecimal(byte scaleQuoteDecimal) {
        this.scaleQuoteDecimal = scaleQuoteDecimal;
    }

    public BigInteger getMinBaseAmount() {
        return minBaseAmount;
    }

    public void setMinBaseAmount(BigInteger minBaseAmount) {
        this.minBaseAmount = minBaseAmount;
    }

    public BigInteger getMinQuoteAmount() {
        return minQuoteAmount;
    }

    public void setMinQuoteAmount(BigInteger minQuoteAmount) {
        this.minQuoteAmount = minQuoteAmount;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"tradingHash\":")
                .append('\"').append(tradingHash.toString()).append('\"');
        sb.append(",\"scaleBaseDecimal\":")
                .append(scaleBaseDecimal);
        sb.append(",\"scaleQuoteDecimal\":")
                .append(scaleQuoteDecimal);
        sb.append(",\"minBaseAmount\":")
                .append('\"').append(minBaseAmount).append('\"');
        sb.append(",\"minQuoteAmount\":")
                .append('\"').append(minQuoteAmount).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
