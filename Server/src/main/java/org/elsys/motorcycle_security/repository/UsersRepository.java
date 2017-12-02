package org.elsys.motorcycle_security.repository;

import org.elsys.motorcycle_security.models.Users;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<Users,Long> {
}
