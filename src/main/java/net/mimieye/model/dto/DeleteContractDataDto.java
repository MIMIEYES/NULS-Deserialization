package net.mimieye.model.dto;

import io.nuls.base.basic.AddressTool;
import net.mimieye.model.txdata.ContractData;

public class DeleteContractDataDto {
    private String sender;
    private String contractAddress;

    public DeleteContractDataDto(ContractData delete) {
        this.sender = AddressTool.getStringAddressByBytes(delete.getSender());
        this.contractAddress = AddressTool.getStringAddressByBytes(delete.getContractAddress());
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
}
