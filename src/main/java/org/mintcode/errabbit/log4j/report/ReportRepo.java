package org.mintcode.errabbit.log4j.report;

import org.apache.log4j.spi.LoggingEvent;

import java.util.List;

/**
 * ReportRepository
 * Before send report, Report be stored temporally
 * Created by soleaf on 2015. 1. 17..
 */
public interface ReportRepo {

    /**
     * Store Report
     * @param event
     */
    public void addReport(LoggingEvent event);

    /**
     * Send to Target server
     * @return
     */
    public List<LoggingEvent> getAll();

}
