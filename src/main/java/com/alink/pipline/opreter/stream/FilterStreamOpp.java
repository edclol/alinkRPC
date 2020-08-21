package com.alink.pipline.opreter.stream;

import com.alibaba.alink.operator.batch.BatchOperator;
import com.alibaba.alink.operator.stream.StreamOperator;
import com.alibaba.alink.operator.stream.sql.FilterStreamOp;
import com.alink.pipline.entry.Node;
import com.alink.pipline.opreter.OperterProcess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by edc on 2020/8/20 下午4:33
 */
public class FilterStreamOpp implements OperterProcess {

    @Override
    public Node process(Node inNode) {

        HashMap<String, String> param = inNode.getNodeParam();

        StreamOperator op = new FilterStreamOp()
                .setClause(param.getOrDefault("clause","category='Iris-setosa'"));

        StreamOperator op1 = op.linkFrom(inNode.getFixedOParray());
        inNode.addFixedOP(op1);
        return inNode;
    }
}
