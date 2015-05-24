package org.mintcode.errabbit.log4j;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;
import org.mintcode.errabbit.log4j.base.Print;
import org.mintcode.errabbit.log4j.base.Settings;
import org.mintcode.errabbit.log4j.base.Version;
import org.mintcode.errabbit.log4j.send.ActiveMQSender;

import javax.jms.JMSException;

/**
 * Log4jAppender for Error Rabbit
 * Created by soleaf on 10/19/14.
 */
public class Log4jAppender extends AppenderSkeleton {

    private Settings settings = Settings.getInstance();
    private ActiveMQSender sender = ActiveMQSender.getInstance();
    private long retryInterval = 1000 * 60 * 10;

    public Log4jAppender() {

    }

    /**
     * Checking Settings
     * - Print Information
     * - Set Activation (by verifying settings)
     */
    private void checkSettings(){

        if (settings.getHost() == null|| settings.getRabbitID() == null){
            settings.setActivated(false);
            return;
        }
        Print.out("Initiation!");
        Print.out("version " + Version.string + " " + (Version.stable ? "Stable" : "Unstable"));
        Print.out("host=" + settings.getHost());
        connect();
    }

    private boolean connect(){
        if (sender.connect(settings.getHost(), settings.getUserName(), settings.getPassword(), settings.getRabbitID())){
            settings.setActivated(true);
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    protected void append(LoggingEvent event) {

        if (settings.getActivated() == null || !settings.getActivated()) {
            return;
        }
        sender.send(event);

    }

    public void close() {
        try {
            sender.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public boolean requiresLayout() {
        return false;
    }

    /**
     * Settings
     */
    public void setHost(String host) {
        settings.setHost(host);
        checkSettings();
    }

    public void setUserName(String userName){
        settings.setUserName(userName);
        checkSettings();
    }

    public void setPassword(String password){
        settings.setPassword(password);
        checkSettings();
    }

    public void setRabbitID(String rabbitID){
        settings.setRabbitID(rabbitID);
        checkSettings();
    }

    @Override
    public void finalize() {
        super.finalize();
        try {
            sender.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}