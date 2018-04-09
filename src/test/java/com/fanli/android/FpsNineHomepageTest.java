package com.fanli.android;

import com.fanli.android.action.Action;
import com.fanli.android.handleData.DataSwitch;
import com.fanli.android.handleData.Fps;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Author: ye.liu
 * Date: 2018/3/28
 * Time: 14:52
 * 9k9首页fps测试
 */

public class FpsNineHomepageTest extends Action {
    private boolean start = false;

    @Test
    public void nineHomepage() throws Exception {
        try {
            driver.findElementByAndroidUIAutomator("text(\"9块9\")").click();
            Thread.sleep(2000);
            closeInterstitial();
            Thread.sleep(2000);
            start = true;
            swipUpAndDownByTime(10);
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
        new Fps().writeExcel("9k9首页FPS");
    }

}
