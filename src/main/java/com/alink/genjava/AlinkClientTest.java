package com.alink.genjava;


import com.alink.ml.utils.Config;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

public class AlinkClientTest {

    /**
     * @param hello
     */
    public void startClient(String hello) {
        TTransport transport = null;
        try {
            transport = new TSocket(Config.THRIFT_SERVER_IP, Config.THRIFT_SERVER_PORT, Config.THRIFT_CLIENT_TIMEOUT);
            // 协议要和服务端一致
            TProtocol protocol = new TBinaryProtocol(transport);

            AlinkService.Client client = new AlinkService.Client(protocol);
            transport.open();
            String result = client.sayhello("ooouuut",hello);
            String s = client.alinkSelect("{\"input_data_path\": \"hdfs:/user/experiment/tmp/1e139b2c-d789-11ea-b72e-000c29c9d8a2-100\", \"output_data_path\": \"hdfs:/user/experiment/tmp/1e139b2c-d789-11ea-b72e-000c29c9d8a2-90\", \"clause\": \"fea_0,fea_1,fea_2,fea_3\"}");

            System.out.println("Client 接收到  "+result);
            System.out.println("Client 接收到  "+s);


        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        } finally {
            if (null != transport) {
                transport.close();
            }
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        new AlinkClientTest().startClient("ssss");
    }

}