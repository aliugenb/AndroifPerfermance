package com.fanli.android;

/**
 * Created with IntelliJ IDEA.
 * User: liuye
 * Date: 2018/3/28
 * Time: 14:52
 * 9k9首页fps测试
 */

import com.fanli.android.handleData.Fps;
import io.appium.java_client.TouchAction;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Date;

public class NineFpsTest {
    private boolean start = false;


    @BeforeTest
    public void setUp() throws Exception {
        Action.setUp();
        Thread.sleep(3000);
        Action.skipStartScreen();
        Thread.sleep(2000);
        Action.closeInterstitial();
        Thread.sleep(2000);
    }

    @Test
    public void nineFpsTest() throws Exception {
        try {
            Action.driver.findElementByAndroidUIAutomator("text(\"9块9\")").click();
            Thread.sleep(2000);
            Action.closeInterstitial();
            start = true;
            Thread.sleep(2000);
            int width=Action.driver.manage().window().getSize().width;
            int height=Action.driver.manage().window().getSize().height;
            long s = (new Date()).getTime();
            while ((new Date()).getTime()-s<Action.formatMin(2)){
                for (int i1 = 0; i1 <= 5; i1++) {
                    TouchAction action = new TouchAction(Action.driver).press(width / 2, height * 5 / 7).waitAction().moveTo(width / 2, height * 2 / 7).release();
                    action.perform();
                    Thread.sleep(1000);
                }
                for (int i2 = 0; i2 <= 5; i2++) {
                    TouchAction action1 = new TouchAction(Action.driver).press(width / 2, height * 2 / 7).waitAction().moveTo(width / 2, height * 5 / 7).release();
                    action1.perform();
                    Thread.sleep(1000);
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            start = true;
            Switch.fpsEnd=true;
        }
    }

    @Test
    public void fpsMonitor() throws IOException, InterruptedException{
        while (!start){
            Thread.sleep(500);
            System.out.println("waiting");
        }
        new Fps().writeExcel("9k9首页-FPS");
    }

    @AfterTest
    public void tearDown() throws Exception {
        Action.tearDown();
    }
}
