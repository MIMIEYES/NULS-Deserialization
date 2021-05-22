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

import io.nuls.base.basic.AddressTool;
import io.nuls.v2.util.ListUtil;
import net.mimieye.core.constant.TxType;
import net.mimieye.core.core.annotation.Autowired;
import net.mimieye.core.core.annotation.Controller;
import net.mimieye.core.core.annotation.RpcMethod;
import net.mimieye.core.log.Log;
import net.mimieye.core.model.StringUtils;
import net.mimieye.model.jsonrpc.RpcErrorCode;
import net.mimieye.model.jsonrpc.RpcResult;
import net.mimieye.model.jsonrpc.RpcResultError;
import net.mimieye.server.utils.JsonRpcUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author: PierreLuo
 * @date: 2019-12-09
 */
@Controller
public class ApiController {

    static String MAIN_API_URL = "https://api.nuls.io/jsonrpc/";
    static String TESTNET_API_URL = "http://beta.api.nuls.io/jsonrpc/";
    static Set<Integer> CONTRACT_RESULT_TX_TYPE_SET = new HashSet<>();
    static {
        CONTRACT_RESULT_TX_TYPE_SET.add(TxType.CROSS_CHAIN);
        CONTRACT_RESULT_TX_TYPE_SET.add(TxType.CREATE_CONTRACT);
        CONTRACT_RESULT_TX_TYPE_SET.add(TxType.CALL_CONTRACT);
    }

    @Autowired
    private DeserializationController deserializationController;

    @Deprecated
    @RpcMethod("getTxByHash")
    public RpcResult getTxByHash(List<Object> params) {
        int network, chainId = 0;
        String hash, url = null;
        try {
            network = Integer.parseInt(params.get(0).toString());
        } catch (Exception e) {
            return RpcResult.paramError("[network] is inValid");
        }
        try {
            hash = (String) params.get(1);
        } catch (Exception e) {
            return RpcResult.paramError("[hash] is inValid");
        }
        if(params.size() > 2) {
            try {
                url = (String) params.get(2);
                if(StringUtils.isNotBlank(url)) {
                    if(url.endsWith("/")) {
                        url += "jsonrpc/";
                    } else {
                        url += "/jsonrpc/";
                    }
                }
            } catch (Exception e) {
                return RpcResult.paramError("[url] is inValid");
            }
        }
        try {
            String prefix = null;
            if(network == 1) {
                url = MAIN_API_URL;
                chainId = network;
            } else if(network  == 2) {
                url = TESTNET_API_URL;
                chainId = network;
            } else {
                RpcResult info = JsonRpcUtil.request(url, "info", ListUtil.of());
                Map result = (Map) info.getResult();
                chainId = Integer.parseInt(result.get("chainId").toString());
                prefix = (String) result.get("addressPrefix");
            }
            RpcResult txResult = JsonRpcUtil.request(url, "getTx", ListUtil.of(chainId, hash));
            if(txResult.getError() != null) {
                return txResult;
            }
            Map resultMap = (Map) txResult.getResult();
            int txType = Integer.parseInt(resultMap.get("type").toString());
            Object txDataHex = resultMap.get("txDataHex");
            List fromList = (List) resultMap.get("from");
            if(StringUtils.isBlank(prefix)) {
                if(!fromList.isEmpty()) {
                    prefix = AddressTool.getPrefix(((Map) fromList.get(0)).get("address").toString());
                } else {
                    List toList = (List) resultMap.get("to");
                    if(!toList.isEmpty()) {
                        prefix = AddressTool.getPrefix(((Map) toList.get(0)).get("address").toString());
                    }
                }
            }
            if(txDataHex != null) {
                RpcResult txDataResult = deserializationController.deserializationTxData(ListUtil.of(chainId, txType, txDataHex, prefix));
                resultMap.put("txData", txDataResult.getResult());
            }
            if(CONTRACT_RESULT_TX_TYPE_SET.contains(txType)) {
                RpcResult contractTxResult = JsonRpcUtil.request(url, "getContractTxResult", ListUtil.of(chainId, hash));
                resultMap.put("contractResult", contractTxResult.getResult());
            }
            return RpcResult.success(resultMap);
        } catch (Exception e) {
            Log.error(e);
            return RpcResult.failed(new RpcResultError(RpcErrorCode.TX_PARSE_ERROR, e.getMessage()));
        }
    }

}
