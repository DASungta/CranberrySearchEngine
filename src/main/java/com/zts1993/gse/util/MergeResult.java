/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.util;

import com.zts1993.gse.bean.URLInfo;
import com.zts1993.gse.index.InvertedIndexTestTool;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by TianShuo on 2015/3/28.
 */
public class MergeResult {

    private Set<String> qyeryKeySet = new HashSet<String>();

    public MergeResult(Set<String> qyeryKeySet) {
        this.qyeryKeySet = qyeryKeySet;
    }


    public ArrayList<URLInfo> queryResult() {

        ArrayList<URLInfo> urlInfoArrayList = new ArrayList<URLInfo>();


        for (String aQyeryKeySet : qyeryKeySet) {
            urlInfoArrayList.addAll(InvertedIndexTestTool.query(aQyeryKeySet));
        }

        return urlInfoArrayList;

    }

    public ArrayList<Tuple> queryResultKeys() {

        ArrayList<Tuple> urlHashKeys = new ArrayList<Tuple>();
        Iterator<String> iterator = qyeryKeySet.iterator();
        String aQyeryKeySet = iterator.next();
        urlHashKeys.addAll(InvertedIndexTestTool.queryKeys(aQyeryKeySet));

        while (iterator.hasNext()) {
            aQyeryKeySet = iterator.next();
            urlHashKeys.retainAll(InvertedIndexTestTool.queryKeys(aQyeryKeySet));
        }

        return urlHashKeys;

    }




}
