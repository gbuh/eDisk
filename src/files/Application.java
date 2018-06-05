package files;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import files.service.HelloService;

public class Application {
    public static void main(String[] args) {
        Logger log = LoggerFactory.getLogger(Application.class);
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:/files/config.xml");
        HelloService service = ctx.getBean(HelloService.class);
        service.print();
        log.info("{} {} !!!", 123, "Logger");
        ((ClassPathXmlApplicationContext) ctx).close();
    }
}
