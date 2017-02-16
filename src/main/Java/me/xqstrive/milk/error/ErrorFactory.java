package me.xqstrive.milk.error;

/**
 * Created by Eder on 2017/2/16.
 */
public class ErrorFactory {
    public static ErrorHandle getErrorHandle(String error){
        if (error.equals("404")){
            return new Error404();
        }
        else{
            return new Error404();
        }
    }
}
