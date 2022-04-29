package pets;

import pets.models.AttributeType;
import pets.models.Group;
import pets.models.Privilege;
import pets.models.User;
import pets.services.AttributeTypeService;
import pets.services.GroupService;
import pets.services.PrivilegeService;
import pets.services.UserService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ImportData {

    public static void importBasicUsersGroupsAndPrivileges(
            UserService userService,
            GroupService groupService,
            PrivilegeService privilegeService
    ){
        Privilege privilegeAdministration = privilegeService.findByName("ADMINISTRATOR");
        if (privilegeAdministration == null) {
            Privilege privilege = new Privilege();
            privilege.setName("ADMINISTRATOR");
            privilegeAdministration = privilegeService.create(privilege);
        }

        Privilege privilegeUser = privilegeService.findByName("USER");
        if (privilegeUser == null) {
            Privilege privilege = new Privilege();
            privilege.setName("USER");
            privilegeUser = privilegeService.create(privilege);
        }

        Group adminGroup = groupService.findByName("ADMINISTRATORS");
        if(adminGroup == null){
            adminGroup = new Group();
            adminGroup.setName("ADMINISTRATORS");
            adminGroup.setCode("ADMINISTRATORS");
            adminGroup.setPrivileges(Collections.singletonList(privilegeAdministration));
            adminGroup = groupService.create(adminGroup);
        }

        Group userGroup = groupService.findByName("USERS");
        if(userGroup == null){
            userGroup = new Group();
            userGroup.setName("USERS");
            userGroup.setCode("USERS");
            userGroup.setPrivileges(Collections.singletonList(privilegeUser));
            userGroup = groupService.create(userGroup);
        }

        User rootUser = userService.getUserByUsername("root");
        if (rootUser == null) {
            rootUser = new User();
            rootUser.setEmail("rootusermail@mail.com");
            rootUser.setFirstName("Root");
            rootUser.setLastName("User");
            rootUser.setUsername("root");
            rootUser.setNewPassword("pass123");
            rootUser.setGroups(Collections.singletonList(adminGroup));
            rootUser = userService.create(rootUser);
        }
    }

    public static void importAttributeTypes(AttributeTypeService attributeTypeService) {
        List<String> names = Arrays.asList(
                "Breed",
                "Gender",
                "Age",
                "Family",
                "Lifespan",
                "Size",
                "Kids friendly",
                "Trainability",
                "Other"
                );
        names.forEach(name -> {
            if(attributeTypeService.findByName(name) == null){
                AttributeType attributeType = new AttributeType();
                attributeType.setName(name);
                attributeTypeService.create(attributeType);
            }
        });
    }
}
