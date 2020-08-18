package com.flink.stream

import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time
/**
 * scala版本的实时词频统计
 */
object Demo01_stream_WordCount {
  def main(args: Array[String]): Unit = {
    //1、获取流式执行环境   --- scala包
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    //2、获取数据源 --- source
    val dstream: DataStream[String] = env.socketTextStream("gpu116", 9999)
    //3、基于datastream转化 --- transformation
    val res: DataStream[(String, Int)] = dstream.flatMap(_.split(" "))
      .map((_, 1)) //(hello,1)
      .keyBy(0) //分组，类似group by
      //.timeWindow(Time.seconds(5),Time.seconds(2)) //滑动窗口
      .sum(1)

    //4、持久化 --- sink
    res.print("wc-")//.setParallelism(1)

    //5、触发执行  流应用一定要触发执行
    env.execute("my wordcount")
  }
}
