package org.ru.skypro.coursework.AuctionSystem.service;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.ru.skypro.coursework.AuctionSystem.model.Report;
import org.ru.skypro.coursework.AuctionSystem.model.projections.LotFullInfo;
import org.ru.skypro.coursework.AuctionSystem.model.projections.LotInfoForCSV;
import org.ru.skypro.coursework.AuctionSystem.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService{
    private static final Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);
    private final LotRepository lotRepository;
    private final ReportRepository reportRepository;

    public ReportServiceImpl(LotRepository lotRepository, ReportRepository reportRepository) {
        this.lotRepository = lotRepository;
        this.reportRepository = reportRepository;
    }

    public Object[] serializeLotInfoForCSV (LotFullInfo lotFullInfo) {
        return new Object[]{lotFullInfo.getLotId(),lotFullInfo.getTitle(),lotFullInfo.getLotStatus(),lotFullInfo.getLastBidder(),lotFullInfo.getBidPrice(),"\n"};
   }

    @Override
    public ResponseEntity<Resource> getCSVFile() throws IOException {
        String fileName = "Lot_report_as_" + LocalDateTime.now() +
                ".csv";
        Path path = Path.of(
                "src/main/java/org/ru/skypro/coursework/AuctionSystem",
                "/REPORTS",
                fileName);
        try (Writer writer = new FileWriter(path.toFile());
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.newFormat(';'))) {
            csvPrinter.printRecord("id","title", "status", "lastBidder", "bidPrice","\n");
            for (LotFullInfo r  : lotRepository.findAllLotFullInfo()) {
                csvPrinter.printRecord(serializeLotInfoForCSV(r));
            }
            csvPrinter.flush();
            System.out.println("CSV file written successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Report report = new Report(String.valueOf(path));
        reportRepository.save(report);

        Long lastReportId = reportRepository.findLastReportId();
        logger.info("Report with report_id=" + lastReportId +" saved to the database");

        return downloadReportFile(path);
    }

    @Override
    public ResponseEntity<Resource> downloadReportFile(Path path) throws IOException {
        try {

            logger.info("The file exists=" + Files.exists(path) + ". File name=" + path.getFileName());

            Resource resource = new ByteArrayResource(Files.readAllBytes(path));

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + path.getFileName() + "\"")
                    .header(HttpHeaders.CONTENT_TYPE)
                    .body(resource);
        }
        catch (Exception e)
        {
            logger.error("The report with this id does not exist or the file path is incorrect");
            throw new NoSuchFileException("The report with this id does not exist or the file path is incorrect");
        }

    }
}