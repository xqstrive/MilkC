package me.xqstrive.milk.url;

import me.xqstrive.milk.error.ErrorFactory;
import me.xqstrive.milk.error.ErrorHandle;
import me.xqstrive.milk.log.Log;
import me.xqstrive.milk.module.ModuleService;
import me.xqstrive.milk.resourse.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * app处理类。给定路径找到相应的service类。
 * 不是线程安全类，一旦注册完成不可修改。框架对外不提供AppContre实例引用。
 * Created by wangqi on 2017/1/18.
 */
public class AppContre implements AppURLs {
    private String appName;
    private UrlMap <String> serviceMap = new UrlMap<String>();
    private UrlMap <String> templateMap = new UrlMap<String>();
    private Log log = Properties.getLog();

    public AppContre(String appName){
        this.appName = appName;
    }

    public String getAppName() {
        return appName;
    }

    /**
     * 注册url函数，捕获NullPointerException异常
     * @param url 注册的url
     * @param service url对应的服务service类
     * @param template 模板名称
     */
    public void registerURLs(String url, String service, String template){
        if(url == null || url.equals("")){
            Properties.getLog().warn("url can not be null");
            return;
        }
        if (service!=null && !service.equals("")){
            serviceMap.put(url,service);
        }
        if (template!=null && !template.equals("")){
            templateMap.put(url,template);
        }
       return;
    }

    /**
     * 这个函数是对相应url调取服务的函数，还需要继续完善。
     * @param url 注册的url
     * @param servletRequest 请求
     * @param servletResponse 相应
     */
    public void serviceURL(String url, ServletRequest servletRequest, ServletResponse servletResponse){
        if (url==null) {
            throw new NullPointerException(appName+"url is null.");  //url 不能为空
        }
        if (url.equals("")){
            url = "/";
        }
        /*这里service和template不可为空字符串（""）,因为在url的put操作的时候对其判断筛除*/
        String service = serviceMap.matches(url);
        String template = templateMap.matches(url);

        if (service == null){
            /*404处理*/
            if (template == null){
                ErrorHandle errorHandle = ErrorFactory.getErrorHandle("404");
                errorHandle.handle(servletRequest,servletResponse);
                return;
            }
            else{
                HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
                HttpServletResponse httpServletResponse = (HttpServletResponse)servletResponse;
                InputStream resource;
                if (Properties.getTemplatePath().equals("")){

                    ServletContext servletContext = httpServletRequest.getServletContext();
                    resource = servletContext.getResourceAsStream(template);
                }
                else{
                    try {
                        resource = new FileInputStream(new File(Properties.getTemplatePath()+template));
                    }
                    catch (FileNotFoundException e){
                        log.erro(e.getMessage());
                        resource = null;
                    }

                }
                if (resource == null){
                    log.erro(appName+":"+template + " is not found.");
                    return;
                }
                try {
                    Writer writer = httpServletResponse.getWriter();
                    writeTemplate(writer,resource);
                } catch (IOException e){
                    log.erro(appName+":HttpServletResponse can not get Writer.");
                }
            }
        }
        else {
            try {
                Class serviceClass = Class.forName(service);
                Object object = serviceClass.newInstance();
                ModuleService moduleService = (ModuleService) object;
                moduleService.service(servletRequest, servletResponse);
            } catch (IllegalAccessException e) {
                log.erro("the" + service + " can not be constructed with null " + e.getMessage());
                return;
            } catch (Exception e) {
                log.erro(appName + url + ":" + e.getMessage());
                return;
            }
        }

    }

    private void writeTemplate(Writer writer,InputStream inputStream) throws IOException{
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream,Properties.getCoding());
        PrintWriter printWriter = (PrintWriter) writer;

        char[] buffer  = new char[64];
        int len;
        try {
            while ((len = inputStreamReader.read(buffer)) != -1){
                printWriter.write(buffer,0,len);
            }
        }
        finally {
            inputStreamReader.close();
        }

    }

}
