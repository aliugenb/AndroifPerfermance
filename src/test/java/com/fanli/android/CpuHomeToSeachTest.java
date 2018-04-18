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
 * Date: 2018/4/4
 * Time: 14:38
 * 首页反复进入主搜检查cpu和memory
 */
public class CpuHomeToSeachTest extends Action {
    private boolean start = false;

    @Test
    public void enterSeach() throws InterruptedException, IOException, MyException {
        try {
            AndroidElement searchElement = driver.findElementById("com.fanli.android.apps:id/search_bg");
            String searchCoordinates = getCenterCoordinates(searchElement);
            searchElement.click();
            sleep(2000);
            String keyWord = "aaa";
            driver.findElementByClassName("android.widget.EditText").sendKeys(keyWord);
            sleep(2000);
            driver.findElementByAndroidUIAutomator("text(\"搜索\")").click();
            start = true;
            sleep(3000);
            pressKey(KEY.BACK);
            sleep(1000);
            pressKey(KEY.BACK);
            sleep(1000);

            //获取历史关键词的坐标
//            execCmd("adb shell input tap " + searchCoordinates + "");
            searchElement.click();
            sleep(2000);
            AndroidElement keyWordElement = driver.findElementByAndroidUIAutomator("text(\"" + keyWord + "\")");
            String keyWordCoordinates = getCenterCoordinates(keyWordElement);
            keyWordElement.click();
            sleep(3000);
            pressKey(KEY.BACK);
            sleep(1000);
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
                pressKey(KEY.BACK);
                sleep(1000);
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
            sleep(500);
            System.out.println("waiting");
        }
        new Cpu().writeExcel("首页进入主搜Cpu");
    }

    @Test
    public void memoryMonitor() throws IOException, InterruptedException {
        while (!start) {
            sleep(500);
            System.out.println("waiting");
        }
        new Memory().writeExcel("首页进入主搜Memory");
    }
}
