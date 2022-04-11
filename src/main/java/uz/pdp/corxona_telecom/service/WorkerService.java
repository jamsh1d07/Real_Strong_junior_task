package uz.pdp.corxona_telecom.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.pdp.corxona_telecom.entity.Address;
import uz.pdp.corxona_telecom.entity.Company;
import uz.pdp.corxona_telecom.entity.User;
import uz.pdp.corxona_telecom.entity.Worker;
import uz.pdp.corxona_telecom.payload.ApiResponse;
import uz.pdp.corxona_telecom.payload.WorkerDto;
import uz.pdp.corxona_telecom.security.repository.AddressRepository;
import uz.pdp.corxona_telecom.security.repository.CompanyRepository;
import uz.pdp.corxona_telecom.security.repository.WorkerRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WorkerService {

    private final WorkerRepository workerRepository;
    private final CompanyRepository companyRepository;
    private final AddressRepository addressRepository;

    public ApiResponse all() {
        List<Worker> all = workerRepository.findAll();
        return new ApiResponse("All", true, all);
    }

    public ApiResponse one(UUID id) {
        Optional<Worker> byId = workerRepository.findById(id);
        if (byId.isPresent()) {
            Worker worker = byId.get();
            return new ApiResponse("found", true, worker);
        } else {
            return new ApiResponse("user not found check your id please", false);
        }
    }

    public ApiResponse add(WorkerDto dto) {

        Optional<Worker> optionalWorker = workerRepository.findByPassportId(dto.getPassportId());
        if (optionalWorker.isPresent()) {
            return new ApiResponse("Worker already exists", false);
        }

        Address address = null;
        Company company = null;
        Optional<Company> byId = companyRepository.findById(dto.getCompanyId());
        Optional<Address> byId1 = addressRepository.findById(dto.getAddressId());
        if (byId.isEmpty()) {
            if (byId1.isEmpty()) {
                return new ApiResponse("Address not found", false);
            }
            return new ApiResponse("Company not found", false);
        }
        company = byId.get();
        address = byId1.get();
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal.getCompany().equals(company)) {
        Worker worker = new Worker();

        worker.setAddress(address);
        worker.setCompany(company);
        worker.setFullName(dto.getFullName());
        worker.setPassportId(dto.getPassportId());
        worker.setPosition(dto.getPosition());
        worker.setPhoneNumber(dto.getPhoneNumber());

        workerRepository.save(worker);
        return new ApiResponse("Worker saved", true, worker);
        }else {
            return new ApiResponse("It is not your company",false);
        }
    }

    public ApiResponse edit(UUID id, WorkerDto dto) {
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        if (optionalWorker.isPresent() ) {
            Optional<Worker> optionalWorker1 = workerRepository.findByPassportId(dto.getPassportId());
            if (optionalWorker1.isPresent()&& optionalWorker1.get().getId().equals(id)) {
                return new ApiResponse("Worker already exists", false);
            }

            Address address = null;
            Company company = null;
            Optional<Company> byId = companyRepository.findById(dto.getCompanyId());
            Optional<Address> byId1 = addressRepository.findById(dto.getAddressId());
            if (byId.isEmpty()) {
                if (byId1.isEmpty()) {
                    return new ApiResponse("Address not found", false);
                }
                return new ApiResponse("Company not found", false);
            }
            company = byId.get();
            address = byId1.get();
            User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (principal.getCompany().equals(company)) {
                Worker worker = optionalWorker.get();

                worker.setAddress(address);
                worker.setCompany(company);
                worker.setFullName(dto.getFullName());
                worker.setPassportId(dto.getPassportId());
                worker.setPosition(dto.getPosition());
                worker.setPhoneNumber(dto.getPhoneNumber());

                workerRepository.save(worker);
                return new ApiResponse("Worker saved", true, worker);
            }else {
                return new ApiResponse("It is not your company",false);
            }

        }else {
            return new ApiResponse("User not found",false);
        }
    }

    public ApiResponse delete(UUID id) {
        Optional<Worker> byId = workerRepository.findById(id);
        if (byId.isPresent()) {
            Worker worker = byId.get();

            User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (principal.getCompany().equals(worker.getCompany())){
                workerRepository.delete(worker);
                return new ApiResponse("Worker is deleted",true);
            }else {
                return new ApiResponse("Wrong company!👨‍🎓",false);
            }
        }else {
            return new ApiResponse("Worker not found",false);
        }
    }
}
