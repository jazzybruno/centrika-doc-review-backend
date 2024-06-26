package rw.ac.rca.centrika;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import rw.ac.rca.centrika.enumerations.EDocStatus;
import rw.ac.rca.centrika.enumerations.EUserRole;
import rw.ac.rca.centrika.repositories.IRoleRepository;
import rw.ac.rca.centrika.services.serviceImpl.RoleServiceImpl;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
@EnableCaching
public class CentrikaDocReview {
	private RoleServiceImpl roleService;
	@Autowired
	public CentrikaDocReview(RoleServiceImpl roleService) {
		this.roleService = roleService;
	}
	public static void main(String[] args) {
		SpringApplication.run(CentrikaDocReview.class, args);
	}
	@Bean
	public void registerRoles(){
		Set<EUserRole> userRoleSet = new HashSet<>();
		userRoleSet.add(EUserRole.ADMIN);
		userRoleSet.add(EUserRole.USER);
		userRoleSet.add(EUserRole.DEPARTMENT_HEAD);
		for (EUserRole role : userRoleSet){
			if(!(roleService.isRolePresent(role))){
				roleService.createRole(role);
			}
		}
	}
}
