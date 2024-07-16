package com.example.use.repository;

import com.example.use.entity.Users;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface UsersRepository extends JpaRepository<Users,Long> {
    Users findOneByEmailAndPassword(String email, String password);
     Users findByEmail(String email);
}