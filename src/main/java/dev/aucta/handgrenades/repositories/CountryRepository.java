package dev.aucta.handgrenades.repositories;

import dev.aucta.handgrenades.models.Country;
import dev.aucta.handgrenades.models.Producer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long>, JpaSpecificationExecutor<Country> {
    Producer findByName(String name);

}
