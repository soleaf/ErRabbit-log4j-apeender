package org.mintcode.errabbit.log4j.send;

import org.mintcode.errabbit.log4j.base.Settings;

/**
 * Created by soleaf on 2014. 10. 26..
 */
public class URL {

    private static Settings settings = Settings.getInstance();

    public static enum URL_TYPE{
        report,
        check
    }

    public static String getURL(URL_TYPE type){
        switch (type){
            case report:
                return settings.getHost() + "/api/report.err";
            default:
                return "";
        }
    }
}
