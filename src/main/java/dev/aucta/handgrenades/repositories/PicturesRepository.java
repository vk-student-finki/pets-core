package dev.aucta.handgrenades.repositories;

import dev.aucta.handgrenades.models.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PicturesRepository extends JpaRepository<Picture, Long> {
}
