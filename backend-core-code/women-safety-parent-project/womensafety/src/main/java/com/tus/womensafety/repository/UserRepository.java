package com.tus.womensafety.repository;
import com.tus.womensafety.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    List<UserEntity> findByEmail(String email_id);

    List<UserEntity> findByEmailContaining(String email_id);
}
