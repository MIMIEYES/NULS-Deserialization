/*
 * *
 *  * MIT License
 *  *
 *  * Copyright (c) 2017-2018 nuls.io
 *  *
 *  * Permission is hereby granted, free of charge, to any person obtaining a copy
 *  * of this software and associated documentation files (the "Software"), to deal
 *  * in the Software without restriction, including without limitation the rights
 *  * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  * copies of the Software, and to permit persons to whom the Software is
 *  * furnished to do so, subject to the following conditions:
 *  *
 *  * The above copyright notice and this permission notice shall be included in all
 *  * copies or substantial portions of the Software.
 *  *
 *  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  * SOFTWARE.
 *
 */
package net.mimieye.model.txdata;


import io.nuls.base.basic.AddressTool;
import io.nuls.base.basic.NulsByteBuffer;
import io.nuls.base.basic.NulsOutputStreamBuffer;
import io.nuls.base.data.Address;
import io.nuls.base.data.BaseNulsData;
import io.nuls.base.data.NulsHash;
import io.nuls.core.exception.NulsException;
import io.nuls.core.parse.SerializeUtils;
import io.nuls.core.rpc.model.ApiModel;
import io.nuls.core.rpc.model.ApiModelProperty;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 委托信息类
 * Delegated information class
 *
 * @author tag
 * 2018/11/28
 */

public class Deposit extends BaseNulsData {
    
    private BigInteger deposit;
    
    private NulsHash agentHash;
    
    private byte[] address;
    
    private transient long time;
    
    private transient int status;
    
    private transient NulsHash txHash;
    
    private transient long blockHeight = -1L;
    
    private transient long delHeight = -1L;

    /**
     * serialize important field
     */
    @Override
    protected void serializeToStream(NulsOutputStreamBuffer stream) throws IOException {
        stream.writeBigInteger(deposit);
        stream.write(address);
        stream.write(agentHash.getBytes());

    }

    @Override
    public void parse(NulsByteBuffer byteBuffer) throws NulsException {
        this.deposit = byteBuffer.readBigInteger();
        this.address = byteBuffer.readBytes(Address.ADDRESS_LENGTH);
        this.agentHash = byteBuffer.readHash();
    }

    @Override
    public int size() {
        int size = 0;
        size += SerializeUtils.sizeOfBigInteger();
        size += Address.ADDRESS_LENGTH;
        size += NulsHash.HASH_LENGTH;
        return size;
    }

    public BigInteger getDeposit() {
        return deposit;
    }

    public void setDeposit(BigInteger deposit) {
        this.deposit = deposit;
    }

    public NulsHash getAgentHash() {
        return agentHash;
    }

    public void setAgentHash(NulsHash agentHash) {
        this.agentHash = agentHash;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public NulsHash getTxHash() {
        return txHash;
    }

    public void setTxHash(NulsHash txHash) {
        this.txHash = txHash;
    }

    public long getBlockHeight() {
        return blockHeight;
    }

    public void setBlockHeight(long blockHeight) {
        this.blockHeight = blockHeight;
    }

    public long getDelHeight() {
        return delHeight;
    }

    public void setDelHeight(long delHeight) {
        this.delHeight = delHeight;
    }

    public byte[] getAddress() {
        return address;
    }

    public void setAddress(byte[] address) {
        this.address = address;
    }

    public Set<byte[]> getAddresses() {
        Set<byte[]> addressSet = new HashSet<>();
        addressSet.add(this.address);
        return addressSet;
    }

    @Override
    public io.nuls.v2.txdata.Deposit clone() throws CloneNotSupportedException {
        return (io.nuls.v2.txdata.Deposit) super.clone();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"deposit\":")
                .append(deposit);
        sb.append(",\"agentHash\":")
                .append('\"').append(agentHash).append('\"');
        sb.append(",\"address\":")
                .append('\"').append(AddressTool.getStringAddressByBytes(address)).append('\"');
        sb.append(",\"time\":")
                .append(time);
        sb.append(",\"status\":")
                .append(status);
        sb.append(",\"txHash\":")
                .append('\"').append(txHash).append('\"');
        sb.append(",\"blockHeight\":")
                .append(blockHeight);
        sb.append(",\"delHeight\":")
                .append(delHeight);
        sb.append('}');
        return sb.toString();
    }
}
