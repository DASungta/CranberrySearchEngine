/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.html;


import com.zts1993.gse.util.ConfigurationUtil;

import java.io.*;

/**
 * Created by TianShuo on 2015/4/8.
 */
public class FetchLocalHtmlFile implements IFetchHtml {

    private String filePath;

    public FetchLocalHtmlFile(String docId) {
        this.filePath = ConfigurationUtil.getValue("HTMLPATH") + docId + ".html";
    }

    public String fetch() {

        BufferedReader br;
        StringBuilder buffer = new StringBuilder();
        String line;

        try {
//            System.out.println(filePath);
            String fileEncode = FileCharsetDetector.getFileEncode(filePath);
            br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), fileEncode));
            while ((line = br.readLine()) != null) {
                buffer.append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return buffer.toString();
    }


}
