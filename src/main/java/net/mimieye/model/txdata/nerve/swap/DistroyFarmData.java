package net.mimieye.model.txdata.nerve.swap;

import io.nuls.base.basic.AddressTool;
import io.nuls.base.basic.NulsByteBuffer;
import io.nuls.base.basic.NulsOutputStreamBuffer;
import io.nuls.base.data.Address;
import io.nuls.base.data.BaseNulsData;
import io.nuls.core.exception.NulsException;

import java.io.IOException;
import java.util.Arrays;

/**
 * @author Niels
 */
public class DistroyFarmData extends BaseNulsData {
    private byte[] farmAddress;

    @Override
    protected void serializeToStream(NulsOutputStreamBuffer stream) throws IOException {
        stream.write(farmAddress);
    }

    @Override
    public void parse(NulsByteBuffer byteBuffer) throws NulsException {
        this.farmAddress = byteBuffer.readBytes(Address.ADDRESS_LENGTH);
    }

    @Override
    public int size() {
        int size = Address.ADDRESS_LENGTH;
        return size;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"farmAddress\":")
                .append('\"').append(AddressTool.getStringAddressByBytes(farmAddress)).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
