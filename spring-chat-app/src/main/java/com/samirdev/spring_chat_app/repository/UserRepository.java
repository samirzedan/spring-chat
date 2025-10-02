package com.samirdev.spring_chat_app.repository;

import com.samirdev.spring_chat_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long> {
    public UserDetails findByEmail(String email);
}
