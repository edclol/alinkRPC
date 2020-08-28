package com.alink.ml.dataproc;

import com.alibaba.alink.operator.batch.BatchOperator;
import com.alibaba.alink.pipeline.dataproc.vector.VectorStandardScaler;
import com.alink.ml.utils.Utils;

import java.util.HashMap;

/**
 * Created by edc on 2020/8/27 下午2:55
 */
public class VectorStandardScalerr {
    public String fit(String parameter) {
        HashMap<String, String> map = Utils.json2map(parameter);

        //读取数据
        BatchOperator trainData = Utils.readBatchOpFromHDFS(map.getOrDefault("input_data_path", ""));

        VectorStandardScaler vectorStandardScaler = new VectorStandardScaler()
                .setSelectedCol(map.getOrDefault("selectedCol", ""))
                .setWithMean(Utils.boolOrTrue(map, "withMean"))
                .setWithStd(Utils.boolOrTrue(map, "withStd"))
                .setOutputCol(map.getOrDefault("outputCol", null));

        BatchOperator transform = vectorStandardScaler.fit(trainData).transform(trainData);

        //存储数据更新schame 返回解析后并处理的schema
        String str = Utils.saveDFAndSchema2str(map, transform);
        return str;
    }
}
