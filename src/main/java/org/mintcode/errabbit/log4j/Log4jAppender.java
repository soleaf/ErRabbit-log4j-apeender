package org.mintcode.errabbit.log4j;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;
import org.mintcode.errabbit.log4j.base.Print;
import org.mintcode.errabbit.log4j.base.Settings;
import org.mintcode.errabbit.log4j.base.Version;
import org.mintcode.errabbit.log4j.report.InmemoryReportRepo;
import org.mintcode.errabbit.log4j.report.ReportRepo;

/**
 * Log4jAppender for Error Rabbit
 * Created by soleaf on 10/19/14.
 */
public class Log4jAppender extends AppenderSkeleton {

    private Settings settings = Settings.getInstance();
    private ReportRepo reportRepo = InmemoryReportRepo.getInstance();

    public Log4jAppender() {

    }

    /**
     * Checking Settings
     * - Print Information
     * - Set Activation (by verifying settings)
     */
    private void checkSettings(){

        Version.printLogo();
        Print.out("Initiation!");
        Print.out("version " + Version.string + " " + (Version.stable ? "Stable" : "Unstable"));
        Print.out("host=" + settings.getHost() + ", sign=" + settings.getSign());

        // Check setting validation
        if (settings.getHost() == null || settings.getSign() == null) {
            settings.setActivated(false);
        } else {
            settings.setActivated(true);
        }

    }

    @Override
    protected void append(LoggingEvent event) {

        if (!settings.getActivated()) {
            Print.out(" [!ERROR] ErRabbit Couldn't run. Check Server settings. or Server status.");
            return;
        }
        reportRepo.addReport(event);
    }

    @Override
    public void close() {

    }

    @Override
    public boolean requiresLayout() {
        return false;
    }


    /**
     * Settings
     */
    public void setHost(String host) {
        settings.setHost(host);
        if (settings.getSign() != null && settings.getSign() != null)
            checkSettings();
    }

    public void setSign(String sign) {
        settings.setSign(sign);
        if (settings.getSign() != null && settings.getSign() != null)
            checkSettings();
    }

}