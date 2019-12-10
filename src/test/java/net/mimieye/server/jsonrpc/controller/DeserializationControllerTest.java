package net.mimieye.server.jsonrpc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.nuls.v2.util.ListUtil;
import net.mimieye.core.parse.JSONUtils;
import net.mimieye.model.jsonrpc.RpcResult;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DeserializationControllerTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void test() throws JsonProcessingException {
        DeserializationController de = new DeserializationController();
        RpcResult rpcResult = de.deserializationTx(ListUtil.of(2, "150037fa2b5d005700409452a3030000000000000000000000000000000000000000000000000000020002aefee5362dad1a404814709bfe1a9d91e988d6ef5b281974bdc4ac6b0590ea93079e4ef52ceecdb7db37737b32eb5e8b9e60e6618c0117020002aefee5362dad1a404814709bfe1a9d91e988d6ef0200010000409452a30300000000000000000000000000000000000000000000000000000890f46669c10fb6cd000117020002aefee5362dad1a404814709bfe1a9d91e988d6ef0200010000409452a3030000000000000000000000000000000000000000000000000000ffffffffffffffff00"));
        Object result = rpcResult.getResult();
        System.out.println(JSONUtils.obj2PrettyJson(result));
        String a0 = "";
        String a1 = "";
        rpcResult = de.deserializationTxData(ListUtil.of(2, 15, a0 + a1));
        result = rpcResult.getResult();
        System.out.println(JSONUtils.obj2PrettyJson(result));

    }
}