package com.fanli.android;

import com.fanli.android.action.Action;
import com.fanli.android.action.KEY;
import com.fanli.android.action.MyException;
import com.fanli.android.handleData.Cpu;
import com.fanli.android.handleData.DataSwitch;
import com.fanli.android.handleData.Memory;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Author: ye.liu
 * Date: 2018/4/8
 * Time: 16:40
 * 超级返搜索滑动检查cpu和memory
 */

public class CpuSwipSuperSearchTest extends Action {
    private boolean start = false;

    @Test
    public void swipSuperSearch() throws InterruptedException, IOException, MyException {
        try {
            driver.findElementByAndroidUIAutomator("text(\"超级返\")").click();
            Thread.sleep(3000);
            closeInterstitial();
            Thread.sleep(2000);
            driver.findElementById("com.fanli.android.apps:id/search_content").click();
            Thread.sleep(2000);
            driver.findElementById("com.fanli.android.apps:id/et_search").sendKeys("U盘");
            Thread.sleep(3000);
            //恢复输入法，点击enter
            execCmd("adb shell ime set " + inputMethod() + "");
            pressKey(KEY.ENTER);
            Thread.sleep(5000);
            start = true;
            swipUpAndDownByTime(testTime);
            Thread.sleep(formatMin(2));
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
        new Cpu().writeExcel("超级返滑动搜索列表Cpu");
    }

    @Test
    public void memoryMonitor() throws IOException, InterruptedException {
        while (!start) {
            Thread.sleep(500);
            System.out.println("waiting");
        }
        new Memory().writeExcel("超级返滑动搜索列表Memory");
    }
}
