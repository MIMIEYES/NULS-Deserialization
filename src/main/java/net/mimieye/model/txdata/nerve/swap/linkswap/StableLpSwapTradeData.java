package net.mimieye.model.txdata.nerve.swap.linkswap;

import io.nuls.base.basic.AddressTool;
import io.nuls.base.basic.NulsByteBuffer;
import io.nuls.base.basic.NulsOutputStreamBuffer;
import io.nuls.base.data.Address;
import io.nuls.base.data.BaseNulsData;
import io.nuls.core.exception.NulsException;
import io.nuls.core.parse.SerializeUtils;
import net.mimieye.model.txdata.nerve.swap.NerveToken;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;

/**
 * @author Niels
 */
public class StableLpSwapTradeData extends BaseNulsData {

    private BigInteger amountOutMin;
    private byte[] to;
    /**
     * 手续费接收地址
     */
    private byte[] feeTo;
    private long deadline;
    private NerveToken[] path;

    @Override
    protected void serializeToStream(NulsOutputStreamBuffer stream) throws IOException {
        stream.writeBigInteger(amountOutMin);
        stream.write(to);
        stream.writeBytesWithLength(feeTo);
        stream.writeUint32(deadline);
        short length = (short) path.length;
        stream.writeUint8(length);
        for (int i = 0; i < length; i++) {
            stream.writeNulsData(path[i]);
        }

    }

    @Override
    public void parse(NulsByteBuffer byteBuffer) throws NulsException {
        this.amountOutMin = byteBuffer.readBigInteger();
        this.to = byteBuffer.readBytes(Address.ADDRESS_LENGTH);
        this.feeTo = byteBuffer.readByLengthByte();
        this.deadline = byteBuffer.readUint32();
        short length = byteBuffer.readUint8();
        this.path = new NerveToken[length];
        for (int i = 0; i < length; i++) {
            path[i] = byteBuffer.readNulsData(new NerveToken());
        }
    }

    @Override
    public int size() {
        int size = 0;
        size += SerializeUtils.sizeOfBigInteger();
        size += Address.ADDRESS_LENGTH;
        size += SerializeUtils.sizeOfBytes(feeTo);
        size += SerializeUtils.sizeOfUint32();
        size += 1;
        size += SerializeUtils.sizeOfNulsData(new NerveToken()) * path.length;
        return size;
    }

    public BigInteger getAmountOutMin() {
        return amountOutMin;
    }

    public void setAmountOutMin(BigInteger amountOutMin) {
        this.amountOutMin = amountOutMin;
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

    public long getDeadline() {
        return deadline;
    }

    public void setDeadline(long deadline) {
        this.deadline = deadline;
    }

    public NerveToken[] getPath() {
        return path;
    }

    public void setPath(NerveToken[] path) {
        this.path = path;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"amountOutMin\":")
                .append('\"').append(amountOutMin).append('\"');
        sb.append(",\"to\":")
                .append('\"').append(AddressTool.getStringAddressByBytes(to)).append('\"');
        sb.append(",\"feeTo\":")
                .append('\"').append(feeTo != null ? AddressTool.getStringAddressByBytes(feeTo) : "").append('\"');
        sb.append(",\"deadline\":")
                .append(deadline);
        sb.append(",\"path\":[");
        if (path != null) {
            for (NerveToken token : path) {
                sb.append(token).append(',');
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append("]}");
        return sb.toString();
    }
}
