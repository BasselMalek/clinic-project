package com.clinic.project.Adapters.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clinic.project.Domain.Model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

}