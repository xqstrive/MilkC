package me.xqstrive.milk.url;

import me.xqstrive.milk.resourse.Properties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 包装的路由映射类
 * Created by Eder on 2017/2/14.
 */
public class UrlMap <T> {
    private Map<String,T> appMap = new HashMap(Properties.getInitialCapacity(),Properties.getLoadFactor());
    private List<Pattern> patterns = new ArrayList(Properties.getInitialCapacity());

    public UrlMap(){

    }

    public T lookingAt(String url){
        for (Pattern pattern:patterns){
            if (url.startsWith(pattern.toString())){
                return appMap.get(pattern.toString());
            }
        }
        return null;
    }

    public T matches(String url){
        for (Pattern pattern:patterns){
            Matcher matcher = pattern.matcher(url);
            if (matcher.matches()){
                return appMap.get(pattern.toString());
            }
        }
        return null;
    }

    public void put(String url,T service){
        if (url.equals("")||url==null||service==null){
            return;
        }
        if (appMap.containsKey(url))
            return;
        appMap.put(url,service);
        Pattern pattern = Pattern.compile(url);
        patterns.add(pattern);
        return;
    }
}
