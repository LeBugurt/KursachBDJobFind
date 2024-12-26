package bd.hh.kursach.web.controller;

import bd.hh.kursach.service.ExportService;
import bd.hh.kursach.service.ResumeService;
import bd.hh.kursach.service.StatusService;
import bd.hh.kursach.web.dto.ResumeDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value = "/api/resumes", produces = "application/json;charset=UTF-8")
@RequiredArgsConstructor
public class ResumeController {
    private final ResumeService resumeService;
    private final StatusService statusService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ExportService exportService;
    private static final String JSON_DIRECTORY = "src/main/resources/resultJson/Resume.json";


    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateResumeStatus(@PathVariable UUID id, @RequestParam String status) {
        return ResponseEntity.ok(statusService.updateResumeStatus(id, status));
    }

    @PostMapping(path = "/add",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResumeDto> createResume(@RequestBody ResumeDto resumeDto) {
        return new ResponseEntity<>(resumeService.createResume(resumeDto), HttpStatus.CREATED);
    }

    @GetMapping("/export/json")
    public ResponseEntity<String> exportResumeToJson() {
        try {
            objectMapper.writeValue(Paths.get(JSON_DIRECTORY).toFile(), resumeService.findAllResume());
            return new ResponseEntity<>("Resume exported to JSON successfully.", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to export resume to JSON: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/export/csv")
    public ResponseEntity<String> exportVacanciesToCsv() {
        try {
            List<ResumeDto> vacancies = resumeService.findAllResume();
            String filePath = exportService.createCsvFile("resume-data.csv", vacancies);
            return ResponseEntity.ok("Vacancies exported to: " + filePath);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error exporting vacancies");
        }
    }

    @GetMapping
    public ResponseEntity<List<ResumeDto>> getAllResumes() {
        return ResponseEntity.ok(resumeService.findAllResume());
    }

    @GetMapping(path = "/find/{id}")
    public ResponseEntity<ResumeDto> findResumeById(@PathVariable("id") UUID id) {

        return resumeService.findResumeById(id) != null ? new ResponseEntity<>(resumeService.findResumeById(id),
                HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(path = "/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResumeDto> updateResume(@RequestBody ResumeDto resumeDto) {

        return resumeService.updateResume(resumeDto) != null ? new ResponseEntity<>(resumeService.updateResume(resumeDto),
                HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<ResumeDto> deleteResumeById(@PathVariable("id") UUID id) {
        return ResponseEntity.status(resumeService.deleteResumeById(id) ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler
    public ResponseEntity<String> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
