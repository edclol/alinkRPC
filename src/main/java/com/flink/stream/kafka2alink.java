package com.flink.stream;

import com.alibaba.alink.operator.stream.StreamOperator;
import com.alibaba.alink.operator.stream.sink.KafkaSinkStreamOp;
import com.alibaba.alink.operator.stream.sink.TextSinkStreamOp;
import com.alibaba.alink.operator.stream.source.KafkaSourceStreamOp;
import com.alibaba.alink.operator.stream.utils.UDFStreamOp;
import com.alink.ml.utils.Config;
import org.apache.flink.table.functions.ScalarFunction;

import static com.alibaba.alink.params.io.KafkaSourceParams.StartupMode.EARLIEST;

/**
 * Created by edc on 2020/8/24 下午3:52
 */
public class kafka2alink {
    public static void main(String[] args) throws Exception {

        KafkaSourceStreamOp data = new KafkaSourceStreamOp()
                .setBootstrapServers(Config.KAFKA_BROKER)
                .setGroupId("alink_group")
                .setTopic(Config.KAFKA_TEST_TOPIC)
                .setStartupMode(EARLIEST);

        data.print();
        StreamOperator message = data.select("message");
        StreamOperator ggg = data.as("ggg");
        System.out.println(ggg.getSchema());

        TextSinkStreamOp textSinkStreamOp = new TextSinkStreamOp()
                .setFilePath("/home/edc/text.csv")
                .setOverwriteSink(true)
                .setNumFiles(2);

        textSinkStreamOp.linkFrom(message);

        StreamOperator.execute();

    }
}
