package com.fanli.android;

import com.fanli.android.handleData.Fps;
import com.fanli.android.handleData.Memory;
import io.appium.java_client.TouchAction;
import org.testng.annotations.Test;

import java.io.IOException;

public class NineFpsTest extends Action{
    private boolean start = false;

    @Test
    public void nineTest() throws Exception {
        Thread.sleep(10000);
        try {
            driver.findElementByAndroidUIAutomator("text(\"9块9\")").click();
            start = true;
            Thread.sleep(2000);
            int width=driver.manage().window().getSize().width;
            int height=driver.manage().window().getSize().height;
            for(int i=0;i<=5;i++) {
                for (int i1 = 0; i1 <= 5; i1++) {
                    TouchAction action = new TouchAction(driver).press(width / 2, height * 5 / 7).waitAction().moveTo(width / 2, height * 2 / 7).release();
                    action.perform();
                }
                for (int i2 = 0; i2 <= 5; i2++) {
                    TouchAction action1 = new TouchAction(driver).press(width / 2, height * 2 / 7).waitAction().moveTo(width / 2, height * 5 / 7).release();
                    action1.perform();
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            start = true;
            Switch.fpsEnd=true;
            Switch.memoryEnd=true;
        }
    }

    @Test
    public void fpsMonitor() throws IOException, InterruptedException{
        while (!start){
            Thread.sleep(500);
            System.out.println("waiting");
        }
        new Fps().writeExcel("9k9-FPS");
    }

    @Test
    public void memoryMonitor() throws IOException, InterruptedException{
        while (!start){
            System.out.println("waiting");
        }
        new Memory().writeExcel("9k9-memory");
    }
}