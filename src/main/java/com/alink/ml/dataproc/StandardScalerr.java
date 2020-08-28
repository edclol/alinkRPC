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

        //读取数据
        BatchOperator trainData = Utils.readBatchOpFromHDFS(map.getOrDefault("input_data_path", ""));

        StandardScaler standardScaler = new StandardScaler()
                .setSelectedCols(Utils.strArrayOrNull(map, "selectedCols"))
                .setWithMean(Utils.boolOrTrue(map, "withMean"))
                .setWithStd(Utils.boolOrTrue(map, "withStd"))
                .setOutputCols(Utils.strArrayOrNull(map, "outputCols"));

        BatchOperator selected = standardScaler.fit(trainData).transform(trainData);

        //存储数据更新schame 返回解析后并处理的schema
        String str = Utils.saveDFAndSchema2str(map, selected);
        return str;
    }


}