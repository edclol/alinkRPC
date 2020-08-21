package com.alink.pipline.opreter.stream;

import com.alibaba.alink.operator.stream.StreamOperator;
import com.alibaba.alink.operator.stream.sql.AsStreamOp;
import com.alibaba.alink.operator.stream.sql.SelectStreamOp;
import com.alink.pipline.entry.Node;
import com.alink.pipline.opreter.OperterProcess;

import java.util.HashMap;

/**
 * Created by edc on 2020/8/21 下午2:11
 */
public class SelectStreamOpp implements OperterProcess {
    @Override
    public Node process(Node inNode) {
        HashMap<String, String> param = inNode.getNodeParam();

        StreamOperator op = new SelectStreamOp()
                .setClause(param.getOrDefault("clause","category as label"));

        StreamOperator op1 = op.linkFrom(inNode.getFixedOParray());
        inNode.addFixedOP(op1);

        return inNode;
    }
}
