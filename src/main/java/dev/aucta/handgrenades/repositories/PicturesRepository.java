package dev.aucta.handgrenades.repositories;

import dev.aucta.handgrenades.models.Picture;
import dev.aucta.handgrenades.models.PictureType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PicturesRepository extends JpaRepository<Picture, Long> {

}
