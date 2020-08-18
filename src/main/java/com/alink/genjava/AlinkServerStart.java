package com.alink.genjava;

import com.alink.mlml.utils.Config;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;

public class AlinkServerStart {

    public void startServer() {
        try {
            System.out.println("AlinkServer  start ....");

            //在这里调用了 AlinkServiceImp 规定了接受的方法和返回的参数
            TProcessor tprocessor = new AlinkService.Processor<AlinkService.Iface>((AlinkService.Iface) new AlinkServiceImp());

            TServerSocket serverTransport = new TServerSocket(Config.THRIFT_SERVER_PORT);
            TServer.Args tArgs = new TServer.Args(serverTransport);
            tArgs.processor(tprocessor);
            tArgs.protocolFactory(new TBinaryProtocol.Factory());

            TServer server = new TSimpleServer(tArgs);
            server.serve();

        } catch (Exception e) {
            System.out.println("AlinkServer start error!!!");
            e.printStackTrace();
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        new AlinkServerStart().startServer();
    }

}