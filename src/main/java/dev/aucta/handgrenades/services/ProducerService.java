package dev.aucta.handgrenades.services;

import dev.aucta.handgrenades.models.Producer;
import dev.aucta.handgrenades.repositories.ProducerRepository;
import dev.aucta.handgrenades.repositories.specifications.ProducerSpecification;
import dev.aucta.handgrenades.repositories.specifications.SearchCriteria;
import dev.aucta.handgrenades.repositories.specifications.SearchOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Service
public class ProducerService {

    @Autowired
    ProducerRepository producerRepository;

    public Page<Producer> all(Pageable pageable) {
        return producerRepository.findAll(pageable);
    }

    public Page<Producer> all(HashMap<String, Object> searchParams, Pageable pageable)
    {
        Iterator<Map.Entry<String, Object>> iterator = searchParams.entrySet().iterator();
        ProducerSpecification specification = new ProducerSpecification();
        while(iterator.hasNext())
        {
            Map.Entry<String, Object> entry = iterator.next();
            specification.add(new SearchCriteria(entry.getKey(), entry.getValue(), SearchOperation.MATCH));
        }
        return producerRepository.findAll(specification, pageable);
    }

    public Producer get(Long id) {
        Producer producer = producerRepository.getById(id);
        return producer;
    }

    public Producer create(Producer producer) {
        return producerRepository.save(producer);
    }

    public Producer update(Producer producer) {
        return producerRepository.save(producer);
    }

    public Boolean delete(Long id) {
        producerRepository.deleteById(id);
        return Boolean.TRUE;
    }
}
