package rw.ac.rca.centrika.services.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.ac.rca.centrika.enumerations.EUserRole;
import rw.ac.rca.centrika.exceptions.BadRequestAlertException;
import rw.ac.rca.centrika.exceptions.InternalServerErrorException;
import rw.ac.rca.centrika.exceptions.NotFoundException;
import rw.ac.rca.centrika.exceptions.ResourceNotFoundException;
import rw.ac.rca.centrika.models.Role;
import rw.ac.rca.centrika.repositories.IRoleRepository;
import rw.ac.rca.centrika.services.RoleService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class RoleServiceImpl implements RoleService {
    private IRoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(IRoleRepository iRoleRepository) {
        this.roleRepository = iRoleRepository;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role getRoleById(UUID roleId) {
        return roleRepository.findById(roleId).orElseThrow(()-> {
            try {
                throw new ResourceNotFoundException("The Role was not found");
            } catch (ResourceNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public Role getRoleByName(EUserRole roleName) {
        return roleRepository.findRoleByRoleName(roleName).orElseThrow(()->{
            try {
                throw new ResourceNotFoundException("The Role was not found");
            } catch (ResourceNotFoundException e){
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public Role createRole(EUserRole roleName) {
        Optional<Role> optionalRole = roleRepository.findRoleByRoleName(roleName);
        if(optionalRole.isPresent()){
            throw new BadRequestAlertException("The role already exists");
        }else{
            Role role = new Role(
                    roleName
            );
           try {
               roleRepository.save(role);
               return role;
           }catch (Exception e){
               throw new InternalServerErrorException(e.getMessage());
           }
        }
    }

    @Override
    public Role deleteRole(UUID roleId) {
        Role role = roleRepository.findById(roleId).orElseThrow(()-> {throw new NotFoundException("The role is not present");
        });
        try {
            roleRepository.deleteById(roleId);
            return role;
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public boolean isRolePresent(EUserRole roleName) {
        return roleRepository.findRoleByRoleName(roleName).isPresent();
    }
}
