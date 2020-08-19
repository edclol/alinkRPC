package com.alink.ml.classification;

import com.alibaba.alink.pipeline.PipelineStageBase;
import com.alibaba.alink.pipeline.recommendation.ALS;
import com.alink.ml.utils.BaseModule;
import com.alink.ml.utils.Utils;

import java.util.HashMap;

/**
 * Created by edc on 2020/8/14
 */
public class ALSS implements BaseModule {
    @Override
    public PipelineStageBase getModule(HashMap<String, String> map) {
        return new ALS()
                //必须设置的参数
                .setPredictionCol(map.getOrDefault("predictionCol", "preCol"))
                .setUserCol(map.getOrDefault("userCol", "userCol"))
                .setItemCol(map.getOrDefault("itemCol", "itemCol"))
                .setRateCol(map.getOrDefault("rateCol", "rateCol"))


                //有默认值的参数
                .setNumIter(Utils.intOrDefault(map, "numIter", "10"))
                .setRank(Utils.intOrDefault(map, "rank", "10"))
                .setLambda(Utils.douOrDefault(map, "lambda", "0.1"))
                .setAlpha(Utils.douOrDefault(map, "alpha", "40.0"))
                .setNonnegative(Utils.boolOrFalse(map, "nonnegative"))
                .setImplicitPrefs(Utils.boolOrFalse(map, "implicitPrefs"))
                .setNumBlocks(Utils.intOrDefault(map, "numBlocks", "1"))
                ;
    }
}
