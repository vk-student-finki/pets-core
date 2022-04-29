package pets.services;

import pets.models.Group;
import pets.models.Privilege;
import pets.repositories.GroupRepository;
import pets.repositories.PrivilegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GroupService {

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    PrivilegeRepository privilegeRepository;

    public Page<Group> getAll(Pageable pageable) {
        return groupRepository.findAll(pageable);
    }

    public Group getById(Long id) {
        return groupRepository.getById(id);
    }

    public Group create(Group group) {
        return groupRepository.save(group);
    }

    public Group update(Group group) {
        return groupRepository.save(group);
    }

    public Group addPrivilege(Long groupId, Privilege privilege) {
        Group group = groupRepository.findById(groupId).orElse(null);
        group.getPrivileges().add(privilege);
        return groupRepository.save(group);
    }

    public Group removePrivilege(Long groupId, Privilege privilege) {
        Group group = groupRepository.findById(groupId).orElse(null);

        Privilege privilegeToRemove = group.getPrivileges().stream().filter(privilege1 -> privilege1.getId().equals(privilege.getId())).findFirst().orElse(null);
        group.getPrivileges().remove(privilegeToRemove);
        return groupRepository.save(group);
    }

    public Boolean delete(Long id) {
        groupRepository.deleteById(id);
        return Boolean.TRUE;
    }

    public Group findByName(String name) {
        return groupRepository.findFirstByName(name);
    }
}
