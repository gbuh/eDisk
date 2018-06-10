package files;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import files.entity.Role;
import files.entity.User;
import files.service.UserService;


public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        final ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:/config.xml");

        final UserService srv = ctx.getBean(UserService.class);

        for (Role r : srv.getRoles()) {
            log.info(r.getName());
        }

        for (User u : srv.getUsers()) {
            log.info(u.getName());
        }

        User user = new User();
        user.setName("user_" + new Random().nextInt());
        user.setPassword("password");
        user.setEnabled(Boolean.TRUE);

        srv.saveUser(user);

        log.info("id:" + user.getId());

        for (User u : srv.getUsers()) {
            log.info(u.getName());
        }

        ((ClassPathXmlApplicationContext) ctx).close();
    }
}
