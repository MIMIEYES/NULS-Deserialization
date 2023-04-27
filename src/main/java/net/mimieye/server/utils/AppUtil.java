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
import io.nuls.base.data.BaseNulsData;
import io.nuls.core.constant.TxType;
import net.mimieye.core.crypto.HexUtil;
import net.mimieye.core.model.StringUtils;
import net.mimieye.model.txdata.*;
import net.mimieye.model.txdata.nerve.*;
import net.mimieye.model.txdata.nerve.dex.*;
import net.mimieye.model.txdata.nerve.swap.*;
import net.mimieye.model.txdata.nerve.swap.linkswap.StableLpSwapTradeData;
import net.mimieye.model.txdata.nerve.swap.linkswap.SwapTradeStableRemoveLpData;
import net.mimieye.model.txdata.nerve.swap.stable.CreateStablePairData;
import net.mimieye.model.txdata.nerve.swap.stable.StableAddLiquidityData;
import net.mimieye.model.txdata.nerve.swap.stable.StableRemoveLiquidityData;
import net.mimieye.model.txdata.nerve.swap.stable.StableSwapTradeData;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: PierreLuo
 * @date: 2019-12-09
 */
public class AppUtil {

    public static void addressPrefix(int chainId, String prefix) {
        if (StringUtils.isBlank(prefix)) {
            return;
        }
        if(chainId != 1 || chainId != 2) {
            AddressTool.addPrefix(chainId, prefix);
        }
    }

