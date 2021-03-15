package com.interview.Intergado.repository;

import com.interview.Intergado.repository.domain.Farm;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface FarmRepository extends CrudRepository<Farm, Long> {
    @Transactional
    @Modifying
    @Query(value = "UPDATE Farm " +
            "SET name = :name " +
            "WHERE id = :farmId")
    void updateFarm(@Param("name") final String name,
                      @Param("farmId") final Long farmId);
    Farm findByName(String name);
}
