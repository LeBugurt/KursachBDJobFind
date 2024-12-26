package bd.hh.kursach.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class PostgresBackupService {

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${pg.dump-path}")
    private String pgDumpPath; //  полный путь к pg_dump

    @Value("${backup.file-location}")
    private String backupFileLocation; // путь к папке где хранятся бэкапы

    public String createBackup() throws IOException {
        String dbName = getDbName();
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String backupFileName = String.format("%s_%s.sql", dbName, timestamp);
        Path backupFile = Paths.get(backupFileLocation,backupFileName);


        List<String> command = Arrays.asList(
                pgDumpPath,
                "-U",
                username,
                "-p",
                extractPortFromUrl(dbUrl),
                "-h",
                extractHostFromUrl(dbUrl),
                "-Fc", // Формат c (часто более быстрый, чем custom)
                "-f",
                backupFile.toString(),
                dbName
        );


        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.environment().put("PGPASSWORD", password);


        try {
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                log.info("Backup created: {}", backupFile);
                return backupFile.toString(); // Возвращаем путь к файлу
            } else {
                throw new IOException("pg_dump failed with exit code: " + exitCode);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Backup process interrupted", e);
        }
    }

    private String getDbName() {
        String[] parts = dbUrl.split("/");
        return parts[parts.length - 1];
    }

    private String extractHostFromUrl(String url) {
        String[] parts = url.split("/");
        String hostAndPort = parts[2];
        return hostAndPort.split(":")[0];
    }

    private String extractPortFromUrl(String url) {
        String[] parts = url.split("/");
        String hostAndPort = parts[2];
        return hostAndPort.split(":")[1];
    }
}