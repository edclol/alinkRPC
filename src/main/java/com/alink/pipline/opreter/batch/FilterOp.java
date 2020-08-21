package com.alink.pipline.opreter.batch;

import com.alibaba.alink.operator.batch.BatchOperator;
import com.alink.pipline.opreter.OperterProcess;

import java.util.List;

/**
 * Created by edc on 2020/8/20 下午4:33
 */
public class FilterOp implements OperterProcess<List<BatchOperator>,List<BatchOperator>> {


    @Override
    public List<BatchOperator> process(List<BatchOperator> batchOperators) {

        BatchOperator batchOperator = batchOperators.get(0);
//        batchOperator.linkFrom()
        return null;
    }
}
