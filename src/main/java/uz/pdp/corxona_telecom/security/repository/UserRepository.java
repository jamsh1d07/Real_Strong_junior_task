package uz.pdp.corxona_telecom.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.corxona_telecom.entity.User;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}