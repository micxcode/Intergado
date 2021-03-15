package com.interview.Intergado.repository;

import com.interview.Intergado.repository.domain.Animal;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AnimalRepository extends CrudRepository<Animal, Long> {
    @Transactional
    @Modifying
    @Query(value = "UPDATE Animal " +
            "SET tag = :tag," +
            "farm_id = :farmId " +
            "WHERE id = :animalId")
    void updateAnimal(@Param("tag") final String tag,
                      @Param("animalId") final Long animalId,
                      @Param("farmId") final Long farmId);
}
