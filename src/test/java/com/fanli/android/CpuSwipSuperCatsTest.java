package com.fanli.android;

import com.fanli.android.action.Action;
import com.fanli.android.action.MyException;
import com.fanli.android.handleData.Cpu;
import com.fanli.android.handleData.DataSwitch;
import com.fanli.android.handleData.Memory;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Author: ye.liu
 * Date: 2018/4/9
 * Time: 15:53
 * 超级返分类滑动检查cpu和memory
 */

public class CpuSwipSuperCatsTest extends Action {
    private boolean start = false;

    @Test
    public void swipSuperCats() throws InterruptedException, IOException, MyException {
        try {
            driver.findElementByAndroidUIAutomator("text(\"超级返\")").click();
            sleep(3000);
            closeInterstitial();
            sleep(2000);
            driver.findElementById("com.fanli.android.apps:id/iv_arrow").click();
            sleep(1000);
            driver.findElementByAndroidUIAutomator("new UiSelector().className(\"android.widget.GridView\").childSelector(new UiSelector().className(\"android.widget.TextView\").index(3))").click();
            sleep(3000);
            start = true;
            swipUpAndDownByTime(testTime);
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
            sleep(500);
            System.out.println("waiting");
        }
        new Cpu().writeExcel("超级返分类滑动Cpu");
    }

    @Test
    public void memoryMonitor() throws IOException, InterruptedException {
        while (!start) {
            sleep(500);
            System.out.println("waiting");
        }
        new Memory().writeExcel("超级返分类滑动Memory");
    }
}
