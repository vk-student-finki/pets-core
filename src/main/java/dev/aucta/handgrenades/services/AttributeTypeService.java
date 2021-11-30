package dev.aucta.handgrenades.services;


import dev.aucta.handgrenades.models.AttributeType;
import dev.aucta.handgrenades.repositories.AttributeTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttributeTypeService {

    @Autowired
    AttributeTypeRepository attributeTypeRepository;


    public Page<AttributeType> all(Pageable pageable) {
        return attributeTypeRepository.findAll(pageable);
    }

    public List<AttributeType> findAllById(Long id){
        return attributeTypeRepository.findAllById(id);
    }

    public AttributeType create(AttributeType attributeType) {
        if(attributeType.getAttributes()!=null){
            attributeType.getAttributes().forEach(attribute -> {
                attribute.setAttributeType(attributeType);
            });
        }
        return attributeTypeRepository.save(attributeType);
    }

    public AttributeType update(AttributeType attributeType) {
        if(attributeType.getAttributes()!=null){
            attributeType.getAttributes().forEach(attribute -> {
                attribute.setAttributeType(attributeType);
            });
        }
        return attributeTypeRepository.save(attributeType);
    }

}
