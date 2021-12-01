package dev.aucta.handgrenades.repositories;

import dev.aucta.handgrenades.models.Producer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProducerRepository extends JpaRepository<Producer, Long> {

}
