package org.mintcode.errabbit.log4j;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;

/**
 * Log4jAppender for Error Rabbit
 * Created by soleaf on 10/19/14.
 */
public class Log4jAppender extends AppenderSkeleton {

    private String host = null;
    private String sign = null;
    private String printHeader = "[ErRabbit'-'] ";
    private boolean activated = false;
    private String version = "0.0.1";

    public Log4jAppender(){

        print("Initiation!");
        print("version " + version);
        print("host=" + host + ", sign=" + sign);

        if (host == null || sign == null){
            activated = false;

        }
        else{
            activated = true;
        }
    }

    private void print(String message){
        System.out.println(printHeader + message);
    }

    @Override
    protected void append(LoggingEvent event) {

        String message = null;
        if (event.locationInformationExists()) {

            String className = event.getLocationInformation().getClassName();
            String methodName = event.getLocationInformation().getMethodName();
            String lineNumber = event.getLocationInformation().getLineNumber();
//            String message = event.getMessage().toString();
//            message = formatedMessage.toString();
        } else {
//            message = event.getMessage().toString();
        }

        switch (event.getLevel().toInt()) {
            case Level.INFO_INT:
                //your decision
                break;
            case Level.DEBUG_INT:
                //your decision
                break;
            case Level.ERROR_INT:
                //your decision
                break;
            case Level.WARN_INT:
                //your decision
                break;
            case Level.TRACE_INT:
                //your decision
                break;
            default:
                //your decision
                break;
        }
    }

    @Override
    public void close() {

    }

    @Override
    public boolean requiresLayout() {
        return false;
    }
}