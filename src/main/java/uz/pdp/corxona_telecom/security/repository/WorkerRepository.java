package uz.pdp.corxona_telecom.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.corxona_telecom.entity.Worker;

import java.util.Optional;
import java.util.UUID;

public interface WorkerRepository extends JpaRepository<Worker, UUID> {
    Optional<Worker> findByPassportId(String passportId);
}