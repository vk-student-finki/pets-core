package dev.aucta.handgrenades.services;

import dev.aucta.handgrenades.models.Group;
import dev.aucta.handgrenades.models.User;
import dev.aucta.handgrenades.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository repository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public User create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    public Page<User> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public User getById(Long id) {
        return repository.getById(id);
    }

    public User update(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    public Boolean delete(Long id) {
        repository.deleteById(id);
        return Boolean.TRUE;
    }

    public User addGroup(Long userId, Group group) {
        User user = repository.findById(userId).orElse(null);
        user.getGroups().add(group);
        return repository.save(user);
    }

    public User removeGroup(Long userId, Group group) {
        User user = repository.findById(userId).orElse(null);
        Group groupToRemove = user.getGroups()
                .stream()
                .filter(group1 -> group1.getId().equals(group.getId()))
                .findFirst()
                .orElse(null);
        user.getGroups().remove(groupToRemove);
        return repository.save(user);
    }
}
