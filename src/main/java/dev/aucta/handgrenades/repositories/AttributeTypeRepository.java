package dev.aucta.handgrenades.repositories;

import dev.aucta.handgrenades.models.AttributeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttributeTypeRepository extends JpaRepository<AttributeType, Long> {

    List<AttributeType> findAllById(Long id);

}
