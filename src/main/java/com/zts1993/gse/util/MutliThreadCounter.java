/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.util;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by TianShuo on 2015/3/29.
 */
public class MutliThreadCounter {


    private static AtomicInteger counter_integer= new AtomicInteger(0);


    public static int sum(){
       return counter_integer.get();
    }

    public static int incr(){
        return counter_integer.getAndIncrement();
    }

//    public static int decr(){
//        return counter_integer.getAndDecrement();
//    }


}
