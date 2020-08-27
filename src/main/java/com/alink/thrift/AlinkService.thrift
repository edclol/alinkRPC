service AlinkService {
    string sayhello(1: string output1, 2: string parameter);

    string alinkDecisionTreeClassifierr(1: string parameter);
    string alinkGbdtClassifier(1: string parameter);
    string alinkLinearSvm(1: string parameter);
    string alinkLogisticRegression(1: string parameter);
    string alinkMultilayerPerceptronClassifier(1: string parameter);
    string alinkNaiveBayesTextClassifier(1: string parameter);
//    string alinkOneVsRest(1: string parameter); 暂时不要了啊
    string alinkRandomForestClassifier(1: string parameter);
    string alinkSoftmax(1: string parameter);
    string alinkALSS(1: string parameter);

    string alinkBisectingKMeans(1: string parameter);
    string alinkKMeans(1: string parameter);
    string alinkGaussianMixture(1: string parameter);
    string alinkLda(1: string parameter);

    string alinkGbdtRegressor(1: string parameter);
    string alinkLinearRegression(1: string parameter);
    string alinkRandomForestRegressor(1: string parameter);
    string alinkIsotonicRegression(1: string parameter);
    string alinkGeneralizedLinearRegression(1: string parameter);
    string alinkAftSurvivalRegression(1: string parameter);
    string alinkDecisionTreeRegressor(1: string parameter);
    string alinkLassoRegression(1: string parameter);
    string alinkRidgeRegression(1: string parameter);


    string alinkMinMaxScaler(1: string parameter);
    string alinkSelect(1: string parameter);
//    string alinkRidgeRegression(1: string parameter);



}