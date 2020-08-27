package com.alink.genjava;

import com.alink.ml.utils.Config;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TNonblockingSocket;
import org.apache.thrift.transport.TNonblockingTransport;

import java.util.concurrent.TimeUnit;

public class AsyncClient {
    public static void main(String[] args) throws Exception {
        // 异步调用管理器
        TAsyncClientManager clientManager = new TAsyncClientManager();
        // 客户端应该使用非阻塞 IO
        TNonblockingTransport transport = new TNonblockingSocket(Config.THRIFT_SERVER_IP, Config.THRIFT_SERVER_PORT);
        // 协议与服务端需要一致
        TProtocolFactory tProtocolFactory = new TBinaryProtocol.Factory();
        // 异步调用
        AlinkService.AsyncClient asyncClient = new AlinkService.AsyncClient(tProtocolFactory,clientManager,transport);
        asyncClient.sayhello("11", "222", new AsyncMethodCallback<String>() {
            @Override
            public void onComplete(String s) {
                System.out.println("异步返回的结果  "+s);
            }

            @Override
            public void onError(Exception e) {
                System.out.println("异步调用失败");
                e.printStackTrace();
            }
        });
        // 让线程等待一秒，以免主线程先于异步调用结果之前结束，导致结果未被输出
        TimeUnit.SECONDS.sleep(100);
    }
}
