package az.nicat.hospitalsystem.repository;

import az.nicat.hospitalsystem.entity.Authority;
import az.nicat.hospitalsystem.entity.enums.UserAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Optional<Authority> findByAuthority(UserAuthority userAuthority);
}
