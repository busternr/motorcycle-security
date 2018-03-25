package org.elsys.motorcycle_security.repository;

import org.elsys.motorcycle_security.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends CrudRepository<User,Long> {
    @Query("select user from User user where user.email=:email")
    User getUserAccountByEmail(@Param("email") String email);

    @Query("select user.email from User user where user.email=:email")
    String getUserAccountByEmailOnlyEmail(@Param("email") String email);

    @Query("select user from User user where user.id=:Id")
    User getUserAccountById(@Param("Id") long Id);

    /*@Query("select user from User user inner join user.userDevices devices where user.email=:email and devices.deviceId=:deviceId")
    User getUserOwnsDevice(@Param("deviceId") String deviceId,@Param("email") String email);*/

    @Query(value = "select * from users inner join devices on users.id=devices.userId where users.email=:email and devices.device_id=:deviceId", nativeQuery = true)
    User getUserOwnsDevice(@Param("deviceId") String deviceId,@Param("email") String email);
}
