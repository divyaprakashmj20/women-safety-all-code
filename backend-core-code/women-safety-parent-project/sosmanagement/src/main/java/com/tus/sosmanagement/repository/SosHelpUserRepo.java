package com.tus.sosmanagement.repository;

import com.tus.sosmanagement.entity.SosHelpUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface SosHelpUserRepo extends JpaRepository<SosHelpUserEntity, SosHelpUserEntity.SosUserId> {

    @Query(value = "SELECT * FROM sos_help_user_entity r WHERE r.sos_id = :sos_id", nativeQuery = true)
    public List<SosHelpUserEntity> findAllUsersBySosId(@Param("sos_id") Long sos_id);

    @Query(value = "SELECT * FROM sos_help_user_entity r WHERE r.user_id = :user_id AND (r.help_state = 'NA' OR r.help_state = 'ACTIVE')", nativeQuery = true)
    public List<SosHelpUserEntity> findAllActiveSosByUserId(@Param("user_id") Long user_id);


}
