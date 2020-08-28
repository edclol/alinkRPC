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
    public PipelineStageBase getModule(HashMap<String, String> map,String schemaStr) {

        return new ALS()
                //必须设置的参数
                .setPredictionCol(map.getOrDefault("predictioncol", "precol"))
                .setUserCol(map.getOrDefault("usercol", "usercol"))
                .setItemCol(map.getOrDefault("itemcol", "itemcol"))
                .setRateCol(map.getOrDefault("ratecol", "ratecol"))

                //有默认值的参数
                .setNumIter(Utils.intOrDefault(map, "numiter", "10"))
                .setRank(Utils.intOrDefault(map, "rank", "10"))
                .setLambda(Utils.douOrDefault(map, "lambda", "0.1"))
                .setAlpha(Utils.douOrDefault(map, "alpha", "40.0"))
                .setNonnegative(Utils.boolOrFalse(map, "nonnegative"))
                .setImplicitPrefs(Utils.boolOrFalse(map, "implicitprefs"))
                .setNumBlocks(Utils.intOrDefault(map, "numblocks", "1"))
                ;
    }
}
