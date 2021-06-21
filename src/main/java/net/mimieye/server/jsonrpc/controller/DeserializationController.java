/**
 * MIT License
 * <p>
 * Copyright (c) 2017-2018 nuls.io
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
package net.mimieye.server.jsonrpc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.nuls.base.basic.NulsByteBuffer;
import io.nuls.base.data.Transaction;
import io.nuls.core.exception.NulsException;
import io.nuls.v2.util.ListUtil;
import net.mimieye.core.core.annotation.Controller;
import net.mimieye.core.core.annotation.RpcMethod;
import net.mimieye.core.crypto.HexUtil;
import net.mimieye.core.log.Log;
import net.mimieye.core.model.StringUtils;
import net.mimieye.core.parse.JSONUtils;
import net.mimieye.model.dto.TransactionDto;
import net.mimieye.model.jsonrpc.RpcErrorCode;
import net.mimieye.model.jsonrpc.RpcResult;
import net.mimieye.model.jsonrpc.RpcResultError;
import net.mimieye.model.txdata.MultiSignTxSignature;
import net.mimieye.model.txdata.TransactionSignature;
import net.mimieye.server.utils.AppUtil;

import java.util.List;

/**
 * @author: PierreLuo
 * @date: 2019-12-09
 */
@Controller
public class DeserializationController {

    @RpcMethod("deserializationTx")
    public RpcResult deserializationTx(List<Object> params) {
        int chainId;
        String txHex;
        String prefix = null;
        try {
            chainId = Integer.parseInt(params.get(0).toString());
        } catch (Exception e) {
            return RpcResult.paramError("[chainId] is inValid");
        }
        try {
            txHex = (String) params.get(1);
        } catch (Exception e) {
            return RpcResult.paramError("[txHex] is inValid");
        }
        if(StringUtils.isBlank(txHex)) {
            return RpcResult.paramError("[txHex] is empty");
        }
        if(params.size() > 2) {
            try {
                prefix = (String) params.get(2);
                AppUtil.addressPrefix(chainId, prefix);
            } catch (Exception e) {
                return RpcResult.paramError("[prefix] is inValid");
            }
        }
        //Object resultObj = null;
        TransactionDto txDto = null;
        String txDataJson = null;
        try {
            Transaction tx = new Transaction();
            tx.parse(new NulsByteBuffer(HexUtil.decode(txHex)));
            txDto = new TransactionDto(tx);
            if(tx.getTransactionSignature() != null) {
                String transactionSignatureJson = null;
                if (!tx.isMultiSignTx()) {
                    TransactionSignature ts = new TransactionSignature();
                    ts.parse(tx.getTransactionSignature(), 0);
                    transactionSignatureJson = ts.toString();
                } else {
                    MultiSignTxSignature mts = new MultiSignTxSignature();
                    mts.parse(tx.getTransactionSignature(), 0);
                    transactionSignatureJson = mts.toString();
                }
                if(StringUtils.isNotBlank(transactionSignatureJson)) {
                    txDto.setTransactionSignature(JSONUtils.json2map(transactionSignatureJson));
                }
            }
            txDataJson = AppUtil.parseTxDataJsonII(tx.getType(), tx.getTxData());
            if(StringUtils.isNotBlank(txDataJson)) {
                txDto.setTxData(JSONUtils.json2map(txDataJson));
            }
            //resultObj = JSONUtils.json2map(JSONUtils.obj2json(txDto));
        } catch (Exception e) {
            if(StringUtils.isNotBlank(txDataJson)) {
                Log.warn("txDataJson is {}", txDataJson);
            }
            Log.error(e);
            return RpcResult.failed(new RpcResultError(RpcErrorCode.TX_PARSE_ERROR, e.getMessage()));
        }
        return RpcResult.success(txDto);
    }

    @RpcMethod("deserializationTxData")
    public RpcResult deserializationTxData(List<Object> params) {
        int chainId;
        int txType;
        String txDataHex;
        String prefix = null;
        try {
            chainId = Integer.parseInt(params.get(0).toString());
        } catch (Exception e) {
            return RpcResult.paramError("[chainId] is inValid");
        }
        try {
            txType = Integer.parseInt(params.get(1).toString());
        } catch (Exception e) {
            return RpcResult.paramError("[txType] is inValid");
        }
        try {
            txDataHex = (String) params.get(2);
        } catch (Exception e) {
            return RpcResult.paramError("[txDataHex] is inValid");
        }
        if(StringUtils.isBlank(txDataHex)) {
            return RpcResult.paramError("[txDataHex] is empty");
        }
        if(params.size() > 3) {
            try {
                prefix = (String) params.get(3);
                AppUtil.addressPrefix(chainId, prefix);
            } catch (Exception e) {
                return RpcResult.paramError("[prefix] is inValid");
            }
        }
        Object txDataObj = null;
        try {
            String txDataJson = AppUtil.parseTxDataJsonII(txType, HexUtil.decode(txDataHex));
            if(StringUtils.isNotBlank(txDataJson)) {
                txDataObj = JSONUtils.json2map(txDataJson);
            }
        } catch (Exception e) {
            Log.error(e);
            return RpcResult.failed(new RpcResultError(RpcErrorCode.TX_PARSE_ERROR, e.getMessage()));
        }
        return RpcResult.success(txDataObj);
    }

    @Deprecated
    @RpcMethod("deserializationBlockHeader")
    public RpcResult deserializationBlockHeader(List<Object> params) {
        int chainId;
        String blockHeaderHex;
        try {
            chainId = Integer.parseInt(params.get(0).toString());
        } catch (Exception e) {
            return RpcResult.paramError("[chainId] is inValid");
        }
        try {
            blockHeaderHex = (String) params.get(1);
        } catch (Exception e) {
            return RpcResult.paramError("[blockHeaderHex] is inValid");
        }
        return RpcResult.success("jsonrpc ok");
    }

    @Deprecated
    @RpcMethod("deserializationBlockData")
    public RpcResult deserializationBlockData(List<Object> params) {
        int chainId;
        String blockDataHex;
        try {
            chainId = Integer.parseInt(params.get(0).toString());
        } catch (Exception e) {
            return RpcResult.paramError("[chainId] is inValid");
        }
        try {
            blockDataHex = (String) params.get(1);
        } catch (Exception e) {
            return RpcResult.paramError("[blockDataHex] is inValid");
        }
        return RpcResult.success("jsonrpc ok");
    }
}
