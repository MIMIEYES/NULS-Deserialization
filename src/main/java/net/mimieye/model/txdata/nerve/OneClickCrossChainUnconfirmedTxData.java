/**
 * MIT License
 * <p>
 * Copyright (c) 2017-2018 nuls.io
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package net.mimieye.model.txdata.nerve;

import io.nuls.base.basic.AddressTool;
import io.nuls.base.basic.NulsByteBuffer;
import io.nuls.base.basic.NulsOutputStreamBuffer;
import io.nuls.base.data.Address;
import io.nuls.base.data.BaseNulsData;
import io.nuls.core.exception.NulsException;
import io.nuls.core.parse.SerializeUtils;
import net.mimieye.core.crypto.HexUtil;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;

/**
 * 一键跨链
 * @author: PierreLuo
 * @date: 2022/3/21
 */
public class OneClickCrossChainUnconfirmedTxData extends BaseNulsData {

    /**
     * 异构链充值交易hash
     */
    private HeterogeneousHash originalTxHash;
    /**
     * 异构链交易所在区块高度
     */
    private long heterogeneousHeight;
    /**
     * 异构链网络中 充值的from地址
     */
    private String heterogeneousFromAddress;
    /**
     * nerve 网络中的到账地址
     */
    private byte[] nerveToAddress;
    // erc20资产
    private BigInteger erc20Amount;
    private int erc20AssetChainId;
    private int erc20AssetId;
    // tipping资产
    private String tippingAddress;
    private BigInteger tipping;
    private int tippingChainId;
    private int tippingAssetId;
    // main资产
    private BigInteger mainAssetFeeAmount;// 跨到目标链的手续费支出
    private BigInteger mainAssetAmount;
    private int mainAssetChainId;
    private int mainAssetId;
    // 目标链跨链信息
    private int desChainId;
    private String desToAddress;
    private String desExtend;
    // 预留字段
    private byte[] extend;

    @Override
    protected void serializeToStream(NulsOutputStreamBuffer stream) throws IOException {
        stream.writeNulsData(this.originalTxHash);
        stream.writeInt64(this.heterogeneousHeight);
        stream.writeString(this.heterogeneousFromAddress);
        stream.write(this.nerveToAddress);
        stream.writeBigInteger(this.erc20Amount);
        stream.writeUint16(this.erc20AssetChainId);
        stream.writeUint16(this.erc20AssetId);
        stream.writeString(this.tippingAddress);
        stream.writeBigInteger(this.tipping);
        stream.writeUint16(this.tippingChainId);
        stream.writeUint16(this.tippingAssetId);
        stream.writeBigInteger(this.mainAssetFeeAmount);
        stream.writeBigInteger(this.mainAssetAmount);
        stream.writeUint16(this.mainAssetChainId);
        stream.writeUint16(this.mainAssetId);
        stream.writeUint16(this.desChainId);
        stream.writeString(this.desToAddress);
        stream.writeString(this.desExtend);
        stream.writeBytesWithLength(this.extend);
    }

    @Override
    public void parse(NulsByteBuffer byteBuffer) throws NulsException {
        this.originalTxHash = byteBuffer.readNulsData(new HeterogeneousHash());
        this.heterogeneousHeight = byteBuffer.readInt64();
        this.heterogeneousFromAddress = byteBuffer.readString();
        this.nerveToAddress = byteBuffer.readBytes(Address.ADDRESS_LENGTH);
        this.erc20Amount = byteBuffer.readBigInteger();
        this.erc20AssetChainId = byteBuffer.readUint16();
        this.erc20AssetId = byteBuffer.readUint16();
        this.tippingAddress = byteBuffer.readString();
        this.tipping = byteBuffer.readBigInteger();
        this.tippingChainId = byteBuffer.readUint16();
        this.tippingAssetId = byteBuffer.readUint16();
        this.mainAssetFeeAmount = byteBuffer.readBigInteger();
        this.mainAssetAmount = byteBuffer.readBigInteger();
        this.mainAssetChainId = byteBuffer.readUint16();
        this.mainAssetId = byteBuffer.readUint16();
        this.desChainId = byteBuffer.readUint16();
        this.desToAddress = byteBuffer.readString();
        this.desExtend = byteBuffer.readString();
        this.extend = byteBuffer.readByLengthByte();
    }

    @Override
    public int size() {
        int size = 0;
        size += SerializeUtils.sizeOfNulsData(this.originalTxHash);
        size += SerializeUtils.sizeOfInt64();
        size += SerializeUtils.sizeOfString(this.heterogeneousFromAddress);
        size += Address.ADDRESS_LENGTH;
        size += SerializeUtils.sizeOfBigInteger();
        size += SerializeUtils.sizeOfInt16();
        size += SerializeUtils.sizeOfInt16();
        size += SerializeUtils.sizeOfString(this.tippingAddress);
        size += SerializeUtils.sizeOfBigInteger();
        size += SerializeUtils.sizeOfInt16();
        size += SerializeUtils.sizeOfInt16();
        size += SerializeUtils.sizeOfBigInteger();
        size += SerializeUtils.sizeOfBigInteger();
        size += SerializeUtils.sizeOfInt16();
        size += SerializeUtils.sizeOfInt16();
        size += SerializeUtils.sizeOfInt16();
        size += SerializeUtils.sizeOfString(this.desToAddress);
        size += SerializeUtils.sizeOfString(this.desExtend);
        size += SerializeUtils.sizeOfBytes(this.extend);
        return size;
    }

