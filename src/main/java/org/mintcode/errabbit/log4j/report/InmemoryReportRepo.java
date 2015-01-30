package org.mintcode.errabbit.log4j.report;

import com.google.gson.Gson;
import org.apache.log4j.spi.LoggingEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by soleaf on 2015. 1. 17..
 */
public class InmemoryReportRepo implements ReportRepo{

    private Queue<LoggingEvent> loggingEvents = new ConcurrentLinkedQueue<LoggingEvent>();

    private static InmemoryReportRepo single = new InmemoryReportRepo();

    public static InmemoryReportRepo getInstance(){
        return single;
    }


    @Override
    public void addReport(LoggingEvent event) {
        loggingEvents.add(event);
    }

    @Override
    public List<LoggingEvent> getAll() {

        List<LoggingEvent> sendingList = new ArrayList<LoggingEvent>();
        while(!loggingEvents.isEmpty()){
            sendingList.add(loggingEvents.poll());
        }
        return sendingList;
    }
}
