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
package net.mimieye.server.utils;

import io.nuls.base.basic.AddressTool;
import io.nuls.core.constant.TxType;
import io.nuls.core.exception.NulsException;
import net.mimieye.model.txdata.*;

/**
 * @author: PierreLuo
 * @date: 2019-12-09
 */
public class AppUtil {

    public static void addressPrefix(int chainId, String prefix) {
        if(chainId != 1 || chainId != 2) {
            AddressTool.addPrefix(chainId, prefix);
        }
    }

    public static String parseTxDataJson(int txType, byte[] txData) throws NulsException {
        String txDataJson = null;
        switch (txType) {
            case TxType.ACCOUNT_ALIAS :
                Alias alias = new Alias();
                alias.parse(txData, 0);
                txDataJson = alias.toString();
                break;
            case TxType.CONTRACT_CREATE_AGENT :
            case TxType.REGISTER_AGENT :
                Agent agent = new Agent();
                agent.parse(txData, 0);
                txDataJson = agent.toString();
                break;
            case TxType.CONTRACT_DEPOSIT :
            case TxType.DEPOSIT :
                Deposit deposit = new Deposit();
                deposit.parse(txData, 0);
                txDataJson = deposit.toString();
                break;
            case TxType.CONTRACT_CANCEL_DEPOSIT :
            case TxType.CANCEL_DEPOSIT :
                CancelDeposit cancelDeposit = new CancelDeposit();
                cancelDeposit.parse(txData, 0);
                txDataJson = cancelDeposit.toString();
                break;
            case TxType.YELLOW_PUNISH :
                YellowPunishData yellowPunishData = new YellowPunishData();
                yellowPunishData.parse(txData, 0);
                txDataJson = yellowPunishData.toString();
                break;
            case TxType.RED_PUNISH :
                RedPunishData redPunishData = new RedPunishData();
                redPunishData.parse(txData, 0);
                txDataJson = redPunishData.toString();
                break;
            case TxType.CONTRACT_STOP_AGENT :
            case TxType.STOP_AGENT :
                StopAgent stopAgent = new StopAgent();
                stopAgent.parse(txData, 0);
                txDataJson = stopAgent.toString();
                break;
            case TxType.CROSS_CHAIN :
                CrossTxData crossTxData = new CrossTxData();
                crossTxData.parse(txData, 0);
                txDataJson = crossTxData.toString();
                break;
            case TxType.CREATE_CONTRACT :
                CreateContractData createContractData = new CreateContractData();
                createContractData.parse(txData, 0);
                txDataJson = createContractData.toString();
                break;
            case TxType.CALL_CONTRACT :
                CallContractData callContractData = new CallContractData();
                callContractData.parse(txData, 0);
                txDataJson = callContractData.toString();
                break;
            case TxType.DELETE_CONTRACT :
                DeleteContractData deleteContractData = new DeleteContractData();
                deleteContractData.parse(txData, 0);
                txDataJson = deleteContractData.toString();
                break;
            case TxType.CONTRACT_TRANSFER :
                ContractTransferData contractTransferData = new ContractTransferData();
                contractTransferData.parse(txData, 0);
                txDataJson = contractTransferData.toString();
                break;
            case TxType.VERIFIER_CHANGE :
                VerifierChangeData verifierChangeData = new VerifierChangeData();
                verifierChangeData.parse(txData, 0);
                txDataJson = verifierChangeData.toString();
                break;
            case TxType.VERIFIER_INIT :
                VerifierInitData verifierInitData = new VerifierInitData();
                verifierInitData.parse(txData, 0);
                txDataJson = verifierInitData.toString();
                break;
            default:
        }
        return txDataJson;
    }
}