package org.elsys.motorcycle_security.business.logic;

import org.elsys.motorcycle_security.dto.UserDto;
import org.elsys.motorcycle_security.info.UserInfo;

public interface User {
    void createNewUser(UserDto userDto);

    UserInfo getUser(String email);

    void updatePassword(String email, String newPassword);
}
