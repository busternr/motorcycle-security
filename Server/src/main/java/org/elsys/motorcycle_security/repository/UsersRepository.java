package org.elsys.motorcycle_security.repository;

import org.elsys.motorcycle_security.models.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsersRepository extends CrudRepository<Users,Long> {
    @Query("select username, password, email from Users user where user.username=:username")
    List<Users> getUserAccountByUsername(@Param("username") String username);
}
