package uz.pdp.corxona_telecom.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.corxona_telecom.entity.Company;

import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {
    Optional<Company> findByName(String name);
    Optional<Company> findByNameAndIdNot(String name,UUID id);
}