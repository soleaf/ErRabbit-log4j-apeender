package org.mintcode.errabbit.log4j;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;
import org.mintcode.errabbit.log4j.base.Print;
import org.mintcode.errabbit.log4j.base.Settings;
import org.mintcode.errabbit.log4j.base.Version;
import org.mintcode.errabbit.log4j.send.Sender;

/**
 * Log4jAppender for Error Rabbit
 * Created by soleaf on 10/19/14.
 */
public class Log4jAppender extends AppenderSkeleton {

    private Settings settings = Settings.getInstance();

    public Log4jAppender() {

        Version.printLogo();
        Print.out("Initiation!");
        Print.out("version " + Version.string + " " + (Version.stable ? "Stable" : "Unstable"));
        Print.out("host=" + settings.getHost() + ", sign=" + settings.getSign());

        // Check setting validation
        if (settings.getHost() == null || settings.getSign() == null) {
            //todo: Check to Sever
            settings.setActivated(true);
        } else {
            settings.setActivated(false);
        }
    }

    @Override
    protected void append(LoggingEvent event) {

        if (!settings.getActivated()) {
            Print.out(" [!ERROR] ErRabbit Not Ready. Check Server settings. or Server status.");
            return;
        }

        Thread t = new Thread(new Sender(event));
        t.start();
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
    }

    public void setSign(String sign) {
        settings.setSign(sign);
    }

}