    public static Map<Integer, Class<? extends BaseNulsData>> DATA_MAP = new HashMap<>();
    static {
        DATA_MAP.put(TxType.ACCOUNT_ALIAS, Alias.class);
        DATA_MAP.put(TxType.CONTRACT_CREATE_AGENT, Agent.class);
        DATA_MAP.put(TxType.REGISTER_AGENT, Agent.class);
        DATA_MAP.put(TxType.CONTRACT_DEPOSIT, Deposit.class);
        DATA_MAP.put(TxType.DEPOSIT, Deposit.class);
        DATA_MAP.put(TxType.CONTRACT_CANCEL_DEPOSIT, CancelDeposit.class);
        DATA_MAP.put(TxType.CANCEL_DEPOSIT, CancelDepositNerve.class);
        DATA_MAP.put(TxType.YELLOW_PUNISH, YellowPunishData.class);
        DATA_MAP.put(TxType.RED_PUNISH, RedPunishData.class);
        DATA_MAP.put(TxType.CONTRACT_STOP_AGENT, StopAgent.class);
        DATA_MAP.put(TxType.STOP_AGENT, StopAgent.class);
        DATA_MAP.put(TxType.CROSS_CHAIN, CrossTxData.class);
        DATA_MAP.put(TxType.CREATE_CONTRACT, CreateContractData.class);
        DATA_MAP.put(TxType.CALL_CONTRACT, CallContractData.class);
        DATA_MAP.put(TxType.DELETE_CONTRACT, DeleteContractData.class);
        DATA_MAP.put(TxType.CONTRACT_TRANSFER, ContractTransferData.class);
        DATA_MAP.put(TxType.VERIFIER_CHANGE, VerifierChangeData.class);
        DATA_MAP.put(TxType.VERIFIER_INIT, VerifierInitData.class);

        DATA_MAP.put(TxType.CHANGE_VIRTUAL_BANK, ChangeVirtualBankTxData.class);
        DATA_MAP.put(TxType.CONFIRM_CHANGE_VIRTUAL_BANK, ConfirmedChangeVirtualBankTxData.class);
        DATA_MAP.put(TxType.CONFIRM_PROPOSAL, ConfirmProposalTxData.class);
        //DATA_MAP.put(TxType.CONFIRM_PROPOSAL, ConfirmUpgradeTxData.class);
        //DATA_MAP.put(TxType.CONFIRM_PROPOSAL, ProposalExeBusinessData.class);
        DATA_MAP.put(TxType.CONFIRM_HETEROGENEOUS_RESET_VIRTUAL_BANK, ConfirmResetVirtualBankTxData.class);
        DATA_MAP.put(TxType.CONFIRM_WITHDRAWAL, ConfirmWithdrawalTxData.class);
        DATA_MAP.put(TxType.DISTRIBUTION_FEE, DistributionFeeTxData.class);
        DATA_MAP.put(TxType.HETEROGENEOUS_CONTRACT_ASSET_REG_COMPLETE, HeterogeneousContractAssetRegCompleteTxData.class);
        DATA_MAP.put(TxType.HETEROGENEOUS_CONTRACT_ASSET_REG_PENDING, HeterogeneousContractAssetRegPendingTxData.class);
        DATA_MAP.put(TxType.HETEROGENEOUS_MAIN_ASSET_REG, HeterogeneousMainAssetRegTxData.class);
        DATA_MAP.put(TxType.INITIALIZE_HETEROGENEOUS, InitializeHeterogeneousTxData.class);
        DATA_MAP.put(TxType.PROPOSAL, ProposalTxData.class);
        DATA_MAP.put(TxType.RECHARGE, RechargeTxData.class);
        DATA_MAP.put(TxType.RECHARGE_UNCONFIRMED, RechargeUnconfirmedTxData.class);
        DATA_MAP.put(TxType.RESET_HETEROGENEOUS_VIRTUAL_BANK, ResetVirtualBankTxData.class);
        //DATA_MAP.put(TxType.VERIFIER_INIT, SubmitHeterogeneousAddressTxData.class);
        DATA_MAP.put(TxType.VOTE_PROPOSAL, VoteProposalTxData.class);
        DATA_MAP.put(TxType.WITHDRAWAL_ADDITIONAL_FEE, WithdrawalAdditionalFeeTxData.class);
        DATA_MAP.put(TxType.WITHDRAWAL_HETEROGENEOUS_SEND, WithdrawalHeterogeneousSendTxData.class);
        DATA_MAP.put(TxType.WITHDRAWAL, WithdrawalTxData.class);
        DATA_MAP.put(TxType.ONE_CLICK_CROSS_CHAIN_UNCONFIRMED, OneClickCrossChainUnconfirmedTxData.class);
        DATA_MAP.put(TxType.ONE_CLICK_CROSS_CHAIN, OneClickCrossChainTxData.class);
        DATA_MAP.put(TxType.ADD_FEE_OF_CROSS_CHAIN_BY_CROSS_CHAIN, WithdrawalAddFeeByCrossChainTxData.class);

        //SWAP
        DATA_MAP.put(TxType.CREATE_SWAP_PAIR, CreatePairData.class);
        DATA_MAP.put(TxType.FARM_CREATE, FarmCreateData.class);
        DATA_MAP.put(TxType.SWAP_TRADE, SwapTradeData.class);
        DATA_MAP.put(TxType.SWAP_ADD_LIQUIDITY, AddLiquidityData.class);
        DATA_MAP.put(TxType.SWAP_REMOVE_LIQUIDITY, RemoveLiquidityData.class);
        DATA_MAP.put(TxType.FARM_STAKE, FarmStakeChangeData.class);
        DATA_MAP.put(TxType.FARM_WITHDRAW, FarmStakeChangeData.class);
        DATA_MAP.put(TxType.FARM_UPDATE, FarmUpdateData.class);
        DATA_MAP.put(TxType.CREATE_SWAP_PAIR_STABLE_COIN, CreateStablePairData.class);
        DATA_MAP.put(TxType.SWAP_TRADE_STABLE_COIN, StableSwapTradeData.class);
        DATA_MAP.put(TxType.SWAP_ADD_LIQUIDITY_STABLE_COIN, StableAddLiquidityData.class);
        DATA_MAP.put(TxType.SWAP_REMOVE_LIQUIDITY_STABLE_COIN, StableRemoveLiquidityData.class);
        DATA_MAP.put(TxType.SWAP_STABLE_LP_SWAP_TRADE, StableLpSwapTradeData.class);
        DATA_MAP.put(TxType.SWAP_TRADE_SWAP_STABLE_REMOVE_LP, SwapTradeStableRemoveLpData.class);
        DATA_MAP.put(TxType.BLOCK_ACCOUNT_NERVE, AccountBlockData.class);
        DATA_MAP.put(TxType.UNBLOCK_ACCOUNT_NERVE, AccountBlockData.class);

        //DEX
        DATA_MAP.put(TxType.COIN_TRADING, CoinTrading.class);
        DATA_MAP.put(TxType.TRADING_ORDER, TradingOrder.class);
        DATA_MAP.put(TxType.TRADING_ORDER_CANCEL, TradingOrderCancel.class);
        DATA_MAP.put(TxType.TRADING_DEAL, TradingDeal.class);
        DATA_MAP.put(TxType.EDIT_COIN_TRADING, EditCoinTrading.class);
        DATA_MAP.put(TxType.ORDER_CANCEL_CONFIRM, TradingCancelTxData.class);

    }

