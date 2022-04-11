package uz.pdp.corxona_telecom.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "addresses")
public class Address {
    @Id
    private UUID id = UUID.randomUUID();

    private String city;

    private String street;

    private Integer homeNumber;

}
