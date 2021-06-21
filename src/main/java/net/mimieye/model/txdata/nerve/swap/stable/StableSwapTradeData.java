package net.mimieye.model.txdata.nerve.swap.stable;

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
public class StableSwapTradeData extends BaseNulsData {

    private byte[] to;
    private byte tokenOutIndex;
    /**
     * 手续费接收地址
     */
    private byte[] feeTo;

    @Override
    protected void serializeToStream(NulsOutputStreamBuffer stream) throws IOException {
        stream.write(to);
        stream.writeByte(tokenOutIndex);
        if (feeTo != null) {
            stream.write(feeTo);
        }
    }

    @Override
    public void parse(NulsByteBuffer byteBuffer) throws NulsException {
        this.to = byteBuffer.readBytes(Address.ADDRESS_LENGTH);
        this.tokenOutIndex = byteBuffer.readByte();
        if (!byteBuffer.isFinished()) {
            this.feeTo = byteBuffer.readBytes(Address.ADDRESS_LENGTH);
        }
    }

    @Override
    public int size() {
        int size = 0;
        size += Address.ADDRESS_LENGTH;
        size += 1;
        if (feeTo != null) {
            size += Address.ADDRESS_LENGTH;
        }
        return size;
    }


    public byte[] getTo() {
        return to;
    }

    public void setTo(byte[] to) {
        this.to = to;
    }

    public byte[] getFeeTo() {
        return feeTo;
    }

    public void setFeeTo(byte[] feeTo) {
        this.feeTo = feeTo;
    }

    public byte getTokenOutIndex() {
        return tokenOutIndex;
    }

    public void setTokenOutIndex(byte tokenOutIndex) {
        this.tokenOutIndex = tokenOutIndex;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"to\":")
                .append('\"').append(AddressTool.getStringAddressByBytes(to)).append('\"');
        sb.append(",\"tokenOutIndex\":")
                .append(tokenOutIndex);
        sb.append(",\"feeTo\":")
                .append('\"').append(feeTo != null ? AddressTool.getStringAddressByBytes(feeTo) : "").append('\"');
        sb.append('}');
        return sb.toString();
    }
}
