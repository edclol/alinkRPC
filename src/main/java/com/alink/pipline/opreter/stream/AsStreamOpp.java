package com.alink.pipline.opreter.stream;

import com.alibaba.alink.operator.stream.StreamOperator;
import com.alibaba.alink.operator.stream.sql.AsStreamOp;
import com.alibaba.alink.operator.stream.sql.FilterStreamOp;
import com.alink.pipline.entry.Node;
import com.alink.pipline.opreter.AlinkStreamService;
import com.alink.pipline.opreter.OperterProcess;

import java.util.HashMap;

/**
 * Created by edc on 2020/8/21 下午2:00
 */
public class AsStreamOpp implements OperterProcess {
    @Override
    public Node process(Node inNode) {

        //得到参数
        HashMap<String, String> param = inNode.getNodeParam();

        //根据参数生成相应的处理算子
        StreamOperator op = new AsStreamOp()
                .setClause(param.getOrDefault("clause","f1,f2,f3"));

        //得到所有前面节点处理过的StreamOP
        StreamOperator[] prevFixedop = AlinkStreamService.getPrevFixedop(inNode);

        //链接到当前处理算子生成新的streamOP
        StreamOperator op1 = op.linkFrom(prevFixedop);

        //将处理好的StreamOP保存到Node里面
        inNode.addFixedOP(op1);

        return inNode;
    }
}
