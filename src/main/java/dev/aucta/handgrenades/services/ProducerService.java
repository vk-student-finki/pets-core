package dev.aucta.handgrenades.services;

import dev.aucta.handgrenades.models.Producer;
import dev.aucta.handgrenades.repositories.ProducerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {

    @Autowired
    ProducerRepository producerRepository;

    public Page<Producer> all(Pageable pageable) {
        return producerRepository.findAll(pageable);
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
