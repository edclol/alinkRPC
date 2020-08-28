package com.alink.ml.dataproc;

import com.alibaba.alink.operator.batch.BatchOperator;
import com.alibaba.alink.pipeline.dataproc.vector.VectorMinMaxScaler;
import com.alink.ml.utils.Utils;

import java.util.HashMap;

/**
 * Created by edc on 2020/8/27 下午2:53
 */
public class VectorMinMaxScalerr {

    public String fit(String parameter) {
        HashMap<String, String> map = Utils.json2map(parameter);

        //读取数据
        BatchOperator trainData = Utils.readBatchOpFromHDFS(map.getOrDefault("input_data_path", ""));

        VectorMinMaxScaler vectorMinMaxScaler = new VectorMinMaxScaler()
                .setSelectedCol(map.getOrDefault("selectedCol", ""))
                .setMin(Utils.douOrDefault(map, "min", "0.0"))
                .setMax(Utils.douOrDefault(map, "max", "1.0"))
                .setOutputCol(map.getOrDefault("outputCol", null));

        BatchOperator transform = vectorMinMaxScaler.fit(trainData).transform(trainData);

        //存储数据更新schame 返回解析后并处理的schema
        String str = Utils.saveDFAndSchema2str(map, transform);
        return str;
    }

}
