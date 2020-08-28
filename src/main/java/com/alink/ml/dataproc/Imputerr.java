package com.alink.ml.dataproc;

import com.alibaba.alink.operator.batch.BatchOperator;
import com.alibaba.alink.pipeline.dataproc.Imputer;
import com.alink.ml.utils.Utils;

import java.util.HashMap;

/**
 * Created by edc on 2020/8/27 上午10:40
 */
public class Imputerr {

    public String fit(String parameter) {
        HashMap<String, String> map = Utils.json2map(parameter);

        //读取hadoop上schema的数据
        String schemaStr = Utils.readSchemaFromHDFS(map);

        //读取数据
        BatchOperator trainData = Utils.readBatchOpFromHDFS(map,schemaStr);

        Imputer imputer = new Imputer()
                .setStrategy(map.getOrDefault("strategy", "mean"))
                .setFillValue(map.getOrDefault("fillValue", null))
                .setSelectedCols(Utils.strArrayOrNull(map, "selectedCols"))
                .setOutputCols(Utils.strArrayOrNull(map, "outputCols"));

        BatchOperator selected = imputer.fit(trainData).transform(trainData);

        //存储数据更新schame 返回解析后并处理的schema
        String str = Utils.saveDFAndSchema2str(map, selected);
        return str;
    }
}
