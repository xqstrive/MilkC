package me.xqstrive.milk.error;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Created by Eder on 2017/2/16.
 */
public interface ErrorHandle {
    void handle(ServletRequest servletRequest, ServletResponse servletResponse);
}
