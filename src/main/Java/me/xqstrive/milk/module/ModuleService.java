package me.xqstrive.milk.module;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Created by wangqi on 2017/1/19.
 * 服务接口，所有指定的url配置应实现接口，并在配置文件中注册
 */
public interface ModuleService {
    void service(ServletRequest servletRequest, ServletResponse servletResponse);

    void init();
}
