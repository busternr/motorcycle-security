package org.elsys.motorcycle_security.business.logic;

import org.elsys.motorcycle_security.models.Users;
import org.elsys.motorcycle_security.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsersHandler {
    @Autowired
    private UsersRepository UsersRepository;

    public void createNewUser(String username, String password, String email, Long deviceid){
        Users u = new Users();
        u.setUsername(username);
        u.setPassword(password);
        u.setEmail(email);
        u.setDeviceid(deviceid);
        UsersRepository.save(u);
    }
}
