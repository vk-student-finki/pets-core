package dev.aucta.handgrenades.services;

import dev.aucta.handgrenades.models.Attribute;
import dev.aucta.handgrenades.models.Grenade;
import dev.aucta.handgrenades.models.Picture;
import dev.aucta.handgrenades.models.PictureType;
import dev.aucta.handgrenades.repositories.AttributeRepository;
import dev.aucta.handgrenades.repositories.GrenadeRepository;
import dev.aucta.handgrenades.repositories.PicturesRepository;
import dev.aucta.handgrenades.repositories.specifications.AttributeSpecification;
import dev.aucta.handgrenades.repositories.specifications.GrenadeSpecification;
import dev.aucta.handgrenades.repositories.specifications.SearchCriteria;
import dev.aucta.handgrenades.repositories.specifications.SearchOperation;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GrenadeService {

    @Autowired
    GrenadeRepository grenadeRepository;

    @Autowired
    PicturesRepository picturesRepository;

    @Autowired
    AttributeRepository attributeRepository;

    @Value("${hand-grenades.file-system.pictures}")
    String HAND_GRENADES_PICTURES_LOCATION;

    public Page<Grenade> all(Pageable pageable) {
        return grenadeRepository.findAll(pageable);
    }

    public Page<Grenade> all(HashMap<String, Object> searchParams, Pageable pageable) {
        Iterator<Map.Entry<String, Object>> iterator = searchParams.entrySet().iterator();
        GrenadeSpecification specification = new GrenadeSpecification();
        AttributeSpecification attributeSpecification = new AttributeSpecification();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            if (entry.getKey().equals("country.id") || entry.getKey().equals("producer.id")) {
                specification.add(new SearchCriteria(entry.getKey(), Long.valueOf(entry.getValue().toString()), SearchOperation.EQUAL));
            }
            if (entry.getKey().equals("searchByAttributes")){
                attributeSpecification.add(new SearchCriteria("value", entry.getValue().toString(), SearchOperation.MATCH));
            } else {
                specification.add(new SearchCriteria(entry.getKey(), entry.getValue(), SearchOperation.MATCH));
            }
        }

        if(attributeSpecification.getList() != null && attributeSpecification.getList().size() > 0){
            List<Attribute> attributeList = attributeRepository.findAll(attributeSpecification);
            specification.add(new SearchCriteria("id", attributeList.stream().map(attr -> attr.getGrenade().getId()).collect(Collectors.toSet()), SearchOperation.IN));
        }

        return grenadeRepository.findAll(specification, pageable);
    }

    public Grenade get(Long id) {
        Grenade grenade = grenadeRepository.getById(id);
        return grenade;
    }

    public Grenade create(Grenade grenade) {
        if (grenade.getAttributes() != null) {
            grenade.getAttributes().forEach(attribute -> {
                attribute.setGrenade(grenade);
            });
        }
        if (grenade.getPictures() != null) {
            grenade.getPictures().forEach(picture -> {
                picture.setGrenade(grenade);
            });
        }

        return grenadeRepository.save(grenade);
    }

    public Grenade update(Grenade grenade) {
        if (grenade.getAttributes() != null) {
            grenade.getAttributes().forEach(attribute -> {
                attribute.setGrenade(grenade);
            });
        }
        if (grenade.getPictures() != null) {
            grenade.getPictures().forEach(picture -> {
                picture.setGrenade(grenade);
            });
        }
        return grenadeRepository.save(grenade);
    }

    public Boolean delete(Long id) {
        grenadeRepository.deleteById(id);
        return Boolean.TRUE;
    }

    public Page<Grenade> filterGrenades(Long producerID, Long countryID, Pageable pageable) {
        if (countryID != null & producerID != null)
            return grenadeRepository.findAllByProducerIdAndCountryId(producerID, countryID, pageable);
        if (producerID == null & countryID != null) return grenadeRepository.findAllByCountryId(countryID, pageable);
        else return grenadeRepository.findAllByProducerId(producerID, pageable);
    }


    public void uploadGrenadePictures(Long grenadeId, PictureType pictureType, MultipartFile[] multipartFiles) {
        for (MultipartFile multipartFile : multipartFiles) {
            File newUploadedFile = new File(HAND_GRENADES_PICTURES_LOCATION + File.separator + multipartFile.getOriginalFilename());
            int indicator = 1;
            while (newUploadedFile.exists()) {
                newUploadedFile = new File(HAND_GRENADES_PICTURES_LOCATION + File.separator
                        + "(" + indicator++ + ")" + multipartFile.getOriginalFilename());
            }
            byte[] bytes;
            try {
                bytes = multipartFile.getBytes();
                Files.write(newUploadedFile.toPath(), bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Grenade grenade = get(grenadeId);

            Picture picture = new Picture();
            picture.setName(newUploadedFile.getName());
            picture.setFilePath(newUploadedFile.getAbsolutePath());
            picture.setType(pictureType);
            picture.setGrenade(grenade);

            if (grenade.getPictures() == null)
                grenade.setPictures(new ArrayList<>());
            grenade.getPictures().add(picture);
            grenadeRepository.save(grenade);
        }
    }

    public File getPictureById(Long pictureId) throws FileNotFoundException {
        Picture picture = picturesRepository.getById(pictureId);
        File file = new File(picture.getFilePath());
        return file;
    }


    public Grenade removePicture(Long grenadeId, Picture picture) {
        Grenade grenade = grenadeRepository.getById(grenadeId);
        grenade.getPictures().removeIf(picture1 -> picture1.getId().equals(picture.getId()));
        grenadeRepository.save(grenade);

        return grenade;

    }
}
