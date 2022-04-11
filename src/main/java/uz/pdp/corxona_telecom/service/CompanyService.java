package uz.pdp.corxona_telecom.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.pdp.corxona_telecom.entity.Address;
import uz.pdp.corxona_telecom.entity.Company;
import uz.pdp.corxona_telecom.entity.User;
import uz.pdp.corxona_telecom.payload.ApiResponse;
import uz.pdp.corxona_telecom.payload.CompanyDto;
import uz.pdp.corxona_telecom.security.repository.AddressRepository;
import uz.pdp.corxona_telecom.security.repository.CompanyRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final AddressRepository addressRepository;

    public ApiResponse all() {
        List<Company> all = companyRepository.findAll();
        return new ApiResponse("All", true, all);
    }

    public ApiResponse add(CompanyDto dto) {
        Optional<Company> optionalCompany = companyRepository.findByName(dto.getName());
        if (optionalCompany.isPresent()) {
            return new ApiResponse("This already exists", false);
        } else {
            User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (principal.getRole().equals("admin")) {
                // adding :
                Optional<Address> optionalAddress = addressRepository.findById(dto.getAddressId());
                if (optionalAddress.isPresent()) {
                    Address address = optionalAddress.get();

                    Company company = new Company();

                    company.setAddress(address);
                    company.setEmail(dto.getEmail());
                    company.setDirectorFullName(dto.getDirectorFullName());
                    company.setName(dto.getName());
                    company.setPhoneNumber(dto.getPhoneNumber());
                    company.setWebsite(dto.getWebsite());

                    Company save = companyRepository.save(company);
                    return new ApiResponse("Company saved",true,save);
                }
            }
        }
        return new ApiResponse("Something went wrong",false);
    }

    public ApiResponse delete(UUID id) {
        //check company before :
        Optional<Company> byId = companyRepository.findById(id);
        if (byId.isPresent()) {
            Company company = byId.get();
            companyRepository.delete(company);
            return new ApiResponse("Company deleted",true);
        }
        return new ApiResponse("Company not found",false);
    }

    public ApiResponse edit(UUID id, CompanyDto dto) {
        //check company before :
        Optional<Company> byId = companyRepository.findById(id);
        if (byId.isPresent()) {
            Company company = byId.get();

            User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (principal.getRole().equals("company") && !principal.getCompany().equals(company)){
                return new ApiResponse("It is not your company",false);
            }

            // check company name already exists or not ?

            Optional<Company> byNameAndIdNot = companyRepository.findByNameAndIdNot(dto.getName(), id);
            if (byNameAndIdNot.isPresent()) {
                return new ApiResponse("Already exists",false);
            }

            Optional<Address> byId1 = addressRepository.findById(dto.getAddressId());

            if (byId1.isPresent()) {
                Address address = byId1.get();

            company.setWebsite(dto.getWebsite());
            company.setName(dto.getName());
            company.setEmail(dto.getEmail());
            company.setPhoneNumber(dto.getPhoneNumber());
            company.setAddress(address);
            company.setDirectorFullName(dto.getDirectorFullName());

                Company save = companyRepository.save(company);

                return new ApiResponse("Company successfully edited",true,save);

            }
            return new ApiResponse("not found address",false);
        }
        return new ApiResponse("Company not found",false);
    }
}
