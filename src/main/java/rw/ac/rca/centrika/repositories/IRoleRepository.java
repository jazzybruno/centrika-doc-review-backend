package rw.ac.rca.centrika.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.ac.rca.centrika.enumerations.EUserRole;
import rw.ac.rca.centrika.models.Role;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IRoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findRoleByRoleName(EUserRole roleName);
}
