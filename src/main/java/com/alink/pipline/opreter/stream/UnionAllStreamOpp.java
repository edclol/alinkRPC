package com.alink.pipline.opreter.stream;

import com.alibaba.alink.operator.stream.StreamOperator;
import com.alibaba.alink.operator.stream.sql.SelectStreamOp;
import com.alibaba.alink.operator.stream.sql.UnionAllStreamOp;
import com.alink.pipline.entry.Node;
import com.alink.pipline.opreter.OperterProcess;

import java.util.HashMap;

/**
 * Created by edc on 2020/8/21 下午2:13
 */
public class UnionAllStreamOpp implements OperterProcess {
    @Override
    public Node process(Node inNode) {

        StreamOperator op = new UnionAllStreamOp();

        StreamOperator op1 = op.linkFrom(inNode.getFixedOParray());
        inNode.addFixedOP(op1);

        return inNode;
    }
}