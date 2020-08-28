package com.alink.ml.dataproc;

import com.alibaba.alink.operator.batch.BatchOperator;
import com.alibaba.alink.pipeline.PipelineStageBase;
import com.alibaba.alink.pipeline.dataproc.MinMaxScaler;
import com.alink.ml.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

/**
 * Created by yh on 2020/8/25 下午6:00
 */
public class MinMaxScalerr {
    public static Logger logger = LogManager.getLogger(MinMaxScaler.class);

    public String fit(String parameter) {
        HashMap<String, String> map = Utils.json2map(parameter);

        //读取hadoop上schema的数据
        String schemaStr = Utils.readSchemaFromHDFS(map);

        //读取数据
        BatchOperator trainData = Utils.readBatchOpFromHDFS(map, schemaStr);

        Double min = Double.valueOf(map.getOrDefault("min", "0.0"));
        Double max = Double.valueOf(map.getOrDefault("max", "1.0"));
        String[] selecetCols = Utils.StringToFeature(map.getOrDefault("input_data_path", "none"));

        //修改数据
        MinMaxScaler minMaxScaler = new MinMaxScaler().setMin(min).setMax(max).setSelectedCols(selecetCols);

        BatchOperator selected = minMaxScaler.fit(trainData).transform(trainData);

        //存储数据更新schame 返回解析后并处理的schema
        String str = Utils.saveDFAndSchema2str(map, selected);
        return str;


    }
}
