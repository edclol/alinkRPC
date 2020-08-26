package com.alink.genjava;


import com.alink.ml.classification.DecisionTreeClassifierr;
import com.alink.ml.classification.RandomForestClassifierr;
import com.alink.ml.dataETL.MinMaxScalerr;
import com.alink.ml.sql.SelectBatchOpp;
import com.alink.ml.sql.Selectt;
import org.apache.thrift.TException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by edc on 2020/8/12
 */
public class AlinkServiceImp implements AlinkService.Iface {



    @Override
    public String sayhello(String output_data_path, String hello) throws TException {
        System.out.println("Server 接受到  " + hello + " " + output_data_path);
//        System.out.println(new SimpleDateFormat("HH:mm:ss").format(System.currentTimeMillis()));
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return "Server -> Clinet  " + output_data_path + " " + hello;
    }

    @Override
    public String alinkDecisionTreeClassifierr(String parameter) throws TException {

        return new DecisionTreeClassifierr().fit(parameter);
    }

    @Override
    public String alinkALSS(String parameter) throws TException {
        return null;
    }

    @Override
    public String alinkBisectingKMeans(String parameter) throws TException {
        return null;
    }

    @Override
    public String alinkKMeans(String parameter) throws TException {
        return null;
    }

    @Override
    public String alinkGaussianMixture(String parameter) throws TException {
        return null;
    }

    @Override
    public String alinkLda(String parameter) throws TException {
        return null;
    }

    @Override
    public String alinkGbdtRegressor(String parameter) throws TException {
        return null;
    }

    @Override
    public String alinkLinearRegression(String parameter) throws TException {
        return null;
    }

    @Override
    public String alinkRandomForestRegressor(String parameter) throws TException {
        return null;
    }

    @Override
    public String alinkIsotonicRegression(String parameter) throws TException {
        return null;
    }

    @Override
    public String alinkGeneralizedLinearRegression(String parameter) throws TException {
        return null;
    }

    @Override
    public String alinkAftSurvivalRegression(String parameter) throws TException {
        return null;
    }

    @Override
    public String alinkDecisionTreeRegressor(String parameter) throws TException {
        return null;
    }

    @Override
    public String alinkLassoRegression(String parameter) throws TException {
        return null;
    }

    @Override
    public String alinkRidgeRegression(String parameter) throws TException {
        return null;
    }

    @Override
    public String alinkMinMaxScaler(String parameter) throws TException {
        return null;
    }

    @Override
    public String alinkSelect(String parameter) throws TException {
        System.out.println(parameter);
        return new SelectBatchOpp().fit(parameter);
    }

    @Override
    public String alinkGbdtClassifier(String parameter) throws TException {
        return null;
    }

    @Override
    public String alinkLinearSvm(String parameter) throws TException {
        return null;
    }

    @Override
    public String alinkLogisticRegression(String parameter) throws TException {
        return null;
    }

    @Override
    public String alinkMultilayerPerceptronClassifier(String parameter) throws TException {
        return null;
    }

    @Override
    public String alinkNaiveBayesTextClassifier(String parameter) throws TException {
        return null;
    }


    @Override
    public String alinkRandomForestClassifier(String parameter) throws TException {
        return new RandomForestClassifierr().fit(parameter);
    }

    @Override
    public String alinkSoftmax(String parameter) throws TException {
        return new MinMaxScalerr().fit(parameter);
    }

}
