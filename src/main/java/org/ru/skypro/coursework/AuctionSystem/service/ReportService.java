package org.ru.skypro.coursework.AuctionSystem.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.nio.file.Path;

public interface ReportService {
    ResponseEntity<Resource> getCSVFile() throws IOException;
    ResponseEntity<Resource> downloadReportFile(Path path) throws IOException;
}
