package com.fanli.android;

import com.fanli.android.action.Action;
import com.fanli.android.handleData.DataSwitch;
import com.fanli.android.handleData.Fps;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Author: ye.liu
 * Date: 2018/3/30
 * Time: 10:42
 * 主搜fps测试
 */

public class FpsHomeSearchTest extends Action {
    private boolean start = false;

    @Test
    public void homeSearch() throws InterruptedException {
        try {
            driver.findElementById("com.fanli.android.apps:id/search_bg").click();
            Thread.sleep(2000);
            driver.findElementByClassName("android.widget.EditText").sendKeys("U盘");
            Thread.sleep(2000);
            driver.findElementByAndroidUIAutomator("text(\"搜索\")").click();
            Thread.sleep(3000);
            start = true;
            swipScreen(5);
        } catch (Exception e) {
            throw e;
        } finally {
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
        new Fps().writeExcel("主搜FPS");
    }
}
