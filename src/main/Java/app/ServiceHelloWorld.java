package app;

import me.xqstrive.milk.module.ModuleService;
import me.xqstrive.milk.resourse.Properties;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * Created by wangqi on 2017/1/24.
 */
public class ServiceHelloWorld  implements ModuleService {
    public void init() {
    }

    public void service(ServletRequest servletRequest, ServletResponse servletResponse) {
        System.out.println("Service:Hello World!");
        try {
            servletResponse.getWriter().write("helloword");
        }catch (IOException e){
            Properties.getLog().erro(e.getMessage());
        }

    }

}
