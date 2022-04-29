package pets;

import pets.services.AttributeTypeService;
import pets.services.GroupService;
import pets.services.PrivilegeService;
import pets.services.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(
		prePostEnabled = true,
		securedEnabled = true,
		jsr250Enabled = true)
public class PetsApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(PetsApplication.class, args);
		UserService userService = (UserService) context.getBean("userService");
		GroupService groupService = (GroupService) context.getBean("groupService");
		PrivilegeService privilegeService = (PrivilegeService) context.getBean("privilegeService");
		AttributeTypeService attributeTypeService = (AttributeTypeService) context.getBean("attributeTypeService");
		ImportData.importBasicUsersGroupsAndPrivileges(userService, groupService, privilegeService);
		ImportData.importAttributeTypes(attributeTypeService);
	}
}
