package com.alink.ml.dataETL;

import com.alibaba.alink.pipeline.PipelineStageBase;
import com.alibaba.alink.pipeline.dataproc.MinMaxScaler;
import com.alink.ml.utils.BaseModule;
import com.alink.ml.utils.Utils;

import java.util.HashMap;

/**
 * Created by edc on 2020/8/25 下午4:33
 */
public class MinMaxScalerr  implements BaseModule {
    @Override
    public PipelineStageBase getModule(HashMap<String, String> map) {

        return new MinMaxScaler()
                .setSelectedCols(Utils.strArrayOrNull(map,"selectedCols"))
                .setMin(Utils.douOrDefault(map,"min","0.0"))
                .setMax(Utils.douOrDefault(map,"max","1.0"))
                .setOutputCols(Utils.strArrayOrNull(map,"outputCols"))
                ;
    }
}
