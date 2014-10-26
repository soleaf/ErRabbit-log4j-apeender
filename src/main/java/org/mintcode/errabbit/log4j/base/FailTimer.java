package org.mintcode.errabbit.log4j.base;

/**
 * Created by soleaf on 2014. 10. 26..
 */
public class FailTimer implements Runnable{

    private int minutes = 0;
    private int maxMintues = 5;

    @Override
    public void run() {

        try {
            while (minutes < maxMintues){
                Thread.sleep(1000);
                minutes++;
            }
            Settings.getInstance().setActivated(true);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
