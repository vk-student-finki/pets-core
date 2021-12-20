package dev.aucta.handgrenades.repositories;

import dev.aucta.handgrenades.models.Grenade;
import dev.aucta.handgrenades.models.Producer;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProducerRepository extends JpaRepository<Producer, Long> {
    Producer findByName(String name);

}
