package com.fanli.android;

import com.fanli.android.action.Action;
import com.fanli.android.action.KEY;
import com.fanli.android.action.MyException;
import com.fanli.android.handleData.Cpu;
import com.fanli.android.handleData.DataSwitch;
import com.fanli.android.handleData.Memory;
import io.appium.java_client.android.AndroidElement;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Author: ye.liu
 * Date: 2018/4/9
 * Time: 14:52
 * 超级返首页反复进入搜索检查cpu和memory
 */

public class CpuSuperToSearchTest extends Action {

    private boolean start = false;

    @Test
    public void superToSearch() throws InterruptedException, IOException, MyException {
        try {
            driver.findElementByAndroidUIAutomator("text(\"超级返\")").click();
            sleep(3000);
            closeInterstitial();
            sleep(2000);
            AndroidElement searchElement = driver.findElementById("com.fanli.android.apps:id/search_content");
            String searchCoordinates = getCenterCoordinates(searchElement);
            searchElement.click();
            sleep(2000);
            //6.8新增首次进入搜索后蒙层
            pressKey(KEY.BACK);
            sleep(1000);
            String keyWord = "aaa";
            driver.findElementById("com.fanli.android.apps:id/et_search").sendKeys(keyWord);
            sleep(2000);
            //恢复输入法，点击enter
            execCmd("adb shell ime set " + getInputMethod() + "");
            driver.findElementById("com.fanli.android.apps:id/et_search").click();
            sleep(1000);
            pressKey(KEY.ENTER);
            sleep(4000);
            start = true;
            pressKey(KEY.BACK);
            sleep(1000);

            execCmd("adb shell input tap " + searchCoordinates + "");
            sleep(2000);
            AndroidElement keyWordElement = driver.findElementByAndroidUIAutomator("text(\"" + keyWord + "\")");
            String keyWordCoordinates = getCenterCoordinates(keyWordElement);
            keyWordElement.click();
            sleep(2000);
            pressKey(KEY.BACK);
            sleep(1000);

            long s = (new Date()).getTime();
            while (((new Date()).getTime() - s) < formatMin(testTime)) {
                checkInFanli();
                execCmd("adb shell input tap " + searchCoordinates + "");
                sleep(2000);
                execCmd("adb shell input tap " + keyWordCoordinates + "");
                sleep(4000);
                pressKey(KEY.BACK);
                sleep(1000);
            }
            //返回app首页后静置2分钟
            pressKey(KEY.BACK);
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
        new Cpu().writeExcel("超级返首页反复进入搜索Cpu");
    }

    @Test
    public void memoryMonitor() throws IOException, InterruptedException {
        while (!start) {
            sleep(500);
            System.out.println("waiting");
        }
        new Memory().writeExcel("超级返首页反复进入搜索Memory");
    }
}
