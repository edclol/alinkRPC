package com.flink.stream;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

import java.util.Arrays;

/**
* flink的java版本词频统计
 * nc -lk 9999
**/
public class Demo01_stream_wordcount {
    public static void main(String[] args) throws Exception {
        //1、执行环境 --- 包
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //2、源 source
        DataStreamSource<String> dstream = env.socketTextStream("gpu116", 9999);
        SingleOutputStreamOperator<Tuple2<String, Integer>> sum = dstream.flatMap((FlatMapFunction<String, String>) (value, out) -> {
            String[] s = value.split(" ");
            Arrays.stream(s).forEach(out::collect);
        })
                .map((MapFunction<String, Tuple2<String, Integer>>) value -> new Tuple2<String,Integer>(value, 1))
                .keyBy(0)
                .sum(1);

        sum.print();

        //5、触发执行
        env.execute("java wordcout");
    }
}
