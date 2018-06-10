package files.service;

import files.entity.Role;
import files.entity.User;

import java.util.List;

public interface UserService {

    User getUser(Long uid);
    
    User getUser(String name);
    
    List<User> getUsers();
    
    void saveUser(User user);
    
    List<Role> getRoles();
}
