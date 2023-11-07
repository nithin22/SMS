package com.nithin.user.repository;

import com.nithin.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepositiry extends JpaRepository<UserEntity,Long> {

}
