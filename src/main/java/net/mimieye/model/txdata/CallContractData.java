package net.mimieye.model.txdata;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.nuls.base.basic.NulsByteBuffer;
import io.nuls.base.basic.NulsOutputStreamBuffer;
import io.nuls.base.data.Address;
import io.nuls.base.data.BaseNulsData;
import io.nuls.core.exception.NulsException;
import io.nuls.core.parse.SerializeUtils;
import net.mimieye.core.parse.JSONUtils;
import net.mimieye.model.dto.CallContractDataDto;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

public class CallContractData  extends BaseNulsData implements ContractData {

    private byte[] sender;
    private byte[] contractAddress;
    private BigInteger value;
    private long gasLimit;
    private long price;
    private String methodName;
    private String methodDesc;
    private byte argsCount;
    private String[][] args;


    @Override
    public int size() {
        int size = 0;
        size += Address.ADDRESS_LENGTH;
        size += Address.ADDRESS_LENGTH;
        size += SerializeUtils.sizeOfBigInteger();
        size += SerializeUtils.sizeOfInt64();
        size += SerializeUtils.sizeOfInt64();

        size += SerializeUtils.sizeOfString(methodName);
        size += SerializeUtils.sizeOfString(methodDesc);
        size += 1;
        if (args != null) {
            for (String[] arg : args) {
                if (arg == null) {
                    size += 1;
                } else {
                    size += 1;
                    for (String str : arg) {
                        size += SerializeUtils.sizeOfString(str);
                    }
                }
            }
        }
        return size;
    }

    @Override
    protected void serializeToStream(NulsOutputStreamBuffer stream) throws IOException {
        stream.write(sender);
        stream.write(contractAddress);
        stream.writeBigInteger(value);
        stream.writeInt64(gasLimit);
        stream.writeInt64(price);

        stream.writeString(methodName);
        stream.writeString(methodDesc);
        stream.write(argsCount);
        if (args != null) {
            for (String[] arg : args) {
                if (arg == null) {
                    stream.write((byte) 0);
                } else {
                    stream.write((byte) arg.length);
                    for (String str : arg) {
                        stream.writeString(str);
                    }
                }
            }
        }
    }

    @Override
    public void parse(NulsByteBuffer byteBuffer) throws NulsException {
        this.sender = byteBuffer.readBytes(Address.ADDRESS_LENGTH);
        this.contractAddress = byteBuffer.readBytes(Address.ADDRESS_LENGTH);
        this.value = byteBuffer.readBigInteger();
        this.gasLimit = byteBuffer.readInt64();
        this.price = byteBuffer.readInt64();

        this.methodName = byteBuffer.readString();
        this.methodDesc = byteBuffer.readString();
        this.argsCount = byteBuffer.readByte();
        byte length = this.argsCount;
        this.args = new String[length][];
        for (byte i = 0; i < length; i++) {
            byte argCount = byteBuffer.readByte();
            if (argCount == 0) {
                args[i] = new String[0];
            } else {
                String[] arg = new String[argCount];
                for (byte k = 0; k < argCount; k++) {
                    arg[k] = byteBuffer.readString();
                }
                args[i] = arg;
            }
        }
    }

    @Override
    public byte[] getSender() {
        return sender;
    }

    @Override
    public byte[] getCode() {
        return null;
    }

    public void setSender(byte[] sender) {
        this.sender = sender;
    }

    @Override
    public byte[] getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(byte[] contractAddress) {
        this.contractAddress = contractAddress;
    }

    @Override
    public BigInteger getValue() {
        return value;
    }

    public void setValue(BigInteger value) {
        this.value = value;
    }

    @Override
    public long getGasLimit() {
        return gasLimit;
    }

    public void setGasLimit(long gasLimit) {
        this.gasLimit = gasLimit;
    }

    @Override
    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    @Override
    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    @Override
    public String getMethodDesc() {
        return methodDesc;
    }

    public void setMethodDesc(String methodDesc) {
        this.methodDesc = methodDesc;
    }

    public byte getArgsCount() {
        return argsCount;
    }

    public void setArgsCount(byte argsCount) {
        this.argsCount = argsCount;
    }

    @Override
    public String[][] getArgs() {
        return args;
    }

    public void setArgs(String[][] args) {
        this.args = args;
    }


    public Set<byte[]> getAddresses() {
        Set<byte[]> addressSet = new HashSet<>();
        addressSet.add(contractAddress);
        return addressSet;
    }

    @Override
    public String toString() {
        try {
            return JSONUtils.obj2json(new CallContractDataDto(this));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
