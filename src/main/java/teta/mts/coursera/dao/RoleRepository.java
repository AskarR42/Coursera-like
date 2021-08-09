package teta.mts.coursera.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teta.mts.coursera.domain.Role;

@Repository
public interface RoleRepository extends JpaRepository <Role, Long> {
}
