package bd.hh.kursach.web.controller;

import bd.hh.kursach.service.PostgresBackupService;
import bd.hh.kursach.service.YandexDiskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BackupController {

    private final PostgresBackupService backupService;
    private final YandexDiskService yandexDiskService;

    @PostMapping("/backup/trigger")
    public ResponseEntity<String> triggerBackup() {
        log.info("Запуск бэкапа базы данных по запросу");
        try {
            String backupPath = backupService.createBackup();
            yandexDiskService.uploadFile(backupPath);
            Files.deleteIfExists(Paths.get(backupPath));
            log.info("Бэкап базы данных успешно завершен и загружен на Яндекс.Диск");
            return ResponseEntity.ok("Backup triggered and completed successfully");
        } catch (IOException e) {
            log.error("Ошибка при создании или загрузке бэкапа", e);
            return ResponseEntity.status(500).body("Error during backup: " + e.getMessage());
        }
    }
}