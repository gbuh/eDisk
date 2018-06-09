package files.dao;

import files.entity.Role;

import java.util.List;

public interface RoleDao extends Dao<Role> {

    List<Role> findAll();

    List<Role> findByUserId(Long uid);

    Role findById(Long rid);
}
