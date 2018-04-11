package com.fanli.android;

import com.fanli.android.action.Action;
import com.fanli.android.handleData.Cpu;
import com.fanli.android.handleData.DataSwitch;
import com.fanli.android.handleData.Memory;
import io.appium.java_client.TouchAction;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Author: ye.liu
 * Date: 2018/4/8
 * Time: 13:41
 * 首页banner滑动
 */

public class CpuBannerTest extends Action {

    private boolean start = false;

    @Test
    public void swipBanner() throws InterruptedException, IOException {
        try {
            if (driver.findElementById("com.fanli.android.apps:id/search_bg").isDisplayed()) {
                start = true;
                int width = driver.manage().window().getSize().width;
                int height = driver.manage().window().getSize().height;
                long s = (new Date()).getTime();
                while ((new Date()).getTime() - s < formatMin(testTime)) {
                    TouchAction action = new TouchAction(driver).press(width * 4 / 5, height * 1 / 5).waitAction().moveTo(width * 1 / 6, height * 1 / 5).release();
                    action.perform();
                    sleep(2000);
                }
            }
            sleep(formatMin(2));
        } catch (Exception e) {
            DataSwitch.excelNormal = false;
            throw e;
        } finally {
            start = true;
            DataSwitch.cpuEnd = true;
            DataSwitch.memoryEnd = true;
        }
    }

    @Test
    public void cpuMonitor() throws IOException, InterruptedException {
        while (!start) {
            Thread.sleep(500);
            System.out.println("waiting");
        }
        new Cpu().writeExcel("首页banner轮播Cpu");
    }

    @Test
    public void memoryMonitor() throws IOException, InterruptedException {
        while (!start) {
            Thread.sleep(500);
            System.out.println("waiting");
        }
        new Memory().writeExcel("首页banner轮播Memory");
    }
}
