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

import io.nuls.base.basic.NulsByteBuffer;
import io.nuls.base.basic.NulsOutputStreamBuffer;
import io.nuls.base.data.BaseNulsData;
import io.nuls.core.exception.NulsException;
import io.nuls.core.parse.SerializeUtils;
import net.mimieye.core.crypto.HexUtil;

import java.io.IOException;
import java.util.Arrays;

/**
 * 跨链追加跨链手续费交易txData
 *
 * @author: PierreLuo
 * @date: 2022/4/13
 */
public class WithdrawalAddFeeByCrossChainTxData extends BaseNulsData {
    /**
     * 异构链交易hash
     */
    private HeterogeneousHash htgTxHash;
    /**
     * 异构链交易所在区块高度
     */
    private long heterogeneousHeight;
    /**
     * 异构链网络中 充值的from地址
     */
    private String heterogeneousFromAddress;
    /**
     * 要追加手续费的nerve提现交易hash
     */
    private String nerveTxHash;
    private String subExtend;
    // 预留字段
    private byte[] extend;

    public WithdrawalAddFeeByCrossChainTxData() {
    }


    @Override
    protected void serializeToStream(NulsOutputStreamBuffer stream) throws IOException {
        stream.writeNulsData(this.htgTxHash);
        stream.writeInt64(this.heterogeneousHeight);
        stream.writeString(this.heterogeneousFromAddress);
        stream.writeString(this.nerveTxHash);
        stream.writeString(this.subExtend);
        //TODO pierre test code
        //stream.writeBytesWithLength(this.extend);
    }

    @Override
    public void parse(NulsByteBuffer byteBuffer) throws NulsException {
        this.htgTxHash = byteBuffer.readNulsData(new HeterogeneousHash());
        this.heterogeneousHeight = byteBuffer.readInt64();
        this.heterogeneousFromAddress = byteBuffer.readString();
        this.nerveTxHash = byteBuffer.readString();
        this.subExtend = byteBuffer.readString();
        //TODO pierre test code
        //this.extend = byteBuffer.readByLengthByte();
    }

    @Override
    public int size() {
        int size = 0;
        size += SerializeUtils.sizeOfNulsData(this.htgTxHash);
        size += SerializeUtils.sizeOfInt64();
        size += SerializeUtils.sizeOfString(this.heterogeneousFromAddress);
        size += SerializeUtils.sizeOfString(this.nerveTxHash);
        size += SerializeUtils.sizeOfString(this.subExtend);
        //TODO pierre test code
        //size += SerializeUtils.sizeOfBytes(this.extend);
        return size;
    }

    public HeterogeneousHash getHtgTxHash() {
        return htgTxHash;
    }

    public void setHtgTxHash(HeterogeneousHash htgTxHash) {
        this.htgTxHash = htgTxHash;
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

    public String getNerveTxHash() {
        return nerveTxHash;
    }

    public void setNerveTxHash(String nerveTxHash) {
        this.nerveTxHash = nerveTxHash;
    }

    public String getSubExtend() {
        return subExtend;
    }

    public void setSubExtend(String subExtend) {
        this.subExtend = subExtend;
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
        sb.append("\"htgTxHash\":")
                .append(htgTxHash.toString());
        sb.append(",\"heterogeneousHeight\":")
                .append(heterogeneousHeight);
        sb.append(",\"heterogeneousFromAddress\":")
                .append('\"').append(heterogeneousFromAddress).append('\"');
        sb.append(",\"nerveTxHash\":")
                .append('\"').append(nerveTxHash).append('\"');
        sb.append(",\"subExtend\":")
                .append('\"').append(subExtend).append('\"');
        sb.append(",\"extend\":")
                .append('\"').append(extend != null ? HexUtil.encode(extend) : null).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
