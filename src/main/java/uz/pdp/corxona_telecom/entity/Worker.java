package uz.pdp.corxona_telecom.entity;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Worker {
    @Id
    private UUID id = UUID.randomUUID();

    @Column(nullable = false,unique = true)
//    @Pattern(regexp = "[A-Z]{2}[0-9]{7}")
    private String passportId;

    private String fullName;

    private String position;

    @Column(nullable = false,unique = true)
    private String phoneNumber;

    @OneToOne
    private Address address;

    @ManyToOne(cascade = CascadeType.ALL)
    private Company company;



}
