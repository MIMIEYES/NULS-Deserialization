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

public class CancelDeal extends BaseNulsData {

    private byte[] cancelHash;

    private byte[] orderHash;

    private byte status;

    private BigInteger cancelAmount;

    @Override
    public int size() {
        int size = NulsHash.HASH_LENGTH * 2;
        size += 1;
        size += SerializeUtils.sizeOfBigInteger();
        return size;
    }

    @Override
    protected void serializeToStream(NulsOutputStreamBuffer stream) throws IOException {
        stream.write(cancelHash);
        stream.write(orderHash);
        stream.write(status);
        stream.writeBigInteger(cancelAmount);
    }

    @Override
    public void parse(NulsByteBuffer byteBuffer) throws NulsException {
        this.cancelHash = byteBuffer.readBytes(NulsHash.HASH_LENGTH);
        this.orderHash = byteBuffer.readBytes(NulsHash.HASH_LENGTH);
        this.status = byteBuffer.readByte();
        this.cancelAmount = byteBuffer.readBigInteger();
    }

    public byte[] getOrderHash() {
        return orderHash;
    }

    public void setOrderHash(byte[] orderHash) {
        this.orderHash = orderHash;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public BigInteger getCancelAmount() {
        return cancelAmount;
    }

    public void setCancelAmount(BigInteger cancelAmount) {
        this.cancelAmount = cancelAmount;
    }

    public byte[] getCancelHash() {
        return cancelHash;
    }

    public void setCancelHash(byte[] cancelHash) {
        this.cancelHash = cancelHash;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"cancelHash\":")
                .append('\"').append(HexUtil.encode(cancelHash)).append('\"');
        sb.append(",\"orderHash\":")
                .append('\"').append(HexUtil.encode(orderHash)).append('\"');
        sb.append(",\"status\":")
                .append(status);
        sb.append(",\"cancelAmount\":")
                .append('\"').append(cancelAmount).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
