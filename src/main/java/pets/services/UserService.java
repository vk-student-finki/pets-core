package pets.services;

import pets.auth.CustomUserDetails;
import pets.auth.service.MfaService;
import pets.models.Group;
import pets.models.User;
import pets.repositories.UserRepository;
import pets.repositories.specifications.SearchCriteria;
import pets.repositories.specifications.SearchOperation;
import pets.repositories.specifications.UserSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    UserRepository repository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    MfaService mfaService;

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
        user.setMfaEnabled(Boolean.TRUE);
        user.setMfaKey(mfaService.registerUser());
        return repository.save(user);
    }

    public Page<User> getAll(Pageable pageable) {
        System.out.println(getCurrentUser().getUsername());
        return repository.findAll(pageable);
    }

    public Page<User> getAll(HashMap<String, Object> searchParams, Pageable pageable) {
        Iterator<Map.Entry<String, Object>> iterator = searchParams.entrySet().iterator();
        UserSpecification specification = new UserSpecification();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            specification.add(new SearchCriteria(entry.getKey(), entry.getValue(), SearchOperation.MATCH));
        }
        return repository.findAll(specification, pageable);
    }

    public User getById(Long id) {
        return repository.getById(id);
    }

    public User update(User user) {
        if (user.getNewPassword() != null && !user.getNewPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getNewPassword()));
        } else {
            user.setPassword(repository.getById(user.getId()).getPassword());
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

    public User findByEmail(String email) {
        User user = repository.findByEmail(email);
        return user;

    }

    public User getUserByUsername(String root) {
        return repository.findByUsername(root);
    }
}
