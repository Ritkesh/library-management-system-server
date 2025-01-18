package com.management.library.library_management.repository;

import com.management.library.library_management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {

    User findByuserName(String userName);
    void deleteByUserName(String userName);
}
