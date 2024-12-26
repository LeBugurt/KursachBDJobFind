package bd.hh.kursach.service.impl;

import bd.hh.kursach.service.ExportService;
import com.opencsv.CSVWriter;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Service
public class ExportServiceImpl implements ExportService {
    private static final String CSV_DIRECTORY = "src/main/resources/resultCSV";

    @Override
    public  <T> String createCsvFile(String fileName, List<T> data) throws IOException {
        File directory = new File(CSV_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String filePath = Paths.get(CSV_DIRECTORY, fileName).toString();
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath),
                CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER, // Disable quotes
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END)) {
            if (!data.isEmpty()) {
                // Получаем поля объекта для заголовка
                String[] header = getHeader(data.get(0));
                writer.writeNext(header);

                for (T item : data) {
                    String[] row = getRow(item);
                    writer.writeNext(row);
                }
            }

        }
        return filePath;
    }

    private String[] getHeader(Object obj) {
        return Arrays.stream(obj.getClass().getDeclaredFields())
                .map(Field::getName)
                .toArray(String[]::new);
    }

    private String[] getRow(Object obj) {
        return Arrays.stream(obj.getClass().getDeclaredFields())
                .map(field -> {
                    try {
                        field.setAccessible(true);
                        Object value = field.get(obj);
                        return value != null ? value.toString() : "";
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Ошибка csv" + e.getMessage());
                    }
                })
                .toArray(String[]::new);
    }
}
