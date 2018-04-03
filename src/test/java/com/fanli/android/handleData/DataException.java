package com.fanli.android.handleData;

/**
 * Created with IntelliJ IDEA.
 * Author: ye.liu
 * Date: 2018/4/2
 * Time: 18:22
 * excel报错信息
 */

public class DataException extends Exception {

    public DataException(String errorMessage) {
        super(errorMessage);
    }
}
