package com.alink.ml.classification;

import com.alibaba.alink.pipeline.PipelineStageBase;
import com.alibaba.alink.pipeline.classification.MultilayerPerceptronClassifier;
import com.alink.ml.utils.BaseModule;
import com.alink.ml.utils.Utils;

import java.util.HashMap;

/**
 * Created by edc on 2020/8/14
 */
public class MultilayerPerceptronClassifierr implements BaseModule {
    @Override
    public PipelineStageBase getModule(HashMap<String, String> map,String schemaStr) {
        //读取标签和特征再封装
        HashMap<String, String[]> feaLab = Utils.StringToFeatureLabel(schemaStr);
        String[] fea = feaLab.getOrDefault("fea",null);
        String label = feaLab.get("label")[0];

        return new MultilayerPerceptronClassifier()
                //必须设置的参数
                .setLabelCol(map.getOrDefault("labelCol", ""))
                .setPredictionCol(map.getOrDefault("predictionCol", "f1"))
                .setLayers(Utils.intArrayOrNull(map, "layers"))

                //有默认值的参数
                .setReservedCols(Utils.strArrayOrNull(map, "reservedCols"))
                .setBlockSize(Integer.valueOf(map.getOrDefault("blockSize", "64")))
                .setVectorCol(map.getOrDefault("vectorCol", null))
                .setMaxIter(Integer.valueOf(map.getOrDefault("maxIter", "100")))
                .setEpsilon(Double.valueOf(map.getOrDefault("epsilon", "1.0E-6")))
                .setL1(Double.valueOf(map.getOrDefault("l1", "0.0")))
                .setL2(Double.valueOf(map.getOrDefault("l2", "0.0")))
                ;
    }
}
