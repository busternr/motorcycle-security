package org.elsys.motorcycle_security.business.logic;

import org.elsys.motorcycle_security.dto.UserDto;
import org.elsys.motorcycle_security.dto.UserInfo;

public interface User {
    void createNewUser(UserDto userDto);

    UserInfo getUser(String email);

    public void updatePassword(long userId, String oldPassword, String newPassword);
}
