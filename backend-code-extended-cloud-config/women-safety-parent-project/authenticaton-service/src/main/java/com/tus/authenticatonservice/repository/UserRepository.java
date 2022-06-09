package com.tus.authenticatonservice.repository;
import com.tus.authenticatonservice.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByEmail(String email_id);

    List<UserEntity> findByEmailContaining(String email_id);
}
