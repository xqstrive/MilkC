package me.xqstrive.milk.url;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * appContre需实现此接口，用于处理url的注册和服务
 * Created by wangqi on 2017/1/18.
 */
public interface AppURLs {
    void registerURLs(String url,String service, String template);

    void serviceURL(String url, ServletRequest servletRequest, ServletResponse servletResponse);

    String getAppName();
}
