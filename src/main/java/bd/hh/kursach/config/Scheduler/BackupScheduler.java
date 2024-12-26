package bd.hh.kursach.config.Scheduler;

import bd.hh.kursach.service.PostgresBackupService;
import bd.hh.kursach.service.YandexDiskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
@Component
@RequiredArgsConstructor
public class BackupScheduler {

    private final PostgresBackupService backupService;
    private final YandexDiskService yandexDiskService;

    @Scheduled(cron = "0 0 3 * * *") // Каждый день в 3:00
    public void performBackup() {
        log.info("Запуск запланированного бэкапа базы данных");
        try {
            String backupPath =  backupService.createBackup();
            yandexDiskService.uploadFile(backupPath);
            Files.deleteIfExists(Paths.get(backupPath));
            log.info("Бэкап базы данных успешно завершен и загружен на Яндекс.Диск");
        } catch (IOException e) {
            log.error("Ошибка при создании или загрузке бэкапа", e);
        }
    }
}
