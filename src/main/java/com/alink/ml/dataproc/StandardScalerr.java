package com.alink.ml.dataproc;

import com.alibaba.alink.operator.batch.BatchOperator;
import com.alibaba.alink.pipeline.dataproc.StandardScaler;
import com.alink.ml.utils.Utils;

import java.util.HashMap;

/**
 * Created by edc on 2020/8/27 上午10:09
 */
public class StandardScalerr {
    public static void main(String[] args) {

    }

    public String fit(String parameter) {
        HashMap<String, String> map = Utils.json2map(parameter);

        //读取hadoop上schema的数据
        String schemaStr = Utils.readSchemaFromHDFS(map);

        //读取数据
        BatchOperator trainData = Utils.readBatchOpFromHDFS(map,schemaStr);

        StandardScaler standardScaler = new StandardScaler()
                .setSelectedCols(Utils.strArrayOrNull(map, "selectedcols"))
                .setWithMean(Utils.boolOrTrue(map, "withmean"))
                .setWithStd(Utils.boolOrTrue(map, "withstd"))
                .setOutputCols(Utils.strArrayOrNull(map, "outputcols"));

        BatchOperator selected = standardScaler.fit(trainData).transform(trainData);

        //存储数据更新schame 返回解析后并处理的schema
        String str = Utils.saveDFAndSchema2str(map, selected);
        return str;
    }


}
