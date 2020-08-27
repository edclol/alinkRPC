package com.alink.ml.regression;

import com.alibaba.alink.operator.batch.BatchOperator;
import com.alibaba.alink.pipeline.Pipeline;
import com.alibaba.alink.pipeline.PipelineModel;
import com.alibaba.alink.pipeline.PipelineStageBase;
import com.alibaba.alink.pipeline.regression.LinearRegression;
import com.alink.ml.sql.SelectBatchOpp;
import com.alink.ml.utils.BaseModule;
import com.alink.ml.utils.Config;
import com.alink.ml.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

import static com.alink.ml.utils.Utils.readBatchOpFromHDFS;

/**
 * Created by edc on 2020/8/14
 */
public class LinearRegressionn {

    public static Logger logger = LogManager.getLogger(SelectBatchOpp.class);

    public String fit(String parameter) {
        String str = "{\"type\": \"ml model\"}";
        try {
            HashMap<String, String> map = Utils.json2map(parameter);
            //获取数据源
            System.out.println(map);

            BatchOperator trainData = readBatchOpFromHDFS(map.getOrDefault("input_data_path", "hdfs:/data/iris.csv"));
            String outputModelPath = map.getOrDefault("output_model_path", "null");
            String optimMethod = map.getOrDefault("optimmethod", "null");
            Double l1 = Double.valueOf(map.getOrDefault("l1", "0.0"));
            Double l2 = Double.valueOf(map.getOrDefault("l2", "0.0"));
            String predictionCol = map.getOrDefault("predictioncol", "prep");
            Boolean withIntercept = Boolean.valueOf(map.getOrDefault("withIntercept", "True"));
            int maxIter = Integer.parseInt(map.getOrDefault("maxIter", "100"));
            Double epsilon = Double.valueOf(map.getOrDefault("epsilon", "1.0E-6"));
            Boolean standardization = Boolean.valueOf(map.getOrDefault("standardization", "True"));

            HashMap<String, String[]> feaLab = Utils.StringToFeatureLabel(map.getOrDefault("input_data_path", "none"));
            String[] fea = feaLab.get("fea");
            String label = feaLab.get("label")[0];

            Pipeline pipeline = new Pipeline().add(
                    new LinearRegression()
                            .setFeatureCols(fea)
                            .setLabelCol(label)
                            .setL1(l1)
                            .setL2(l2)
                            .setMaxIter(maxIter)
                            .setEpsilon(epsilon)
                            .setStandardization(standardization)
                            .setWithIntercept(withIntercept)
                            .setOptimMethod(optimMethod)
                            .setPredictionCol(predictionCol));

            PipelineModel model = pipeline.fit(trainData);

            //存储数据
            String modelPath = outputModelPath + "_model";
            modelPath = Config.HADOOP_FSURI + modelPath.substring(5);

            model.save(modelPath);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            logger.info("return   " + str);
        }
        return str;
//    @Override
//    public PipelineStageBase getModule(HashMap<String, String> map) {
//        return new LinearRegression()
//                .setPredictionCol(map.getOrDefault("predictionCol","predictionCol"))
//                .setLabelCol(map.getOrDefault("labelCol","labelCol"))
//
//                .setFeatureCols(Utils.strArrayOrNull(map,"featureCols"))
//                .setReservedCols(Utils.strArrayOrNull(map,"reservedCols"))
//                .setWeightCol(map.getOrDefault("weightCol",null))
//                .setOptimMethod(map.getOrDefault("optimMethod",null))
//                .setVectorCol(map.getOrDefault("vectorCol",null))
//                .setL1(Utils.douOrDefault(map,"l1","0.0"))
//                .setL2(Utils.douOrDefault(map,"l2","0.0"))
//                .setWithIntercept(Utils.boolOrTrue(map,"withIntercept"))
//                .setMaxIter(Utils.intOrDefault(map,"maxIter","100"))
//                .setEpsilon(Utils.douOrDefault(map,"epsilon","1.0E-6"))
//                .setWeightCol(map.getOrDefault("vectorCol",null))
//                .setStandardization(Utils.boolOrTrue(map,"standardization"))
//                ;
//    }
    }
}
