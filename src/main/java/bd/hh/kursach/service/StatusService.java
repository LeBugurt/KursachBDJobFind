package bd.hh.kursach.service;

import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface StatusService {

    ResponseEntity<?> updateVacancyStatus(String status, UUID id);

    ResponseEntity<?> updateResumeStatus(UUID id, String status);
}
