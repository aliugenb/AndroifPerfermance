package com.fanli.android.handleData;

/**
 * Created with IntelliJ IDEA.
 * Author: ye.liu
 * Date: 2018/4/2
 * Time: 18:22
 */

public class excelException extends Exception{
    public excelException()
    {
        super();
    }
    public excelException(String errorMessage)
    {
        super(errorMessage);
    }
}
