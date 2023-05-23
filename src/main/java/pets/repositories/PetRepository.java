package pets.repositories;

import pets.models.Pet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long>, JpaSpecificationExecutor<Pet> {


    Pet findFirstByCountryId(Long id);
    Pet findFirstByName(String name);

    Page<Pet> findAllByCountryId(Long id, Pageable pageable);

    List<Pet> findAllByAddedForApplicationIsTrue();

}
