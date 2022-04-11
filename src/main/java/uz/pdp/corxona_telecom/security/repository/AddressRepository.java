
package uz.pdp.corxona_telecom.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.corxona_telecom.entity.Address;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {
}