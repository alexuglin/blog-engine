package com.skillbox.diplom.repository;

import com.skillbox.diplom.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
