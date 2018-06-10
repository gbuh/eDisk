package files;

import files.dao.RoleDao;
import files.dao.UserDao;
import files.entity.Role;
import files.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Random;

public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        final ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:/config.xml");

        final RoleDao dao = ctx.getBean(RoleDao.class);

        for (Role r : dao.findAll()) {
            log.info(r.getName());
        }

        final UserDao userDao = ctx.getBean(UserDao.class);

        for (User u : userDao.findAll()) {
            log.info(u.getName());
        }

        User user = new User();
        user.setName("user" + new Random().nextInt());
        user.setPassword("password");
        user.setEnabled(Boolean.TRUE);

        userDao.create(user);

        log.info("id:" + user.getId());

        for (User u : userDao.findAll()) {
            log.info(u.getName());
        }

        ((ClassPathXmlApplicationContext) ctx).close();
    }
}
