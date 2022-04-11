package uz.pdp.corxona_telecom.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.corxona_telecom.payload.ApiResponse;
import uz.pdp.corxona_telecom.payload.WorkerDto;
import uz.pdp.corxona_telecom.service.WorkerService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/worker")
@RequiredArgsConstructor
public class WorkerController {

    private final WorkerService workerService;

    /**
     * all workers on database
     *
     * @return
     */
    @PreAuthorize(value = "hasAnyRole(company,admin)")
    @GetMapping("/list")
    public HttpEntity<?> all() {
        ApiResponse apiResponse = workerService.all();
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * searching by id
     *
     * @param id
     * @return
     */
    @GetMapping("/one/{id}")
    public HttpEntity<?> one(@PathVariable UUID id) {
        ApiResponse apiResponse = workerService.one(id);

        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    /**
     * add worker
     * @param dto
     * @return
     */
    @PreAuthorize(value = "hasRole(company)")
    @PostMapping("/add")
    public HttpEntity<?> add(@Valid @RequestBody WorkerDto dto) {
        ApiResponse apiResponse = workerService.add(dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    /**
     * edit worker
     * @param id
     * @param dto
     * @return
     */
    @PreAuthorize(value = "hasRole(company)")
    @PutMapping("/edit/{id}")
    public HttpEntity<?> edit(@PathVariable UUID id,@RequestBody WorkerDto dto){
        ApiResponse apiResponse = workerService.edit(id,dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 202 : 409).body(apiResponse);
    }

    /**
     *  only company can delete worker
     * @param id
     * @return
     */
    @PreAuthorize(value = "hasRole(company)")
    @DeleteMapping("/delete/{id}")
    public HttpEntity<?> delete(@PathVariable UUID id){
        ApiResponse apiResponse = workerService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    // xatolik bo'lsa requiredlarni ko'rsatadi
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }


}
