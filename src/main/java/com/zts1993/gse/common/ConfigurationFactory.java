package com.zts1993.gse.common;

import com.zts1993.gse.util.Configuration;

/**
 * Created by TianShuo on 2015/3/22.
 */
public class ConfigurationFactory {

    private static Configuration configuration;

    public static Configuration getConfiguration() {
        if (configuration == null) {
            configuration = new Configuration();
        }
        return configuration;
    }

    public static String getValue(String key) {
        return getConfiguration().getValue(key);
    }

}
