/**
 * MIT License
 * <p>
 * Copyright (c) 2019-2020 nerve.network
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
import net.mimieye.model.txdata.nerve.bus.HeterogeneousAddress;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 虚拟银行提交异构链地址txData
 * @author: Loki
 * @date: 2020-02-17
 */
public class SubmitHeterogeneousAddressTxData extends BaseNulsData {

    /**
     * 节点地址
     */
    private byte[] agentAddress;

    /**
     * 异构链地址集合
     */
    private List<HeterogeneousAddress> heterogeneousAddressList;


    @Override
    protected void serializeToStream(NulsOutputStreamBuffer stream) throws IOException {
        stream.write(agentAddress);
        int count = heterogeneousAddressList == null ? 0 : heterogeneousAddressList.size();
        stream.writeUint16(count);
        if(null != heterogeneousAddressList){
            for(HeterogeneousAddress address : heterogeneousAddressList){
                stream.writeNulsData(address);
            }
        }
    }

    @Override
    public void parse(NulsByteBuffer byteBuffer) throws NulsException {
        this.agentAddress = byteBuffer.readBytes(Address.ADDRESS_LENGTH);
        int count = byteBuffer.readUint16();
        if(0 < count){
            List<HeterogeneousAddress> list = new ArrayList<>();
            for(int i = 0; i< count; i++){
                list.add(byteBuffer.readNulsData(new HeterogeneousAddress()));
            }
            this.heterogeneousAddressList = list;
        }
    }

    @Override
    public int size() {
        int size = Address.ADDRESS_LENGTH;
        size += SerializeUtils.sizeOfUint16();
        if (null != heterogeneousAddressList) {
           for(HeterogeneousAddress hAddress : heterogeneousAddressList){
               size += SerializeUtils.sizeOfNulsData(hAddress);
           }
        }
        return size;
    }

    public byte[] getAgentAddress() {
        return agentAddress;
    }

    public void setAgentAddress(byte[] agentAddress) {
        this.agentAddress = agentAddress;
    }

    public List<HeterogeneousAddress> getHeterogeneousAddressList() {
        return heterogeneousAddressList;
    }

    public void setHeterogeneousAddressList(List<HeterogeneousAddress> heterogeneousAddressList) {
        this.heterogeneousAddressList = heterogeneousAddressList;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"agentAddress\":")
                .append('\"').append(AddressTool.getStringAddressByBytes(agentAddress)).append('\"');
        sb.append(",\"heterogeneousAddressList\": [");
        if (heterogeneousAddressList != null && !heterogeneousAddressList.isEmpty()) {
            heterogeneousAddressList.stream().forEach(address -> sb.append(address.toString()).append(','));
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append("]}");
        return sb.toString();
    }

}
