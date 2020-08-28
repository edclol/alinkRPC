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
                .setLabelCol(label)
                .setPredictionCol(map.getOrDefault("predictioncol", "precol"))

                //有默认值的参数
                .setFeatureCols(fea)
                .setReservedCols(Utils.strArrayOrNull(map, "reservedcols"))
                .setWeightCol(map.getOrDefault("weightcol", null))
                .setOptimMethod(map.getOrDefault("optimmethod", null))
                .setL1(Utils.douOrDefault(map, "l1", "0.0"))
                .setL2(Utils.douOrDefault(map, "l2", "0.0"))
                .setWithIntercept(Utils.boolOrTrue(map, "withintercept"))
                .setMaxIter(Utils.intOrDefault(map, "maxiter", "100"))
                .setEpsilon(Utils.douOrDefault(map, "epsilon", "1.0E-6"))
                .setWeightCol(map.getOrDefault("vectorcol", null))
                .setStandardization(Utils.boolOrTrue(map, "standardization"))
                ;
    }
}
