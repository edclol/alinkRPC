package com.alink.ml.utils;

/**
 * Created by edc on 2020/8/13
 * <p>
 * 静态配置类
 */
public final class Config {

    public static final String HADOOP_FSURI = "hdfs://172.16.1.127:9000";

    public static final int THRIFT_SERVER_PORT = 8091;
    public static final String THRIFT_SERVER_IP = "172.16.1.213";
    public static final int THRIFT_CLIENT_TIMEOUT = 10000;


    public static final String ZOOKEEPER_HOST = "172.16.2.114:2181,172.16.2.115:2181,172.16.2.116:2181";
    public static final String KAFKA_BROKER = "172.16.2.115:9092,172.16.z2.114:9092,172.16.2.116:9092";
    public static final String KAFKA_TEST_GROUP = "test_group";
    public static final String KAFKA_TEST_TOPIC ="edc1";


}
