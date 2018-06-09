package files;

import files.service.HelloService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:/config.xml");

        log.info("Start app INFO {}!!", "Logger");
        log.debug("Start app DEBUG {}!?", "Logger");

        HelloService service = ctx.getBean(HelloService.class);

        service.print();

        ((ClassPathXmlApplicationContext) ctx).close();
    }
}
