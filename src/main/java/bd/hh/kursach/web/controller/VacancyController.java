package bd.hh.kursach.web.controller;

import bd.hh.kursach.service.ExportService;
import bd.hh.kursach.service.StatusService;
import bd.hh.kursach.service.VacancyService;
import bd.hh.kursach.web.dto.VacancyDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/vacancies")
public class VacancyController {

    private final VacancyService vacancyService;
    private final StatusService statusService;
    private final ExportService exportService;
    private static final String JSON_DIRECTORY = "src/main/resources/resultJson/Vacancies.json";

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public VacancyController(VacancyService vacancyService, StatusService statusService, ExportService exportService) {
        this.vacancyService = vacancyService;
        this.statusService = statusService;
        this.exportService = exportService;
    }

    @GetMapping
    public ResponseEntity<List<VacancyDto>> getAllVacancies() {
        return ResponseEntity.ok(vacancyService.getAllVacancies());
    }

    @GetMapping("/export/json")
    public ResponseEntity<String> exportVacanciesToJson() {
        try {
            objectMapper.writeValue(Paths.get(JSON_DIRECTORY).toFile(), vacancyService.getAllVacancies());
            return new ResponseEntity<>("Vacancies exported to JSON successfully.", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to export vacancies to JSON: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/export/csv")
    public ResponseEntity<String> exportVacanciesToCsv() {
        try {
            List<VacancyDto> vacancies = vacancyService.getAllVacancies();
            String filePath = exportService.createCsvFile("vacancies.csv", vacancies);
            return ResponseEntity.ok("Vacancies exported to: " + filePath);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error exporting vacancies");
        }
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