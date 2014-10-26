package org.mintcode.errabbit.log4j.send;

import org.apache.log4j.spi.LoggingEvent;
import org.mintcode.common.conn.PostParamAndParseJson;
import org.mintcode.errabbit.log4j.base.Settings;

import java.util.HashMap;
import java.util.Map;

/**
 * Send to Server
 * - Making Params
 * - And Send data to Server
 * Created by soleaf on 2014. 10. 26..
 */
public class Sender implements Runnable{

    private LoggingEvent event;
    private Settings settings = Settings.getInstance();

    public Sender(LoggingEvent event){
        this.event = event;
    }

    @Override
    public void run() {

        if (event == null)
            return;

        // Make Default Params
        Map<String,Object> params = makeBasicParam();

        // Extended Message
        if (event.getMessage() instanceof Exception){
            Map<String,String> exceptionMap = ExceptionToJson.make((Exception) event.getMessage());
            params.putAll(exceptionMap);
        }
        else{
            params.put("basic_msg",event.getMessage().toString());
        }

        // Set Signing code
        params.put("sign", settings.getSign());

        // Send
        PostParamAndParseJson conn = new PostParamAndParseJson(URL.getURL(URL.URL_TYPE.report),params);
        if (!conn.request()){
            System.out.print("Fail to send a message.");
            settings.addFailCount();
        }
    }

    /**
     * Make Basic Params
     * @return
     */
    private Map<String,Object> makeBasicParam(){

        Map<String,Object> param = new HashMap<String, Object>();
        param.put("basic_lv",event.getLevel().toString());
        param.put("basic_time",event.getTimeStamp());

        if (event.locationInformationExists()) {
            param.put("basic_class",event.getLocationInformation().getClassName());
            param.put("basic_method",event.getLocationInformation().getMethodName());
            param.put("basic_line",event.getLocationInformation().getLineNumber());
        }

        return param;
    }
}
