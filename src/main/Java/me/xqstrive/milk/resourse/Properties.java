package me.xqstrive.milk.resourse;

import me.xqstrive.milk.log.Log;

import java.io.File;

/**
 * Created by wangqi on 2017/1/18.
 */
public class Properties {
    private static Instance instance;
    public static synchronized void init(String servletPath){
        if (instance==null){
            instance = new Instance();
        }
        StringBuffer milk = new StringBuffer(servletPath);
        milk.append("WEB-INF").append(File.separator).append("milk.xml");
        instance.setProjectPath(servletPath);
        instance.init();
        instance.loadXmlConfig(milk.toString());

    }
    public static Log getLog(){
        return instance.getLog();
    }
    public static String getTemplatePath() {
        return instance.getTemplatePath();
    }
    public static String getProjectPath(){
        return instance.getProjectPath();
    }
    public static  int getInitialCapacity() {
        return instance.getInitialCapacity();
    }
    public static  float getLoadFactor(){
        return instance.getLoadFactor();
    }
    public static  String getCoding(){
        return instance.getEncoding();
    }

}
