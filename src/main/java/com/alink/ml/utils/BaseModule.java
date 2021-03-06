package com.alink.ml.utils;

import com.alibaba.alink.operator.batch.BatchOperator;
import com.alibaba.alink.pipeline.Pipeline;
import com.alibaba.alink.pipeline.PipelineModel;
import com.alibaba.alink.pipeline.PipelineStageBase;

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

        //读取hadoop上schema的数据
        String schemaStr = Utils.readSchemaFromHDFS(params);

        //获取数据源
        BatchOperator trainData = Utils.readBatchOpFromHDFS(params,schemaStr);

        //获取module
        PipelineStageBase module = getModule(params,schemaStr);

        //构建管道
        Pipeline pipeline = new Pipeline().add(module);
        PipelineModel pipelineModel = pipeline.fit(trainData);

        //存储模型
        String savePath = Config.HADOOP_FSURI + params.getOrDefault("output_model_path", "hdfs:/data/iris.csv").substring(5) + "_module";
        pipelineModel.save(savePath);

        return "{\"type\": \"ml model\"}";

    }


    /**
     * 每个模型返回自己的module
     * @param map
     * @return
     */
    PipelineStageBase getModule(HashMap<String, String> map,String schemaStr);
}
