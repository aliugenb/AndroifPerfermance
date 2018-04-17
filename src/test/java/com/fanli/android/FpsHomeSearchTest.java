package com.fanli.android;

import com.fanli.android.action.Action;
import com.fanli.android.action.MyException;
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
    public void homeSearch() throws InterruptedException, IOException, MyException {
        try {
            driver.findElementById("com.fanli.android.apps:id/search_bg").click();
            sleep(2000);
            driver.findElementByClassName("android.widget.EditText").sendKeys("U盘");
            sleep(2000);
            driver.findElementByAndroidUIAutomator("text(\"搜索\")").click();
            sleep(3000);
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
        new Fps().writeExcel("主搜FPS");
    }
}
