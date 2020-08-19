package com.alink.mlml.utils;

import com.alibaba.alink.operator.batch.BatchOperator;
import com.alibaba.alink.pipeline.Pipeline;
import com.alibaba.alink.pipeline.PipelineModel;
import com.alibaba.alink.pipeline.PipelineStageBase;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;

/**
 * 模型训练的通用接口，默认实现fit 把模型设置抽离 增加模型需要实现该接口并实现getModule方法
 * Created by edc on 2020/8/13
 */
public interface BaseModule {

    /**
     * 模型通用方法
     * @param parameter
     * @return
     */
    default String fit(String parameter) {
        HashMap<String, String> params = Utils.json2map(parameter);
        //获取数据源
        BatchOperator trainData = Utils.getBatchOp(params.getOrDefault("filePath", "hdfs:/data/iris.csv"), Config.HADOOP_FSURI);

        //获取module
        PipelineStageBase module = getModule(params);
        //构建管道
        Pipeline pipeline = new Pipeline().add(module);
        PipelineModel pipelineModel = pipeline.fit(trainData);
        //存储模型
        String savePath = Config.HADOOP_FSURI + params.getOrDefault("outModulePath", "").substring(5);
        //System.out.println("module savePath is " + savePath);
        pipelineModel.save(savePath);

        return "{\"type\": \"ml model\"}";

    }


    /**
     * 每个模型返回自己的module
     * @param map
     * @return
     */
    PipelineStageBase getModule(HashMap<String, String> map);
}
