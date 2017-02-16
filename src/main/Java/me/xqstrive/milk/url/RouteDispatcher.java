package me.xqstrive.milk.url;

import me.xqstrive.milk.error.ErrorFactory;
import me.xqstrive.milk.error.ErrorHandle;
import me.xqstrive.milk.resourse.Properties;
import org.w3c.dom.*;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

/**
 * Created by wangqi on 2017/2/9.
 */

public class RouteDispatcher {
    private UrlMap <AppURLs> appMap = new UrlMap<AppURLs>();

    public RouteDispatcher(){
    }

    /**
     * 这里根据配置文件进行初始化app
     */
    public void init(){
        //配置文件加载
        //code 待续，以下仅供测试使用。

        String appName = "/index";
        AppURLs appURLs = new AppContre(appName);
        appURLs.registerURLs("/\\d",null,"\\template\\index.jsp");
        appMap.put(appName,appURLs);
        loadXmlConfig();
    }

    /**
     * 处理服务函数
     * @param servletPath 待处理url路径
     * @param servletRequest 请求
     * @param servletResponse 回应
     */
    public void service(String servletPath, ServletRequest servletRequest, ServletResponse servletResponse){
        if (servletPath==null||servletPath.equals("")){
            return;
        }
        AppURLs appURLs = appMap.lookingAt(servletPath);
        if (appURLs!=null){
            try {
                String url = servletPath.substring(appURLs.getAppName().length());
                appURLs.serviceURL(url,servletRequest,servletResponse);
            }catch (StringIndexOutOfBoundsException e){
                Properties.getLog().erro(e.getMessage()+"\n\t"+appURLs.getAppName()+"can not be Regular expressions");
            }
            return;
        }
        //这里应该有一个404的处理
        ErrorHandle errorHandle = ErrorFactory.getErrorHandle("404");
        errorHandle.handle(servletRequest,servletResponse);
        return;

    }

    /**
     * 初始化部分加载函数，负责处理配置文件中app部分的加载。
     */
    private void loadXmlConfig(){
        String milkXmlPath = Properties.getProjectPath()+"WEB-INF"+File.separator+"milk.xml";
        File configFile = new File(milkXmlPath);
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        /*过滤空白字符*/
        documentBuilderFactory.setValidating(true);
        documentBuilderFactory.setIgnoringElementContentWhitespace(true);

        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(configFile);
            Element rootElement = document.getDocumentElement();
            NodeList nodeListOfRoot = rootElement.getChildNodes();

            for(int i=0;i<nodeListOfRoot.getLength();i++){
                Node node = nodeListOfRoot.item(i);
                if (node.getNodeName().equals("app")){
                    dealWithApp(node);
                }
            }
        } catch (Exception e){
            Properties.getLog().erro(e.getMessage());
        }
    }

    private void dealWithApp(Node node){
        Element app = (Element)node;
        NodeList appListOfRoot = app.getChildNodes();

        /*获取appname*/
        Text appNameText = null;
        for (int i=0;i<appListOfRoot.getLength();i++){
            if (appListOfRoot.item(i).getNodeName().equals("app-name")){
                appNameText = (Text)appListOfRoot.item(i).getFirstChild();
                break;
            }
        }
        if (appNameText!=null) {
            String appName = appNameText.getData().trim();
            /*处理app*/
            AppURLs appURLs = new AppContre(appName);
            for (int i = 0; i < appListOfRoot.getLength(); i++) {
                if (appListOfRoot.item(i).getNodeName().equals("urls")) {
                    String urlName = "";
                    String service = "";
                    String template = "";
                    Element urls = (Element) appListOfRoot.item(i);
                    NodeList urlsListOfRoot = urls.getChildNodes();
                    for (int j = 0; j < urlsListOfRoot.getLength(); j++) {
                        Element url = (Element) urlsListOfRoot.item(j);
                        NodeList urlListOfRoot = url.getChildNodes();
                        for (int x = 0; x < urlListOfRoot.getLength(); x++) {
                            Node urlNode = urlListOfRoot.item(x);
                            String nodeName = urlNode.getNodeName();
                            if (urlNode.hasChildNodes()){
                                Text text = (Text) urlNode.getFirstChild();
                                if (nodeName.equals("url-name")) {
                                    urlName = text.getData().trim();
                                } else if (nodeName.equals("url-service")) {
                                    service = text.getData().trim();
                                } else {
                                    template = text.getData().trim();
                                }
                            }

                        }
                        appURLs.registerURLs(urlName, service, template);
                        urlName = "";
                        service = "";
                        template = "";
                    }
                    break;
                }
            }
            appMap.put(appName, appURLs);

        }
        return;
    }
}
