package com.alink.ml.regression;

import com.alibaba.alink.pipeline.PipelineStageBase;
import com.alibaba.alink.pipeline.regression.GeneralizedLinearRegression;
import com.alink.ml.utils.BaseModule;
import com.alink.ml.utils.Utils;

import java.util.HashMap;

/**
 * Created by edc on 2020/8/14
 */
public class GeneralizedLinearRegressionn implements BaseModule {
    @Override
    public PipelineStageBase getModule(HashMap<String, String> map,String schemaStr) {
        //读取标签和特征再封装
        HashMap<String, String[]> feaLab = Utils.StringToFeatureLabel(schemaStr);
        String[] fea = feaLab.getOrDefault("fea",null);
        String label = feaLab.get("label")[0];

        return new GeneralizedLinearRegression()
                .setLabelCol(label)
                .setPredictionCol(map.getOrDefault("predictioncol", "predictioncol"))
                .setFeatureCols(fea)

                //有默认值的参数
                .setFamily(map.getOrDefault("family", "gaussian"))
                .setVariancePower(Utils.douOrDefault(map, "variancepower", "0.0"))
                .setLink(map.getOrDefault("link", null))
                .setLinkPower(Utils.douOrDefault(map, "linkpower", "1.0"))
                .setOffsetCol(map.getOrDefault("offsetcol", null))
                .setFitIntercept(Utils.boolOrTrue(map, "fitintercept"))
                .setRegParam(Utils.douOrDefault(map, "regparam", "0.0"))
                .setLinkPredResultCol(map.getOrDefault("linkpredresultcol", null))
                .setWeightCol(map.getOrDefault("weightcol", null))
                .setMaxIter(Utils.intOrDefault(map, "maxiter", "10"))


                ;


    }
}
