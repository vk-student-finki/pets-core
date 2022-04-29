package pets.services;

import pets.models.Attribute;
import pets.models.Pet;
import pets.models.Picture;
import pets.models.PictureType;
import pets.repositories.AttributeRepository;
import pets.repositories.PetRepository;
import pets.repositories.PicturesRepository;
import pets.repositories.specifications.AttributeSpecification;
import pets.repositories.specifications.PetSpecification;
import pets.repositories.specifications.SearchCriteria;
import pets.repositories.specifications.SearchOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PetService {

    @Autowired
    PetRepository petRepository;

    @Autowired
    PicturesRepository picturesRepository;

    @Autowired
    AttributeRepository attributeRepository;

    @Value("${pets.file-system.pictures}")
    String PETS_PICTURES_LOCATION;

    public Page<Pet> all(Pageable pageable) {
        return petRepository.findAll(pageable);
    }

    public Page<Pet> all(HashMap<String, Object> searchParams, Pageable pageable) {
        Iterator<Map.Entry<String, Object>> iterator = searchParams.entrySet().iterator();
        PetSpecification specification = new PetSpecification();
        AttributeSpecification attributeSpecification = new AttributeSpecification();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            if (entry.getKey().equals("country.id")) {
                specification.add(new SearchCriteria(entry.getKey(), Long.valueOf(entry.getValue().toString()), SearchOperation.EQUAL));
            }
            else if (entry.getKey().equals("searchByAttributes")){
                attributeSpecification.add(new SearchCriteria("value", entry.getValue().toString(), SearchOperation.MATCH));
            } else {
                specification.add(new SearchCriteria(entry.getKey(), entry.getValue(), SearchOperation.MATCH));
            }
        }

        if(attributeSpecification.getList() != null && attributeSpecification.getList().size() > 0){
            List<Attribute> attributeList = attributeRepository.findAll(attributeSpecification);
            specification.add(new SearchCriteria("id", attributeList.stream().map(attr -> attr.getPet().getId()).collect(Collectors.toSet()), SearchOperation.IN));
        }

        return petRepository.findAll(specification, pageable);
    }

    public Pet get(Long id) {
        Pet pet = petRepository.getById(id);
        return pet;
    }

    public Pet create(Pet pet) {
        if (pet.getAttributes() != null) {
            pet.getAttributes().forEach(attribute -> {
                attribute.setPet(pet);
            });
        }
        if (pet.getPictures() != null) {
            pet.getPictures().forEach(picture -> {
                picture.setPet(pet);
            });
        }

        return petRepository.save(pet);
    }

    public Pet update(Pet pet) {
        if (pet.getAttributes() != null) {
            pet.getAttributes().forEach(attribute -> {
                attribute.setPet(pet);
            });
        }
        if (pet.getPictures() != null) {
            pet.getPictures().forEach(picture -> {
                picture.setPet(pet);
            });
        }
        return petRepository.save(pet);
    }

    public Boolean delete(Long id) {
        petRepository.deleteById(id);
        return Boolean.TRUE;
    }

    public Page<Pet> filterPets( Long countryID, Pageable pageable) {
       return petRepository.findAllByCountryId(countryID, pageable);
    }


    public void uploadPetsPictures(Long petId, PictureType pictureType, MultipartFile[] multipartFiles) {
        for (MultipartFile multipartFile : multipartFiles) {
            File newUploadedFile = new File(PETS_PICTURES_LOCATION + File.separator + multipartFile.getOriginalFilename());
            int indicator = 1;
            while (newUploadedFile.exists()) {
                newUploadedFile = new File(PETS_PICTURES_LOCATION + File.separator
                        + "(" + indicator++ + ")" + multipartFile.getOriginalFilename());
            }
            byte[] bytes;
            try {
                bytes = multipartFile.getBytes();
                Files.write(newUploadedFile.toPath(), bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Pet pet = get(petId);

            Picture picture = new Picture();
            picture.setName(newUploadedFile.getName());
            picture.setFilePath(newUploadedFile.getAbsolutePath());
            picture.setType(pictureType);
            picture.setPet(pet);

            if (pet.getPictures() == null)
                pet.setPictures(new ArrayList<>());
            pet.getPictures().add(picture);
            petRepository.save(pet);
        }
    }

    public File getPictureById(Long pictureId) throws FileNotFoundException {
        Picture picture = picturesRepository.getById(pictureId);
        File file = new File(picture.getFilePath());
        return file;
    }


    public Pet removePicture(Long petId, Picture picture) {
        Pet pet = petRepository.getById(petId);
        pet.getPictures().removeIf(picture1 -> picture1.getId().equals(picture.getId()));
        petRepository.save(pet);

        return pet;

    }

    public PictureType[] pictureTypes() {
        return  PictureType.values();
    }


    public void updatePictureType (Long petId, Long pictureId, PictureType pictureType)
    {
        Picture picture = picturesRepository.getById(pictureId);
        picture.setType(pictureType);
        picturesRepository.save(picture);

    }
}
