package bd.hh.kursach.web.controller;

import bd.hh.kursach.service.StatusService;
import bd.hh.kursach.service.VacancyService;
import bd.hh.kursach.web.dto.VacancyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/vacancies")
public class VacancyController {

    private final VacancyService vacancyService;
    private final StatusService statusService;

    @Autowired
    public VacancyController(VacancyService vacancyService, StatusService statusService) {
        this.vacancyService = vacancyService;
        this.statusService = statusService;
    }

    @GetMapping
    public ResponseEntity<List<VacancyDto>> getAllVacancies() {
        return ResponseEntity.ok(vacancyService.getAllVacancies());
    }

    @GetMapping("/search")
    public ResponseEntity<List<VacancyDto>> getVacanciesByKeywords(@RequestParam String keyword) {
        return ResponseEntity.ok(vacancyService.getVacanciesByKeyWord(keyword));
    }

    @GetMapping("/onlyMine/{id}")
    public ResponseEntity<List<VacancyDto>> getVacanciesByTabOnlyMine(@PathVariable UUID id) {
        return ResponseEntity.ok(vacancyService.getVacanciesByTabOnlyMy(id));
    }

    @GetMapping("/sort/oldFirst")
    public ResponseEntity<List<VacancyDto>> getVacanciesByOldFirst() {
        return ResponseEntity.ok(vacancyService.getAllVacanciesOldFirst());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatusVacancy(@PathVariable UUID id, @RequestParam String status) {
        return ResponseEntity.ok(statusService.updateVacancyStatus(status, id));
    }

    @PostMapping(path = "/add")
    public ResponseEntity<UUID> createVacancy(@RequestBody VacancyDto vacancyDto) {
        return new ResponseEntity<>(vacancyService.createVacancy(vacancyDto), HttpStatus.CREATED);
    }

    @GetMapping(path = "/find/{id}")
    public ResponseEntity<VacancyDto> findVacancyById(@PathVariable("id") UUID id) {
        return vacancyService.findVacancyById(id) != null ? new ResponseEntity<>(vacancyService.findVacancyById(id), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(path = "/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UUID> updateVacancy(@RequestBody VacancyDto vacancyDto) {
        return ResponseEntity.ok(vacancyService.updateVacancy(vacancyDto).getId());
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<VacancyDto> deleteVacancyById(@PathVariable("id") UUID id) {
        return ResponseEntity.status(vacancyService.deleteVacancyById(id) ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND).build();
    }
}