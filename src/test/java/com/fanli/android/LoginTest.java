package com.fanli.android;

import com.fanli.android.handleData.Cpu;
import io.appium.java_client.android.AndroidKeyCode;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginTest extends Action{

    private boolean start = false;

    @Test
    public void moretest() throws Exception{
        try{
            if (driver.findElementById( "com.fanli.android.apps:id/splash_img").isDisplayed()){
                driver.findElementByName("跳过").click();
            }
        }catch (Exception e){
        }
        Thread.sleep(500);
        driver.findElementByAndroidUIAutomator("text(\"我的\")").click();
        Thread.sleep(500);
        try{
            if (driver.findElementByAndroidUIAutomator("text(\"我的账户\")").isDisplayed()){
                System.out.println("已登录");
            }
        }catch (Exception e){
            if (driver.findElementByAndroidUIAutomator("text(\"登录\")").isDisplayed()) {
                WebElement username = driver.findElementById("com.fanli.android.apps:id/login_username");
                WebElement password = driver.findElementById("com.fanli.android.apps:id/login_password");
                username.sendKeys("15021127629");
                password.sendKeys("qwe123456");
                Thread.sleep(500);
                driver.pressKeyCode(66);
                Thread.sleep(1000);
                driver.findElementById("com.fanli.android.apps:id/btn_login").click();
                Thread.sleep(1000);
                try{
                    if (driver.findElementByAndroidUIAutomator("text(\"我的\")").isDisplayed()) {
                        System.out.println("登录成功");
                    }
                }catch (Exception i){
                    System.out.println("账号密码错误");
                }finally {
                    start = true;
                    Switch.cpuEnd=false;
                }
            }
        }
    }
}