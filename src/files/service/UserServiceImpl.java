package files.service;

import files.dao.RoleDao;
import files.dao.UserDao;
import files.entity.Role;
import files.entity.User;

import java.util.List;

public class UserServiceImpl implements UserService {

    private RoleDao roleDao;

    private UserDao userDao;

    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User getUser(Long uid) {
        User user = userDao.read(uid);
        user.setRoles(roleDao.findByUserId(uid));
        return user;
    }

    @Override
    public User getUser(String name) {
        User user = userDao.findByName(name);
        user.setRoles(roleDao.findByUserId(user.getId()));
        return user;
    }

    @Override
    public List<User> getUsers() {
        // TODO Auto-generated method stub
        return userDao.findAll();
    }

    @Override
    public void saveUser(User user) {
        if (user.getId() == null) {
            userDao.create(user);
        } else {
            userDao.update(user);
        }
    }

    @Override
    public List<Role> getRoles() {
        return roleDao.findAll();
    }

}
