package com.tus.sosmanagement.repository;

import com.tus.sosmanagement.entity.RelativeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface RelativeRepository extends JpaRepository<RelativeEntity, RelativeEntity.RelativeEntityId> {
//    ArrayList<Long> findByRelativeId(Long relative_id);


    @Query(value = "SELECT sos_id FROM relative_entity r WHERE r.relative_id = :relative_id", nativeQuery = true)
    public ArrayList<Long> findByRelativeId(@Param("relative_id") Long relative_id);

    @Query(value = "SELECT relative_id FROM relative_entity r WHERE r.sos_id = :sos_id", nativeQuery = true)
    public ArrayList<Long> findBySosId(@Param("sos_id") Long sos_id);

//    @Query(value = "SELECT * FROM relative_entity r WHERE r.sos_id = :sos_id", nativeQuery = true)
//    public ArrayList<RelativeEntity> findEntryBySosId(@Param("sos_id") Long sos_id);

//    @Query(value = "SELECT * FROM relative_entity r WHERE r.relative_id = :relative_id", nativeQuery = true)
//    public ArrayList<Long> findByRelativeId(@Param("relative_id") Long relative_id);
}
