package bd.hh.kursach.service;
import bd.hh.kursach.config.props.DiskProp;
import bd.hh.kursach.web.dto.YandexDiskResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class YandexDiskService {

    private final ObjectMapper objectMapper;

    private final DiskProp diskProp;

    public void uploadFile(String filePath) throws IOException {
        File file = new File(filePath);
        String fileName = file.getName();
        String remotePath = String.format("%s/%s", diskProp.getUploadPath(), fileName);
        String uploadUrl = getUploadUrl(remotePath);

        if (uploadUrl == null) {
            log.error("Не удалось получить url для загрузки файла на Яндекс.Диск");
            return;
        }

        HttpPut httpPut = new HttpPut(uploadUrl);
        FileEntity fileEntity = new FileEntity(file);
        httpPut.setEntity(fileEntity);
        httpPut.addHeader("Content-Type", "application/octet-stream");

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(httpPut)) {

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode >= 200 && statusCode < 300) {
                log.info("Файл {} успешно загружен на Яндекс.Диск", fileName);
            } else {
                log.error("Не удалось загрузить файл {}. Статус код: {}", fileName, statusCode);
                try (InputStream is = response.getEntity().getContent()) {
                    String responseBody = new String(is.readAllBytes());
                    log.error("Ответ от Яндекс.Диска: {}", responseBody);
                }
            }
        }
    }


    private String getUploadUrl(String remotePath) throws IOException {
        String apiUrl = String.format("https://cloud-api.yandex.ru/v1/disk/resources/upload?path=%s&overwrite=true", remotePath);
        HttpGet httpGet = new HttpGet(apiUrl);
        httpGet.addHeader("Authorization", "OAuth " + diskProp.getOauthToken());
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(httpGet)) {

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode >= 200 && statusCode < 300) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    YandexDiskResponse yandexDiskResponse = objectMapper.readValue(entity.getContent(), YandexDiskResponse.class);
                    return yandexDiskResponse.getHref();
                }
            } else {
                try(InputStream is = response.getEntity().getContent()) {
                    String responseBody = new String(is.readAllBytes());
                    log.error("Не удалось получить url. Статус код: {}, Ответ от Яндекс.Диска: {}", statusCode, responseBody);
                    return null;
                }
            }
        }

        return null;

    }
}