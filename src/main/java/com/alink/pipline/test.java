package com.alink.pipline;

import com.alink.pipline.opreter.OperatorEnum;
import com.alink.pipline.opreter.OperterProcess;

public class test {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        String cmd = "";

        final String className = OperatorEnum.getAllClassName().get("cmd");
        OperterProcess operterProcess = (OperterProcess) Class.forName(className).newInstance();
        operterProcess.process("");




    }
}
