package com.alink.ml.dataETL;

import com.alibaba.alink.operator.batch.BatchOperator;
import com.alibaba.alink.pipeline.dataproc.vector.VectorImputer;
import com.alibaba.alink.pipeline.dataproc.vector.VectorMaxAbsScaler;
import com.alink.ml.utils.Utils;

import java.util.HashMap;

/**
 * Created by edc on 2020/8/27 下午2:52
 */
public class VectorMaxAbsScalerr {
    public String fit(String parameter) {
        HashMap<String, String> map = Utils.json2map(parameter);

        //读取数据
        BatchOperator trainData = Utils.readBatchOpFromHDFS(map.getOrDefault("input_data_path", ""));

        VectorMaxAbsScaler vectorMaxAbsScaler = new VectorMaxAbsScaler()
                .setSelectedCol(map.getOrDefault("selectedCol", ""))
                .setOutputCol(map.getOrDefault("outputCol", null));

        BatchOperator transform = vectorMaxAbsScaler.fit(trainData).transform(trainData);

        //存储数据更新schame 返回解析后并处理的schema
        String str = Utils.saveDFAndSchema2str(map, transform);
        return str;
    }
}
