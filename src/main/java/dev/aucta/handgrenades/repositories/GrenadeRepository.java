package dev.aucta.handgrenades.repositories;

import dev.aucta.handgrenades.models.Grenade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrenadeRepository extends JpaRepository<Grenade, Long> {

    Grenade findFirstByProducerId(Long id);
    Grenade findFirstByCountryId(Long id);


    Page<Grenade> findAllByProducerId(Long id, Pageable pageable);
    Page<Grenade> findAllByCountryId(Long id, Pageable pageable);
    Page<Grenade> findAllByProducerIdAndCountryId(Long idP, Long idC, Pageable pageable);
}
