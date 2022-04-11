package uz.pdp.corxona_telecom.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CompanyDto {

    @NotNull(message = "name is required")
    private String name;

    @NotNull(message = "director name is required")
    private String directorFullName;

    @NotNull(message = "address is required")
    private UUID addressId;

    private String email;

    private String website;

    @NotNull(message = "required")
    @Pattern(regexp = "[+]998[0-9]{9}")
    private String phoneNumber;
}
