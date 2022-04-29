package pets.services;

import pets.models.Privilege;
import pets.repositories.PrivilegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PrivilegeService {

    @Autowired
    PrivilegeRepository privilegeRepository;

    public Page<Privilege> getAll(Pageable pageable) {
        return privilegeRepository.findAll(pageable);
    }

    public Privilege getById(Long id) {
        return privilegeRepository.getById(id);
    }

    public Privilege create(Privilege privilege) {
        return privilegeRepository.save(privilege);
    }

    public void generatePrivileges() {
        Privilege adminPrivilege = new Privilege();
        adminPrivilege.setName("ADMINISTRATOR");
        Privilege userPrivilege = new Privilege();
        userPrivilege.setName("USER");
        if (privilegeRepository.findByName(adminPrivilege.getName()) == null)
            create(adminPrivilege);
        if (privilegeRepository.findByName(userPrivilege.getName()) == null)
            create(userPrivilege);
    }

    public Privilege findByName(String name) {
        return privilegeRepository.findByName(name);
    }
}
