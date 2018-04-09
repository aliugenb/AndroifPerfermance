package com.fanli.android;

import com.fanli.android.action.Action;
import com.fanli.android.handleData.DataSwitch;
import com.fanli.android.handleData.Fps;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Author: ye.liu
 * Date: 2018/3/29
 * Time: 18:22
 * 超级返首页fps
 */

public class FpsSuperHomepageTest extends Action {
    private boolean start = false;

    @Test
    public void superHomepage() throws Exception {
        try {
            driver.findElementByAndroidUIAutomator("text(\"超级返\")").click();
            sleep(2000);
            closeInterstitial();
            sleep(2000);
            start = true;
            swipUpAndDownByTime(testTime);
        } catch (Exception e) {
            DataSwitch.excelNormal = false;
            throw e;
        } finally {
            start = true;
            DataSwitch.fpsEnd = true;
        }
    }

    @Test
    public void fpsMonitor() throws IOException, InterruptedException {
        while (!start) {
            sleep(500);
            System.out.println("waiting");
        }
        new Fps().writeExcel("超级返首页FPS");
    }
}
