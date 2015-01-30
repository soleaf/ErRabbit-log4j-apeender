package org.mintcode.errabbit.log4j.send;

import com.google.gson.Gson;
import org.apache.log4j.spi.LoggingEvent;
import org.mintcode.common.conn.PostParamAndParseJson;
import org.mintcode.errabbit.log4j.base.Print;
import org.mintcode.errabbit.log4j.base.Settings;
import org.mintcode.errabbit.log4j.report.ReportRepo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Send to Server
 * - Making Params
 * - And Send data to Server
 * Created by soleaf on 2014. 10. 26..
 */
public class LazySender implements Runnable{

    private Settings settings = Settings.getInstance();
    private Long sleepInterval = 1000L;
    private ReportRepo reportRepo;

    @Override
    public void run() {

        // Timer Polling
        while(true){
            try {
                send();
                Thread.sleep(sleepInterval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    /**
     * Send to Sever
     */
    private void send(){

        List<LoggingEvent> sendingList= reportRepo.getAll();

        // Make JsonData
        Gson gson = new Gson();
        String data = gson.toJson(sendingList);

        if (data == null)
            return;

        // Make Default Params
        Map<String,Object> params = new HashMap<String, Object>();

        // Set Signing code
        params.put("sign", settings.getSign());

        try {
            params.put("data", URLEncoder.encode(data,"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Print.out("Fail to generate sending data");
            return;
        }

        // Send
        PostParamAndParseJson conn = new PostParamAndParseJson(URL.getURL(URL.URL_TYPE.report),params);
        if (!conn.request()){
            Print.out("Fail to send a message.");
            settings.addFailCount();
        }
    }

    public void setReportRepo(ReportRepo reportRepo) {
        this.reportRepo = reportRepo;
    }
}
