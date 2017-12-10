package org.elsys.motorcycle_security.repository;

import org.elsys.motorcycle_security.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User,Long> {
    @Query("select user from User user where user.username=:username")
    User getUserAccountByUsername(@Param("username") String username);
}
