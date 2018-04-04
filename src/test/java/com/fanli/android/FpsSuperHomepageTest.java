package com.fanli.android;

/**
 * Created with IntelliJ IDEA.
 * Author: ye.liu
 * Date: 2018/3/29
 * Time: 18:22
 * 超级返首页fps
 */

import com.fanli.android.action.Action;
import com.fanli.android.handleData.DataSwitch;
import com.fanli.android.handleData.Fps;
import org.openqa.selenium.NoSuchElementException;
import org.testng.annotations.Test;

import java.io.IOException;

public class FpsSuperHomepageTest extends Action {
    private boolean start = false;

    @Test
    public void superHomepage() throws Exception {
        try {
            driver.findElementByAndroidUIAutomator("text(\"超级返\")").click();
            Thread.sleep(2000);
            closeInterstitial();
            Thread.sleep(2000);
            start = true;
            swipScreenByTime(2);
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
        new Fps().writeExcel("超级返首页FPS");
    }
}
