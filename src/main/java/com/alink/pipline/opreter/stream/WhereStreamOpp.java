package com.alink.pipline.opreter.stream;

import com.alibaba.alink.operator.stream.StreamOperator;
import com.alibaba.alink.operator.stream.sql.WhereStreamOp;
import com.alink.pipline.entry.Node;
import com.alink.pipline.opreter.OperterProcess;

import java.util.HashMap;

/**
 * Created by edc on 2020/8/21 下午2:14
 */
public class WhereStreamOpp implements OperterProcess {
    @Override
    public Node process(Node inNode) {
        HashMap<String, String> param = inNode.getNodeParam();

        StreamOperator op = new WhereStreamOp()
                .setClause(param.getOrDefault("clause","category='Iris-setosa'"));

        StreamOperator op1 = op.linkFrom(inNode.getFixedOParray());
        inNode.addFixedOP(op1);

        return inNode;
    }
}