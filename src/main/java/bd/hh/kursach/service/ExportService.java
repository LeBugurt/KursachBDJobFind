package bd.hh.kursach.service;

import java.io.IOException;
import java.util.List;

public interface ExportService {

    <T> String createCsvFile(String fileName, List<T> data) throws IOException;

}
