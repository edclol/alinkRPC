package com.alink.ml.regression;

import com.alibaba.alink.pipeline.PipelineStageBase;
import com.alibaba.alink.pipeline.regression.AftSurvivalRegression;
import com.alink.ml.utils.BaseModule;
import com.alink.ml.utils.Utils;

import java.util.HashMap;

/**
 * Created by edc on 2020/8/14
 */
public class AftSurvivalRegressionn implements BaseModule {
    @Override
    public PipelineStageBase getModule(HashMap<String, String> map, String schemaStr) {
        //读取标签和特征再封装
        HashMap<String, String[]> feaLab = Utils.StringToFeatureLabel(schemaStr);
        String[] fea = feaLab.getOrDefault("fea", null);
        String label = feaLab.get("label")[0];
        return new AftSurvivalRegression()
                .setCensorCol(map.getOrDefault("censorcol", "censorcol"))
                .setLabelCol(label)
                .setPredictionCol(map.getOrDefault("predictioncol", "predictioncol"))

                //有默认值的参数
                .setQuantileProbabilities(Utils.douArrayOrDeafult(
                        map,
                        "quantileprobabilities",
                        new double[]{0.01, 0.05, 0.1, 0.25, 0.5, 0.75, 0.9, 0.95, 0.99}))
                .setMaxIter(Utils.intOrDefault(map, "maxiter", "100"))
                .setEpsilon(Utils.douOrDefault(map, "epsilon", "1.0E-6"))
                .setWithIntercept(Utils.boolOrTrue(map, "withintercept"))
                .setVectorCol(map.getOrDefault("vectorcol", null))
                .setFeatureCols(fea)
                .setL1(Utils.douOrDefault(map, "l1", "0.0"))
                .setL2(Utils.douOrDefault(map, "l2", "0.0"))
                .setReservedCols(Utils.strArrayOrNull(map, "reservedcols"))
                .setVectorCol(map.getOrDefault("vectorcol", null))
                .setPredictionDetailCol(map.getOrDefault("predictiondetailcol", "predictiondetailcol"))


                ;
    }
}
