package com.alink.ml.classification;

import com.alibaba.alink.pipeline.PipelineStageBase;
import com.alibaba.alink.pipeline.classification.LogisticRegression;
import com.alink.ml.utils.BaseModule;
import com.alink.ml.utils.Utils;

import java.util.HashMap;

/**
 * Created by edc on 2020/8/14
 */
public class LogisticRegressionn implements BaseModule {
    @Override
    public PipelineStageBase getModule(HashMap<String, String> map,String schemaStr) {
        //读取标签和特征再封装
        HashMap<String, String[]> feaLab = Utils.StringToFeatureLabel(schemaStr);
        String[] fea = feaLab.getOrDefault("fea",null);
        String label = feaLab.get("label")[0];
        return new LogisticRegression()
                //必须设置的参数
                .setLabelCol(map.getOrDefault("labelCol", "label"))
                .setPredictionCol(map.getOrDefault("predictionCol", "preCol"))

                //有默认值的参数
                .setFeatureCols(Utils.strArrayOrNull(map, "featureCols"))
                .setReservedCols(Utils.strArrayOrNull(map, "reservedCols"))
                .setWeightCol(map.getOrDefault("weightCol", null))
                .setOptimMethod(map.getOrDefault("optimMethod", null))
                .setL1(Utils.douOrDefault(map, "l1", "0.0"))
                .setL2(Utils.douOrDefault(map, "l2", "0.0"))
                .setWithIntercept(Utils.boolOrTrue(map, "withIntercept"))
                .setMaxIter(Utils.intOrDefault(map, "maxIter", "100"))
                .setEpsilon(Utils.douOrDefault(map, "epsilon", "1.0E-6"))
                .setWeightCol(map.getOrDefault("vectorCol", null))
                .setStandardization(Utils.boolOrTrue(map, "standardization"))
                ;
    }
}
