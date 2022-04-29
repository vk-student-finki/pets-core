package pets.repositories;

import pets.models.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PicturesRepository extends JpaRepository<Picture, Long> {

}
