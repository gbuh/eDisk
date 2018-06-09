package files;

import files.dao.RoleDao;
import files.entity.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        final ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:/config.xml");

        final RoleDao dao = ctx.getBean(RoleDao.class);
        
        for (Role r : dao.findAll()) {
            log.info(r.getName());
        }
        
        ((ClassPathXmlApplicationContext) ctx).close();
    }
}
