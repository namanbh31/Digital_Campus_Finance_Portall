package com.digitalFinancePortal.repositories;

import com.digitalFinancePortal.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface UserRepository extends CrudRepository<User, Long> {
}
