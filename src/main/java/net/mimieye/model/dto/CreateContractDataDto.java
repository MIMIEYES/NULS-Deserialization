package net.mimieye.model.dto;

import io.nuls.base.basic.AddressTool;
import io.nuls.core.crypto.HexUtil;
import net.mimieye.model.txdata.ContractData;
import net.mimieye.model.txdata.CreateContractData;

public class CreateContractDataDto {
    private String sender;
    private String contractAddress;
    private String alias;
    private String hexCode;
    private long gasLimit;
    private long price;
    private String[][] args;

    public CreateContractDataDto(ContractData contractData) {
        CreateContractData create = (CreateContractData) contractData;
        this.sender = AddressTool.getStringAddressByBytes(create.getSender());
        this.contractAddress = AddressTool.getStringAddressByBytes(create.getContractAddress());
        this.alias = create.getAlias();
        this.hexCode = HexUtil.encode(create.getCode());
        this.gasLimit = create.getGasLimit();
        this.price = create.getPrice();
        this.args = create.getArgs();
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getHexCode() {
        return hexCode;
    }

    public void setHexCode(String hexCode) {
        this.hexCode = hexCode;
    }

    public long getGasLimit() {
        return gasLimit;
    }

    public void setGasLimit(long gasLimit) {
        this.gasLimit = gasLimit;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String[][] getArgs() {
        return args;
    }

    public void setArgs(String[][] args) {
        this.args = args;
    }
}
