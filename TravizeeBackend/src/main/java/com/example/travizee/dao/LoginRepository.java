package com.example.travizee.dao;

import com.example.travizee.model.UserModel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface LoginRepository extends CrudRepository<UserModel, Long> {
    Optional<UserModel> findByEmail(String email);

    Optional<UserModel> findByUsernameOrEmail(String userName, String email);

    List<UserModel> findByIdIn(List<Long> userIds);

    Boolean existsByEmail(String email);

    Boolean existsByUsername(String userName);

    Optional<UserModel> findById(String id);

}
