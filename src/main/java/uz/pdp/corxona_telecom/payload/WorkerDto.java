package uz.pdp.corxona_telecom.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WorkerDto {
    @NotNull(message = "passport is required")
    private String passportId;

    @NotNull(message = "FullName is required")
    private String fullName;

    private String position;

    @NotNull(message = "phone number is required")
    private String phoneNumber;

    private UUID addressId;

    @NotNull(message = "company is required")
    private UUID companyId;

}
