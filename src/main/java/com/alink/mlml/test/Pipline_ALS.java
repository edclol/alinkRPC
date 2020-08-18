package com.alink.mlml.test;

import com.alibaba.alink.operator.batch.BatchOperator;
import com.alibaba.alink.operator.batch.source.CsvSourceBatchOp;
import com.alibaba.alink.pipeline.Pipeline;
import com.alibaba.alink.pipeline.PipelineModel;
import com.alibaba.alink.pipeline.classification.DecisionTreeClassifier;
import com.alibaba.alink.pipeline.recommendation.ALS;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;

/**
 * Created by edc on 2020/8/6
 */
public class Pipline_ALS {

    private static Gson pGson =
            new GsonBuilder()
                    .serializeNulls()
                    .disableHtmlEscaping()
                    .serializeSpecialFloatingPointValues()
                    .create();

    public static void ALSS(String url, String schema, String outpath, String... args) throws Exception {

        //数据源
        url = "https://alink-release.oss-cn-beijing.aliyuncs.com/data-files/movielens_ratings.csv";
        schema = "userid bigint, movieid bigint, rating double, timestamp string";
        outpath = "/home/edc/alink-als.csv";

        BatchOperator trainData = new CsvSourceBatchOp()
                .setFilePath(url).setSchemaStr(schema);

        //构建管道
        Pipeline pipeline = new Pipeline();
        Pipeline pipeline1 = pipeline
                .add(new ALS().setUserCol("userid")
                        .setItemCol("movieid")
                        .setRateCol("rating")
                        .setNumIter(10).setRank(10).setLambda(0.1)
                        .setPredictionCol("pred_rating"));

        PipelineModel pipelineModel = pipeline1.fit(trainData);
        pipelineModel.save(outpath);

    }


    public static String DecisionTreeClassifierr(String url) {

        //数据源
//        url = "/home/edc/exampleData.csv";
//        schema = "f0 double, f1 string, f2 bigint, f3 bigint ,label bigint";
//        outpath = "/home/edc/alink-decisionTree.csv";
//        FieldDelimiter = ",";
//        FeatureCols="f0,f1,f2,f3";
//        LabelCol= "label";
//        PredictionDetailCol = "";

        ParamsBase paramsBase = pGson.fromJson(url, ParamsBase.class);
        HashMap<String, String> params = paramsBase.getParams();


        BatchOperator trainData = new CsvSourceBatchOp()
//                .setFieldDelimiter(params.getOrDefault("fieldDelimiter", ","))
//                .setQuoteChar(params.getOrDefault("quoteChar", "\"").toString().charAt(0))
//                .setSkipBlankLine(params.getOrDefault("skipBlankLine", "True").equals("True"))
                .setFilePath(params.getOrDefault("filePath", "").toString())
//                .setRowDelimiter(params.getOrDefault("rowDelimiter", "\n").toString())
//                .setIgnoreFirstLine(params.getOrDefault("ignoreFirstLine", "").equals("True"))
                .setIgnoreFirstLine(true)
                .setSchemaStr(params.getOrDefault("schemaStr", "").toString());



        DecisionTreeClassifier model = new DecisionTreeClassifier()
                //必须填入的参数
                .setPredictionCol(params.getOrDefault("predictionCol", "").toString())
                .setFeatureCols(params.getOrDefault("featureCols", "").toString().split(","))
                .setLabelCol(params.getOrDefault("labelCol", "").toString())

                //不是必须填入有默认值的参数
                .setTreeType(params.getOrDefault("treeType", "avg").toString())
                .setMaxDepth(Integer.valueOf(params.getOrDefault("maxDepth", "2147483647").toString()))
                .setMinSamplesPerLeaf(Integer.valueOf(params.getOrDefault("minSamplesPerLeaf", "2147483647").toString()))
                .setCreateTreeMode(params.getOrDefault("createTreeMode", "series").toString())
                .setMaxBins(Integer.valueOf(params.getOrDefault("maxBins", "128").toString()))
                .setMaxMemoryInMB(Integer.valueOf(params.getOrDefault("maxMemoryInMB", "64").toString()))


                .setReservedCols(params.getOrDefault("reservedCols", null))
                .setPredictionDetailCol(null)
                .setCategoricalCols(params.getOrDefault("categoricalCols", null))
                .setWeightCol(params.getOrDefault("weightCol", null));



        //构建管道

        Pipeline pipeline = new Pipeline().add(model);
        PipelineModel pipelineModel = pipeline.fit(trainData);


        pipelineModel.save(params.get("outpath"));

        return "{\"type\": \"ml model\"}";

    }


    public static void main(String[] args) {

    }
}