    public String getTippingAddress() {
        return tippingAddress;
    }

    public void setTippingAddress(String tippingAddress) {
        this.tippingAddress = tippingAddress;
    }

    public BigInteger getTipping() {
        return tipping;
    }

    public void setTipping(BigInteger tipping) {
        this.tipping = tipping;
    }

    public int getTippingChainId() {
        return tippingChainId;
    }

    public void setTippingChainId(int tippingChainId) {
        this.tippingChainId = tippingChainId;
    }

    public int getTippingAssetId() {
        return tippingAssetId;
    }

    public void setTippingAssetId(int tippingAssetId) {
        this.tippingAssetId = tippingAssetId;
    }

    public HeterogeneousHash getOriginalTxHash() {
        return originalTxHash;
    }

    public void setOriginalTxHash(HeterogeneousHash originalTxHash) {
        this.originalTxHash = originalTxHash;
    }

    public long getHeterogeneousHeight() {
        return heterogeneousHeight;
    }

    public void setHeterogeneousHeight(long heterogeneousHeight) {
        this.heterogeneousHeight = heterogeneousHeight;
    }

    public String getHeterogeneousFromAddress() {
        return heterogeneousFromAddress;
    }

    public void setHeterogeneousFromAddress(String heterogeneousFromAddress) {
        this.heterogeneousFromAddress = heterogeneousFromAddress;
    }

    public byte[] getNerveToAddress() {
        return nerveToAddress;
    }

    public void setNerveToAddress(byte[] nerveToAddress) {
        this.nerveToAddress = nerveToAddress;
    }

    public BigInteger getErc20Amount() {
        return erc20Amount;
    }

    public void setErc20Amount(BigInteger erc20Amount) {
        this.erc20Amount = erc20Amount;
    }

    public int getErc20AssetChainId() {
        return erc20AssetChainId;
    }

    public void setErc20AssetChainId(int erc20AssetChainId) {
        this.erc20AssetChainId = erc20AssetChainId;
    }

    public int getErc20AssetId() {
        return erc20AssetId;
    }

    public void setErc20AssetId(int erc20AssetId) {
        this.erc20AssetId = erc20AssetId;
    }

    public BigInteger getMainAssetFeeAmount() {
        return mainAssetFeeAmount;
    }

    public void setMainAssetFeeAmount(BigInteger mainAssetFeeAmount) {
        this.mainAssetFeeAmount = mainAssetFeeAmount;
    }

    public BigInteger getMainAssetAmount() {
        return mainAssetAmount;
    }

    public void setMainAssetAmount(BigInteger mainAssetAmount) {
        this.mainAssetAmount = mainAssetAmount;
    }

    public int getMainAssetChainId() {
        return mainAssetChainId;
    }

    public void setMainAssetChainId(int mainAssetChainId) {
        this.mainAssetChainId = mainAssetChainId;
    }

    public int getMainAssetId() {
        return mainAssetId;
    }

    public void setMainAssetId(int mainAssetId) {
        this.mainAssetId = mainAssetId;
    }

    public int getDesChainId() {
        return desChainId;
    }

    public void setDesChainId(int desChainId) {
        this.desChainId = desChainId;
    }

    public String getDesToAddress() {
        return desToAddress;
    }

    public void setDesToAddress(String desToAddress) {
        this.desToAddress = desToAddress;
    }

    public String getDesExtend() {
        return desExtend;
    }

    public void setDesExtend(String desExtend) {
        this.desExtend = desExtend;
    }

    public byte[] getExtend() {
        return extend;
    }

    public void setExtend(byte[] extend) {
        this.extend = extend;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"originalTxHash\":")
                .append(originalTxHash.toString());
        sb.append(",\"heterogeneousHeight\":")
                .append(heterogeneousHeight);
        sb.append(",\"heterogeneousFromAddress\":")
                .append('\"').append(heterogeneousFromAddress).append('\"');
        sb.append(",\"nerveToAddress\":")
                .append('\"').append(AddressTool.getStringAddressByBytes(nerveToAddress)).append('\"');
        sb.append(",\"erc20Amount\":")
                .append(erc20Amount);
        sb.append(",\"erc20AssetChainId\":")
                .append(erc20AssetChainId);
        sb.append(",\"erc20AssetId\":")
                .append(erc20AssetId);
        sb.append(",\"tippingAddress\":")
                .append('\"').append(tippingAddress).append('\"');
        sb.append(",\"tipping\":")
                .append(tipping);
        sb.append(",\"tippingChainId\":")
                .append(tippingChainId);
        sb.append(",\"tippingAssetId\":")
                .append(tippingAssetId);
        sb.append(",\"mainAssetFeeAmount\":")
                .append(mainAssetFeeAmount);
        sb.append(",\"mainAssetAmount\":")
                .append(mainAssetAmount);
        sb.append(",\"mainAssetChainId\":")
                .append(mainAssetChainId);
        sb.append(",\"mainAssetId\":")
                .append(mainAssetId);
        sb.append(",\"desChainId\":")
                .append(desChainId);
        sb.append(",\"desToAddress\":")
                .append('\"').append(desToAddress).append('\"');
        sb.append(",\"desExtend\":")
                .append('\"').append(desExtend).append('\"');
        sb.append(",\"extend\":")
                .append('\"').append(extend != null ? HexUtil.encode(extend) : null).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
