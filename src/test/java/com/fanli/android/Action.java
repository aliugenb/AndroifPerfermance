package com.fanli.android;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.io.File;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public  class Action {

    public static AndroidDriver<AndroidElement> driver;

//    @BeforeTest
    public static void setUp() throws Exception {
        GetDeviceInfo getDeviceInfo = new GetDeviceInfo();
        //设置apk的路径
        File classpathRoot = new File(System.getProperty("user.dir"));
        File appDir = new File(classpathRoot, "apps");
        File app = new File(appDir, "FanliAndroid.apk");

        //设置自动化相关参数
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("noReset", false);
        //  capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
        capabilities.setCapability("device", "Android");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", getDeviceInfo.getDeviceName());

        //设置安卓系统版本
        capabilities.setCapability("platformVersion", getDeviceInfo.getOsVersion());
        //设置apk路径
        capabilities.setCapability("app", app.getAbsolutePath());

        //设置app的主包名和主类名
        capabilities.setCapability("appPackage", "com.fanli.android.apps");
        capabilities.setCapability("appActivity", "com.fanli.android.basicarc.ui.activity.SplashActivity");

        //初始化
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
    }

//    @AfterTest
    public static void tearDown() throws Exception {
        driver.quit();
    }


    public static void skipStartScreen(){
        try{
            while(driver.findElementById("com.fanli.android.apps:id/main_image").isDisplayed()){
                driver.findElementById("com.fanli.android.apps:id/main_image").click();
            }
        }catch(Exception e){
            System.out.println("无开机画面或已关闭");
        }
    }

    public static void skipSplash(){
        try{
            while(driver.findElementById("com.fanli.android.apps:id/splash_img").isDisplayed()){
                driver.findElementByAndroidUIAutomator("text(\"9块9\")").click();
            }
        }catch(Exception e){
            System.out.println("跳过splash");
        }
    }

    public static void closeInterstitial(){
        try{
            while(Action.driver.findElementById("com.fanli.android.apps:id/close").isDisplayed()){
                Action.driver.findElementById("com.fanli.android.apps:id/close").click();
            }
        }catch(Exception e){
            System.out.println("弹层不存在！");
        }
    }

    //分钟转换成毫秒
    public static int formatMin(int i){
        int timeLong = 0;
        if(i>0){
            timeLong = i*60*1000;
        }else {
            System.out.println("输入错误");
        }
        return timeLong;
    }
}
