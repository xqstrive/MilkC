package me.xqstrive.milk.resourse;

import me.xqstrive.milk.log.Log;
import me.xqstrive.milk.log.SimpleLog;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

/**
 * Created by wangqi on 2017/1/18.
 */
public class Instance {
    private Log log; //日志，可以使用其他
    private String encoding = "UTF-8";//编码模式
    private String templatePath = "";//模板主目录。
    private String projectPath = "";//项目目录。
    private int initialCapacity = 16; // urlsMap的相关初始化数据，与Map初始化相同，可自己定义，否则使用默认值
    private float loadFactor = 0.75f;

    public Instance(){
    }

    public synchronized void init(){
        if (log==null){
            this.log = new SimpleLog();
        }
        log.init();
    }
    public void loadXmlConfig(String XmlPath){
        File configFile = new File(XmlPath);
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
                if (!node.getNodeName().equals("app")){
                    dealWithNodes(node);
                }
            }
        } catch (Exception e){
            log.erro(e.getMessage());
        }
    }

    public Log getLog() {
        return log;
    }
    public void setLog(Log log) {
        this.log = log;
    }

    public String getTemplatePath() {
        return templatePath;
    }
    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    public String getProjectPath() {
        return projectPath;
    }
    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }

    public int getInitialCapacity() {
        return initialCapacity;
    }
    public void setInitialCapacity(int initialCapacity) {
        this.initialCapacity = initialCapacity;
    }

    public float getLoadFactor() {
        return loadFactor;
    }
    public void setLoadFactor(float loadFactor) {
        this.loadFactor = loadFactor;
    }

    public String getEncoding() {
        return encoding;
    }
    public void setEncoding(String coding) {
        this.encoding = coding;
    }

    private void dealWithNodes(Node node){
        String nodeName = node.getNodeName();
        Text textNode = (Text)node.getFirstChild();
        if (textNode!=null) {
            String text = textNode.getData();
            if (nodeName.equals("static-path")) {
                setTemplatePath(text.trim());
            } else if (nodeName.equals("encoding")) {
                setEncoding(text.trim());
            } else if (nodeName.equals("initial-capacity")) {
                try {
                    int initialCapacity = Integer.parseInt(text.trim());
                    setInitialCapacity(initialCapacity);
                } catch (NumberFormatException e) {
                    log.erro(e.getMessage());
                }
            } else if (nodeName.equals("load-factor")) {
                try {
                    float loadFactor = Float.parseFloat(text.trim());
                    setLoadFactor(loadFactor);
                } catch (NumberFormatException e) {
                    log.erro(e.getMessage());
                }
            } else {
                log.erro("Read config file errror." + "Can not find the node" + nodeName);
            }
        }
    }

}
