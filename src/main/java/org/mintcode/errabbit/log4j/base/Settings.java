package org.mintcode.errabbit.log4j.base;

/**
 * Settings Singleton
 * Created by soleaf on 2014. 10. 26..
 */
public class Settings {

    private String host;
    private String sign;
    private Boolean activated;

    private int failCount = 0;
    private int failThreshold = 0;

    private final String printHeader = "[ErRabbit] ";
    private static Settings single = new Settings();

    private Settings(){}
    public static Settings getInstance(){
        return single;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.failCount = 0;
        this.activated = activated;
    }

    public String getPrintHeader() {
        return printHeader;
    }

    public int getFailCount() {
        return failCount;
    }

    public int getFailThreshold() {
        return failThreshold;
    }

    public void setFailThreshold(int failThreshold) {
        this.failThreshold = failThreshold;
    }

    public void addFailCount(){
        this.failCount++;
        if (this.failCount >= this.failThreshold){

            Print.out("Stop sending some minutes. Because fail count " + failCount + ".");

            // Stop send messages
            this.activated = false;
            Thread t = new Thread(new FailTimer());
            t.start();
        }
    }
}