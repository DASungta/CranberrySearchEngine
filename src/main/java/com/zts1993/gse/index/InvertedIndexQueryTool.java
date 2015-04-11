/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.index;

import com.zts1993.gse.bean.Factors;
import com.zts1993.gse.bean.QueryResultItem;
import com.zts1993.gse.bean.URLInfo;
import com.zts1993.gse.db.cache.KVCache;
import com.zts1993.gse.db.redis.RedisDB;
import com.zts1993.gse.filter.TermFilter;
import com.zts1993.gse.html.IHtmlContentProvider;
import com.zts1993.gse.html.LocalFsHtmlContentProvider;
import com.zts1993.gse.index.comparator.UrlScoreComparator;
import com.zts1993.gse.index.score.IScore;
import com.zts1993.gse.index.score.Tf_Idf;
import com.zts1993.gse.segmentation.ISegmentation;
import com.zts1993.gse.segmentation.SegmentationFactory;
import org.ansj.domain.Term;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.*;

/**
 * Created by TianShuo on 2015/3/28.
 */
public class InvertedIndexQueryTool {

    private static final Logger logger = LogManager.getLogger("QueryResult");

    private String queryKey;


    private long totalResultCount = 0;

    private Set<String> queryWordsSet = new HashSet<String>();

    List<Map.Entry<String, Double>> infoIds;

    private HashMap<String, Double> urlScores = new HashMap<String, Double>();
    private HashMap<String, Integer> urlHits = new HashMap<String, Integer>();

    private ArrayList<URLInfo> urlInfoArrayList = new ArrayList<URLInfo>();

    public ArrayList<QueryResultItem> getQueryResultItems() {
        return queryResultItems;
    }

    private ArrayList<QueryResultItem> queryResultItems = new ArrayList<QueryResultItem>();

    public InvertedIndexQueryTool(String queryKey) {
        this.queryKey = queryKey;
    }


    public void divide() {

        ISegmentation iSegmentation = SegmentationFactory.getDefaultSegmentation();
        List<Term> termList = iSegmentation.parse(queryKey);
        termList = TermFilter.process(termList);


        queryWordsSet = new HashSet<String>();
        for (Term term : termList) {
            queryWordsSet.add(term.getRealName());
        }

    }


    public void preQueryProcess() {

        Jedis jedis = RedisDB.getJedis();
        IScore scroeCalculator = new Tf_Idf();

        int totolPages = Integer.parseInt(jedis.get("totolPages"));
        int queryWordsCount = queryWordsSet.size();


        for (String eachKeywords : queryWordsSet) {


            eachKeywords = eachKeywords.toLowerCase();

            Set<Tuple> st = jedis.zrevrangeWithScores(eachKeywords, 0, Factors.MaxRecordPerWord);

            Long stSize = jedis.zcount(eachKeywords, -1000.0, 1000.0);


            //double idf = java.lang.Math.log((totolPages * 1.0 - stSize + 0.5) / (stSize + 0.5));

            //inverse frequency smooth
            double idf = java.lang.Math.log(totolPages * 1.0 / stSize + 1);

            for (Tuple tuple : st) {
//                URLInfo newUrlInfo = URLInfoLogic.getSimpleURLInfo(tuple, idf);
//
//                if (urlInfoArrayList.contains(newUrlInfo)) {
//                    newUrlInfo = URLInfoLogic.getURLInfo(tuple, idf);
//
//                    int index = urlInfoArrayList.indexOf(newUrlInfo);
//                    URLInfo preUrlInfo = urlInfoArrayList.get(index);
//                    preUrlInfo.addHit();
//
//                    preUrlInfo.addRank(newUrlInfo.getRank());
//                    urlInfoArrayList.set(index, preUrlInfo);
//
//                } else {
//                    newUrlInfo = URLInfoLogic.getURLInfo(tuple, idf);
//                    urlInfoArrayList.add(newUrlInfo);
//                }
                // int wordCount = Integer.valueOf(KVCache.get("wordCount:" + tuple.getElement(), jedis));
                double rank = scroeCalculator.getScore(tuple.getScore(), idf, 0);
                String key = tuple.getElement();
                //double rank = tuple.getScore() * idf;
                if (urlScores.containsKey(key)) {

                    rank = urlScores.get(key) + rank;
                    urlScores.put(key, rank);

                    int hits = urlHits.get(key);
                    urlHits.put(key, hits + 1);

                } else {

                    urlScores.put(key, rank);
                    urlHits.put(key, 1);

                }

            }


        }

        totalResultCount = urlScores.size();

        //update coord rank
        for (String key : urlScores.keySet()) {
            double val = urlScores.get(key) * (urlHits.get(key) * 1.0 / queryWordsCount * 1.0);
            urlScores.put(key, val);
        }


//        for (int position = 0; position < urlInfoArrayList.size() && position < Factors.MaxRecordPerRequest; position++) {
//            double coord = urlInfoArrayList.get(position).getHits() * 1.0 / queryWordsCount * 1.0;
//            URLInfo preUrlInfo = urlInfoArrayList.get(position);
//            preUrlInfo.setRank(preUrlInfo.getRank() * coord);
//            urlInfoArrayList.set(position, preUrlInfo);
//        }

        RedisDB.closeJedis(jedis);
    }


    public void processQuery(int start, int end) {
        Jedis jedis = RedisDB.getJedis();

        Sort();

        List<Map.Entry<String, Double>> resIds = infoIds.subList(start, end);

        for (int i = 0; i < resIds.size(); i++) {
            String docId = resIds.get(i).getKey();
            String url = KVCache.get("url:" + docId, jedis);
            double value = resIds.get(i).getValue();

            IHtmlContentProvider iHtmlContentProvider = new LocalFsHtmlContentProvider(docId);
            String content = iHtmlContentProvider.fetchMarkedText(queryWordsSet);
            String title = iHtmlContentProvider.fetchTitle();

            QueryResultItem queryResultItem = new QueryResultItem(docId, url, title, content, value);
            queryResultItems.add(queryResultItem);
        }

        RedisDB.closeJedis(jedis);
    }


    public void Sort() {
        infoIds = new ArrayList<Map.Entry<String, Double>>(urlScores.entrySet());
        Collections.sort(infoIds, new UrlScoreComparator());
    }

    public Set<String> getQueryWordsSet() {
        return queryWordsSet;
    }


    public ArrayList<URLInfo> getUrlInfoArrayList() {
        return urlInfoArrayList;
    }

    public long getTotalResultCount() {
        return totalResultCount;
    }


}
