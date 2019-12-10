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
        rpcResult = de.deserializationTxData(ListUtil.of(2, 5, "00e4b9fa91020000000000000000000000000000000000000000000000000000010002a838b812b0cde89ed43402bdcd4830da8fa6d9935820c4e21dcf018751e2b28b223a980a08906c1ead6edb9c02f003fbe3af4ae8"));
        result = rpcResult.getResult();
        System.out.println(JSONUtils.obj2PrettyJson(result));

    }
}