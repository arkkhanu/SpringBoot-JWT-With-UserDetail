package com.ark.JWT.With.UserDetails.repository;

import com.ark.JWT.With.UserDetails.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUserName(String userName);
}
