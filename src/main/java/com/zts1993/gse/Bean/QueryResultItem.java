/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.bean;

/**
 * Created by TianShuo on 2015/4/9.
 */
public class QueryResultItem {


    private String docId;
    private String url;
    private String title;
    private String description;
    //    private String date;
    private double rank;
    private int wordCount;

    public QueryResultItem(String docId, String url, String title, String description, double rank) {
        this.docId = docId;
        this.url = url;
        this.title = title;
        this.description = description;
//        this.date = date;
        this.rank = rank;
//        this.wordCount = wordCount;
    }


    public QueryResultItem(URLInfo urlInfo, String content, String title) {
        this.docId = urlInfo.getDocId();
        this.url = urlInfo.getUrl();
//        this.date = urlInfo.getDate();
        this.rank = urlInfo.getRank();
//        this.wordCount = urlInfo.getWordCount();
        this.description = content;
        this.title = title;

    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public String getDate() {
//        return date;
//    }
//
//    public void setDate(String date) {
//        this.date = date;
//    }

    public double getRank() {
        return rank;
    }

    public void setRank(double rank) {
        this.rank = rank;
    }
//
//    public int getWordCount() {
//        return wordCount;
//    }
//
//    public void setWordCount(int wordCount) {
//        this.wordCount = wordCount;
//    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "QueryResultItem{" +
                "docId='" + docId + '\'' +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
//                ", date='" + date + '\'' +
                ", rank=" + rank +
//                ", wordCount=" + wordCount +
                '}';
    }
}
