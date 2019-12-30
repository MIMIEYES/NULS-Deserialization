/**
 * MIT License
 * <p>
 * Copyright (c) 2017-2019 nuls.io
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
package net.mimieye.model.dto;

import io.nuls.base.RPCUtil;
import io.nuls.base.data.CoinData;
import io.nuls.base.data.Transaction;
import io.nuls.core.exception.NulsException;
import io.nuls.core.model.ByteUtils;
import io.nuls.core.model.DateUtils;
import io.nuls.core.rpc.model.ApiModel;
import io.nuls.core.rpc.model.ApiModelProperty;
import io.nuls.core.rpc.model.TypeDescriptor;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: PierreLuo
 * @date: 2019-06-29
 */
@Data
public class TransactionDto {
    
    private String hash;
    
    private int type;
    
    private String time;
    
    private long blockHeight = -1L;
    
    private String blockHash;
    
    private String remark;
    
    //private String transactionSignatureHex;
    private Object transactionSignature;
    
    private int status = 0;
    
    private int size;
    
    private int inBlockIndex;
    
    private List<CoinFromDto> from;
    
    private List<CoinToDto> to;

    private Object txData;



    public TransactionDto() {
    }

    public TransactionDto(Transaction transaction) throws NulsException {
        this.blockHeight = transaction.getBlockHeight();
        this.status = transaction.getStatus().getStatus();
        this.hash = transaction.getHash().toString();
        this.remark = ByteUtils.asString(transaction.getRemark());
        this.inBlockIndex = transaction.getInBlockIndex();
        this.size = transaction.getSize();
        this.time = DateUtils.timeStamp2DateStr(transaction.getTime() * 1000);
        //this.transactionSignatureHex = RPCUtil.encode(transaction.getTransactionSignature());
        this.type = transaction.getType();
        if (transaction.getCoinData() != null) {
            CoinData coinData = transaction.getCoinDataInstance();
            this.from = coinData.getFrom().stream().map(from -> new CoinFromDto(from)).collect(Collectors.toList());
            this.to = coinData.getTo().stream().map(to -> new CoinToDto(to)).collect(Collectors.toList());
        }
    }

}
