/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse;


import com.zts1993.gse.service.IndexServiceThread;
import com.zts1993.gse.service.RestApiThread;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Main {

    private static final Logger logger = LogManager.getLogger("MainServiceThread");

    public static void main(String[] args) {
        // write your code here

        IndexServiceThread indexServiceThread = new IndexServiceThread("Main");
        indexServiceThread.setDaemon(true);
        indexServiceThread.start();

        RestApiThread restApiThread = new RestApiThread("Main");
        restApiThread.setDaemon(true);
        restApiThread.start();


        while (true) {
            try {
                Thread.sleep(1000000);

                //InvertedIndexTestTool.queryIndex("雷锋");
                //InvertedIndexTestTool.queryIndex("中国");
                //InvertedIndexTestTool.queryIndex("的");
                //InvertedIndexTestTool.queryIndex("南京");
                //InvertedIndexTestTool.queryIndex("cnbeta");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }


}
