package com.base.new_base.Repositories;

import com.base.new_base.Entity.Role;
import com.base.new_base.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findAll();

    User findById(int id);

    User findByLogin(String login);
    List<User> findByRole(Role role);


}