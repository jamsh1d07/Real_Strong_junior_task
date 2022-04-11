package uz.pdp.corxona_telecom.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Company {

    @Id
    private UUID id = UUID.randomUUID();

    private String name;

    private String directorFullName;

    @OneToOne
    private Address address;

    private String email;

    private String website;

    private String phoneNumber;


}
