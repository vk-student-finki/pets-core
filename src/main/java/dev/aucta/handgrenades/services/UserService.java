package dev.aucta.handgrenades.services;

import dev.aucta.handgrenades.auth.CustomUserDetails;
import dev.aucta.handgrenades.models.Group;
import dev.aucta.handgrenades.models.User;
import dev.aucta.handgrenades.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository repository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public User getCurrentUser() {
        if (SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().getPrincipal() != null) {
            CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return repository.findByUsername(userDetails.getUsername());
        }
        return null;
    }

    public User create(User user) {
        user.setPassword(passwordEncoder.encode(user.getNewPassword()));
        return repository.save(user);
    }

    public Page<User> getAll(Pageable pageable) {
        System.out.println(getCurrentUser().getUsername());
        return repository.findAll(pageable);
    }

    public User getById(Long id) {
        return repository.getById(id);
    }

    public User update(User user) {
        if (user.getNewPassword() != null && !user.getNewPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getNewPassword()));
        }
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

    public User findByUsername(String username) {
        User user = repository.findByUsername(username);
        return user;
    }
}