    public static Map<Integer, Class<? extends BaseNulsData>> NULS_DATA_MAP = new HashMap<>();
    static {
        NULS_DATA_MAP.put(TxType.ACCOUNT_ALIAS, Alias.class);
        NULS_DATA_MAP.put(TxType.CONTRACT_CREATE_AGENT, Agent.class);
        NULS_DATA_MAP.put(TxType.REGISTER_AGENT, Agent.class);
        NULS_DATA_MAP.put(TxType.CONTRACT_DEPOSIT, Deposit.class);
        NULS_DATA_MAP.put(TxType.DEPOSIT, Deposit.class);
        NULS_DATA_MAP.put(TxType.CONTRACT_CANCEL_DEPOSIT, CancelDeposit.class);
        NULS_DATA_MAP.put(TxType.CANCEL_DEPOSIT, CancelDeposit.class);
        NULS_DATA_MAP.put(TxType.YELLOW_PUNISH, YellowPunishData.class);
        NULS_DATA_MAP.put(TxType.RED_PUNISH, RedPunishData.class);
        NULS_DATA_MAP.put(TxType.CONTRACT_STOP_AGENT, StopAgent.class);
        NULS_DATA_MAP.put(TxType.STOP_AGENT, StopAgent.class);
        NULS_DATA_MAP.put(TxType.CROSS_CHAIN, CrossTxData.class);
        NULS_DATA_MAP.put(TxType.CREATE_CONTRACT, CreateContractData.class);
        NULS_DATA_MAP.put(TxType.CALL_CONTRACT, CallContractData.class);
        NULS_DATA_MAP.put(TxType.DELETE_CONTRACT, DeleteContractData.class);
        NULS_DATA_MAP.put(TxType.CONTRACT_TRANSFER, ContractTransferData.class);
        NULS_DATA_MAP.put(TxType.VERIFIER_CHANGE, VerifierChangeData.class);
        NULS_DATA_MAP.put(TxType.VERIFIER_INIT, VerifierInitData.class);
        NULS_DATA_MAP.put(TxType.BLOCK_ACCOUNT, AccountBlockData.class);
        NULS_DATA_MAP.put(TxType.UNBLOCK_ACCOUNT, AccountBlockData.class);

    }
    public static String parseTxDataJsonII(int chainId, int txType, byte[] txData) throws Exception {
        if (txData == null) {
            return null;
        }
        Class<? extends BaseNulsData> aClass;
        if (chainId == 1 || chainId == 2) {
            aClass = NULS_DATA_MAP.get(txType);
        } else {
            aClass = DATA_MAP.get(txType);
        }
        if (aClass == null) {
            return String.format("{\"data\": \"%s\"}", HexUtil.encode(txData));
        }
        BaseNulsData baseNulsData = aClass.getDeclaredConstructor().newInstance();
        baseNulsData.parse(txData, 0);
        return baseNulsData.toString();
    }

}
