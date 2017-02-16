package me.xqstrive.milk.filter;

import me.xqstrive.milk.resourse.Properties;
import me.xqstrive.milk.url.RouteDispatcher;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
    The main class for this frameWork.
    Created by wangqi at 2016/12/8.
 */
public class FilterDispatcher implements Filter {
    private FilterConfig filterConfig;
    private RouteDispatcher routeDispatcher;

    /**
     * initial the config and properties.
     * @param filterConfig
     * @throws ServletException
     */
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        Properties.init(filterConfig.getServletContext().getRealPath("/"));
        routeDispatcher= new RouteDispatcher();
        routeDispatcher.init();
    }

    /**
     * 请求处理部分，交给路由分发器RouteDispatcher处理。
     * @param servletRequest 请求
     * @param servletResponse 回应
     * @param filterChain 过滤链
     * @throws IOException 抛出异常类型
     * @throws ServletException 抛出异常
     */
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        /*
        分发url
         */
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        response.setCharacterEncoding(Properties.getCoding());

        String s = "sdfsdf是打发";
        byte[] bytes = s.getBytes();
        byte[] bytes1 = s.getBytes(Properties.getCoding());
        Charset charset = Charset.forName(Properties.getCoding());

        char a = '是';
        char b = '打';
        char c = '发';

        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes1);

        CharBuffer charBuffer = charset.decode(byteBuffer);
        char[] chars = charBuffer.array();
        HttpServletRequest request = (HttpServletRequest)servletRequest;

        routeDispatcher.service(request.getServletPath(),servletRequest,servletResponse);
    }

    public void destroy() {

    }
}