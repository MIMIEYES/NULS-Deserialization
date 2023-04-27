package net.mimieye.model.txdata.nerve.dex;

import io.nuls.base.basic.NulsByteBuffer;
import io.nuls.base.basic.NulsOutputStreamBuffer;
import io.nuls.base.data.BaseNulsData;
import io.nuls.base.data.NulsHash;
import io.nuls.core.crypto.HexUtil;
import io.nuls.core.exception.NulsException;
import io.nuls.core.parse.SerializeUtils;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;

public class TradingDeal extends BaseNulsData {
    public static int NONCE_LENGTH = 8;
    //交易对hash
    private byte[] tradingHash;
    //买单交易hash
    private byte[] buyHash;

    private byte[] buyNonce;
    //卖单交易hash
    private byte[] sellHash;

    private byte[] sellNonce;
    //交易币种成交量
    private BigInteger quoteAmount;
    //计价币种成交量
    private BigInteger baseAmount;
    //买家支付手续费
    private BigInteger buyFee;
    //卖家支付手续费
    private BigInteger sellFee;
    //成交价
    private BigInteger price;
    //成交状态: 1 买单已完全成交，2 卖单已完全成交，3 买卖双方都完全成交
    private byte type;
    //主动成交方：1买单主动成交， 2卖单主动成交
    private byte taker;

    @Override
    public int size() {
        int size = NulsHash.HASH_LENGTH * 3;
        size += NONCE_LENGTH * 2;
        size += SerializeUtils.sizeOfBigInteger() * 5;
        size += 2;
        return size;
    }

    @Override
    protected void serializeToStream(NulsOutputStreamBuffer stream) throws IOException {
        stream.write(tradingHash);
        stream.write(buyHash);
        stream.write(buyNonce);
        stream.write(sellHash);
        stream.write(sellNonce);
        stream.writeBigInteger(quoteAmount);
        stream.writeBigInteger(baseAmount);
        stream.writeBigInteger(buyFee);
        stream.writeBigInteger(sellFee);
        stream.writeBigInteger(price);
        stream.writeByte(type);
        stream.writeByte(taker);
    }

    @Override
    public void parse(NulsByteBuffer byteBuffer) throws NulsException {
        tradingHash = byteBuffer.readBytes(NulsHash.HASH_LENGTH);
        buyHash = byteBuffer.readBytes(NulsHash.HASH_LENGTH);
        buyNonce = byteBuffer.readBytes(NONCE_LENGTH);
        sellHash = byteBuffer.readBytes(NulsHash.HASH_LENGTH);
        sellNonce = byteBuffer.readBytes(NONCE_LENGTH);
        quoteAmount = byteBuffer.readBigInteger();
        baseAmount = byteBuffer.readBigInteger();
        buyFee = byteBuffer.readBigInteger();
        sellFee = byteBuffer.readBigInteger();
        price = byteBuffer.readBigInteger();
        type = byteBuffer.readByte();
        taker = byteBuffer.readByte();
    }


    public byte[] getSellHash() {
        return sellHash;
    }

    public void setSellHash(byte[] sellHash) {
        this.sellHash = sellHash;
    }

    public byte[] getBuyHash() {
        return buyHash;
    }

    public void setBuyHash(byte[] buyHash) {
        this.buyHash = buyHash;
    }

    public BigInteger getPrice() {
        return price;
    }

    public void setPrice(BigInteger price) {
        this.price = price;
    }

    public BigInteger getQuoteAmount() {
        return quoteAmount;
    }

    public void setQuoteAmount(BigInteger quoteAmount) {
        this.quoteAmount = quoteAmount;
    }

    public BigInteger getBaseAmount() {
        return baseAmount;
    }

    public void setBaseAmount(BigInteger baseAmount) {
        this.baseAmount = baseAmount;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public byte getTaker() {
        return taker;
    }

    public void setTaker(byte taker) {
        this.taker = taker;
    }

    public byte[] getTradingHash() {
        return tradingHash;
    }

    public void setTradingHash(byte[] tradingHash) {
        this.tradingHash = tradingHash;
    }

    public byte[] getBuyNonce() {
        return buyNonce;
    }

    public void setBuyNonce(byte[] buyNonce) {
        this.buyNonce = buyNonce;
    }

    public byte[] getSellNonce() {
        return sellNonce;
    }

    public void setSellNonce(byte[] sellNonce) {
        this.sellNonce = sellNonce;
    }

    public BigInteger getBuyFee() {
        return buyFee;
    }

    public void setBuyFee(BigInteger buyFee) {
        this.buyFee = buyFee;
    }

    public BigInteger getSellFee() {
        return sellFee;
    }

    public void setSellFee(BigInteger sellFee) {
        this.sellFee = sellFee;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"tradingHash\":")
                .append('\"').append(HexUtil.encode(tradingHash)).append('\"');
        sb.append(",\"buyHash\":")
                .append('\"').append(HexUtil.encode(buyHash)).append('\"');
        sb.append(",\"buyNonce\":")
                .append('\"').append(HexUtil.encode(buyNonce)).append('\"');
        sb.append(",\"sellHash\":")
                .append('\"').append(HexUtil.encode(sellHash)).append('\"');
        sb.append(",\"sellNonce\":")
                .append('\"').append(HexUtil.encode(sellNonce)).append('\"');
        sb.append(",\"quoteAmount\":")
                .append('\"').append(quoteAmount).append('\"');
        sb.append(",\"baseAmount\":")
                .append('\"').append(baseAmount).append('\"');
        sb.append(",\"buyFee\":")
                .append('\"').append(buyFee).append('\"');
        sb.append(",\"sellFee\":")
                .append('\"').append(sellFee).append('\"');
        sb.append(",\"price\":")
                .append('\"').append(price).append('\"');
        sb.append(",\"type\":")
                .append(type);
        sb.append(",\"taker\":")
                .append(taker);
        sb.append('}');
        return sb.toString();
    }
}
