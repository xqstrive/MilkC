package app;

import me.xqstrive.milk.module.ModuleService;

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
        try {
            servletResponse.getWriter().write("Hello World!");
        }catch (IOException e){
            System.out.print(e.getMessage());
        }

    }

}
