package com.alink.ml.dataproc;

import com.alibaba.alink.operator.batch.BatchOperator;
import com.alibaba.alink.operator.batch.dataproc.SampleBatchOp;
import com.alink.ml.utils.Utils;

import java.util.HashMap;

/**
 * Created by edc on 2020/8/27 上午10:50
 */
public class VectorNormalizerr {
    public String fit(String parameter) {
        HashMap<String, String> map = Utils.json2map(parameter);

        //读取hadoop上schema的数据
        String schemaStr = Utils.readSchemaFromHDFS(map);

        //读取数据
        BatchOperator trainData = Utils.readBatchOpFromHDFS(map,schemaStr);

        SampleBatchOp ratio = new SampleBatchOp()
                .setRatio(Utils.douOrDefault(map, "ratio", "0.3"))
                .setWithReplacement(Utils.boolOrFalse(map, "withReplacement"));

        BatchOperator selected = ratio.linkFrom(trainData);

        //存储数据更新schame 返回解析后并处理的schema
        String str = Utils.saveDFAndSchema2str(map, selected);
        return str;
    }

}
