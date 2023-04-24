package net.mimieye.server.jsonrpc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.nuls.base.basic.AddressTool;
import io.nuls.v2.util.ListUtil;
import net.mimieye.core.parse.JSONUtils;
import net.mimieye.model.jsonrpc.RpcResult;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DeserializationControllerTest {

    @Before
    public void setUp() throws Exception {
        AddressTool.addPrefix(5, "TNVT");
        AddressTool.addPrefix(9, "NERVE");
    }

    @Test
    public void test() throws JsonProcessingException {
        DeserializationController de = new DeserializationController();
        RpcResult rpcResult = de.deserializationTx(ListUtil.of(5, "5300902246641c737761705472616465537461626c6552656d6f76654c702074657374490000000000000000000000000000000000000000000000000000000000000000050001b9978dbea4abebb613b6be2d0d66b4179d2511cb00bc23466402050001000500660005005a008c0117050001b9978dbea4abebb613b6be2d0d66b4179d2511cb050001000000c84e676dc11b000000000000000000000000000000000000000000000000083816bba9840a80a2000117050004247ca1adaac395ee8eaab7549c1648e252f17011050001000000c84e676dc11b00000000000000000000000000000000000000000000000000000000000000006a210369b20002bc58c74cb6fd5ef564f603834393f53bed20c3314b4b7aba8286a7e0473045022100e0713c4e6e2f58653705e983adbf384ce436b476d101de479bc24d3a260622cc022037249ccaf1cee27b9d3ea4a6fee7f39e2a597565085cb0c635fa04cb57afec19"));
        Object result = rpcResult.getResult();
        System.out.println(JSONUtils.obj2PrettyJson(result));
    }
}