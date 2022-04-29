package pets.repositories;

import pets.models.AttributeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttributeTypeRepository extends JpaRepository<AttributeType, Long> {

    List<AttributeType> findAllById(Long id);

    AttributeType findFirstByName(String name);
}
