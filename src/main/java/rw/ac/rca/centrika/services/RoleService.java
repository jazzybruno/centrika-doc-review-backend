package rw.ac.rca.centrika.services;

import rw.ac.rca.centrika.enumerations.EUserRole;
import rw.ac.rca.centrika.models.Role;

import java.util.List;
import java.util.UUID;

public interface RoleService {
    public List<Role> getAllRoles();
    public Role getRoleById(UUID roleId);
    public  Role getRoleByName(EUserRole roleName);
    public void createRole(EUserRole roleName);
    public Role deleteRole(UUID roleId);
    public boolean isRolePresent(EUserRole roleName);
}
