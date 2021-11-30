package dev.aucta.handgrenades.repositories;

import dev.aucta.handgrenades.models.Grenade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrenadeRepository extends JpaRepository<Grenade, Long> {

}
