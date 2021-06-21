package net.mimieye.model.txdata.nerve.swap;

import io.nuls.base.basic.NulsByteBuffer;
import io.nuls.base.basic.NulsOutputStreamBuffer;
import io.nuls.base.data.BaseNulsData;
import io.nuls.core.exception.NulsException;
import io.nuls.core.parse.SerializeUtils;

import java.io.IOException;
import java.math.BigInteger;

/**
 * @author Niels
 */
public class FarmCreateData extends BaseNulsData {
    private NerveToken stakeToken;
    private NerveToken syrupToken;
    private BigInteger syrupPerBlock;
    private BigInteger totalSyrupAmount;
    private long startBlockHeight;
    private long lockedTime;

    @Override
    protected void serializeToStream(NulsOutputStreamBuffer stream) throws IOException {
        stream.writeUint16(stakeToken.getChainId());
        stream.writeUint16(stakeToken.getAssetId());
        stream.writeUint16(syrupToken.getChainId());
        stream.writeUint16(syrupToken.getAssetId());
        stream.writeBigInteger(syrupPerBlock);
        stream.writeBigInteger(totalSyrupAmount);
        stream.writeInt64(startBlockHeight);
        stream.writeInt64(lockedTime);
    }

    @Override
    public void parse(NulsByteBuffer byteBuffer) throws NulsException {
        this.stakeToken = new NerveToken(byteBuffer.readUint16(), byteBuffer.readUint16());
        this.syrupToken = new NerveToken(byteBuffer.readUint16(), byteBuffer.readUint16());
        this.syrupPerBlock = byteBuffer.readBigInteger();
        this.totalSyrupAmount = byteBuffer.readBigInteger();
        this.startBlockHeight = byteBuffer.readInt64();
        this.lockedTime = byteBuffer.readInt64();
    }

    @Override
    public int size() {
        int size = 4;
        size += 4;
        size += SerializeUtils.sizeOfBigInteger();
        size += SerializeUtils.sizeOfBigInteger();
        size += SerializeUtils.sizeOfInt64();
        size += SerializeUtils.sizeOfInt64();
        return size;
    }

    public NerveToken getStakeToken() {
        return stakeToken;
    }

    public void setStakeToken(NerveToken stakeToken) {
        this.stakeToken = stakeToken;
    }

    public NerveToken getSyrupToken() {
        return syrupToken;
    }

    public void setSyrupToken(NerveToken syrupToken) {
        this.syrupToken = syrupToken;
    }

    public BigInteger getSyrupPerBlock() {
        return syrupPerBlock;
    }

    public void setSyrupPerBlock(BigInteger syrupPerBlock) {
        this.syrupPerBlock = syrupPerBlock;
    }

    public long getStartBlockHeight() {
        return startBlockHeight;
    }

    public void setStartBlockHeight(long startBlockHeight) {
        this.startBlockHeight = startBlockHeight;
    }

    public long getLockedTime() {
        return lockedTime;
    }

    public void setLockedTime(long lockedTime) {
        this.lockedTime = lockedTime;
    }

    public BigInteger getTotalSyrupAmount() {
        return totalSyrupAmount;
    }

    public void setTotalSyrupAmount(BigInteger totalSyrupAmount) {
        this.totalSyrupAmount = totalSyrupAmount;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"stakeToken\":")
                .append(stakeToken);
        sb.append(",\"syrupToken\":")
                .append(syrupToken);
        sb.append(",\"syrupPerBlock\":")
                .append('\"').append(syrupPerBlock).append('\"');
        sb.append(",\"totalSyrupAmount\":")
                .append('\"').append(totalSyrupAmount).append('\"');
        sb.append(",\"startBlockHeight\":")
                .append(startBlockHeight);
        sb.append(",\"lockedTime\":")
                .append(lockedTime);
        sb.append('}');
        return sb.toString();
    }
}
