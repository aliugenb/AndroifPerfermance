package com.fanli.android;

import com.fanli.android.action.Action;
import com.fanli.android.handleData.DataSwitch;
import com.fanli.android.handleData.Fps;
import org.openqa.selenium.NoSuchElementException;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Author: ye.liu
 * Date: 2018/3/30
 * Time: 10:05
 * 超级返限量秒杀FPS
 */

public class FpsSuperLimitTest extends Action {
    private static boolean start = false;

    @Test
    public void superLimit() throws Exception {
        try {
            driver.findElementByAndroidUIAutomator("text(\"超级返\")").click();
            Thread.sleep(3000);
            closeInterstitial();
            Thread.sleep(2000);
            driver.findElementByAndroidUIAutomator("text(\"限量秒杀\")").click();
            Thread.sleep(3000);
            start = true;
            swipUpAndDownByTime(5);
        } catch (Exception e) {
            DataSwitch.excelNormal = false;
            throw e;
        }finally {
            start = true;
            DataSwitch.fpsEnd = true;
        }
    }

    @Test
    public void fpsMonitor() throws IOException, InterruptedException {
        while (!start) {
            Thread.sleep(500);
            System.out.println("waiting");
        }
        new Fps().writeExcel("超级返限量秒杀FPS");
    }
}
