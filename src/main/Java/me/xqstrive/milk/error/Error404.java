package me.xqstrive.milk.error;

import me.xqstrive.milk.resourse.Properties;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * Created by Eder on 2017/2/16.
 */
public class Error404 implements ErrorHandle{
    public void handle(ServletRequest servletRequest, ServletResponse servletResponse){
        try {
            String original =
                    "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <title>Title</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<p align=\"center\">找不到网页！</p>\n" +
                    "</body>\n" +
                    "</html>";

            servletResponse.getWriter().write(original);
        }catch (IOException e){
            Properties.getLog().erro(e.getMessage());
        }

    }
}
