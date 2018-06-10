package files.dao;

import files.entity.User;

import java.util.List;

public interface UserDao extends CrudDao<Long, User> {
    List<User> findAll();

    User findByName(String name);
}
