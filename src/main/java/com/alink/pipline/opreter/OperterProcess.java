package com.alink.pipline.opreter;

import com.alibaba.alink.operator.batch.BatchOperator;
import com.alink.pipline.entry.Node;

import java.util.List;

/**
 * Created by edc on 2020/8/20 下午4:00
 */
public interface OperterProcess {

    Node process(Node inNode);
}
