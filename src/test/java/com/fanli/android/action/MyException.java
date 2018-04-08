package com.fanli.android.action;

/**
 * Created with IntelliJ IDEA.
 * Author: ye.liu
 * Date: 2018/4/2
 * Time: 18:22
 * 自定义报错信息
 */

public class MyException extends Exception {

    public MyException(String errorMessage) {
        super(errorMessage);
    }
}
