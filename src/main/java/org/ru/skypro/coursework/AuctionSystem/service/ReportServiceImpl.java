package org.ru.skypro.coursework.AuctionSystem.service;

import org.ru.skypro.coursework.AuctionSystem.model.Report;
import org.ru.skypro.coursework.AuctionSystem.model.projections.LotInfoForCSV;
import org.ru.skypro.coursework.AuctionSystem.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

    public String serializeLotInfoForCSV (LotInfoForCSV lotInfoForCSV) {
        return lotInfoForCSV.getLotId()+
                ";"+
                lotInfoForCSV.getTitle()+
                ";"+
                lotInfoForCSV.getLotStatus()+
                ";"+
                lotInfoForCSV.getLastBidder()+
                ";"+
                lotInfoForCSV.getBidPrice()+
                ";";
   }

    @Override
    public ResponseEntity<Resource> getCSVFile() throws IOException {
        String headers = "id;title;status;lastBidder;currentPrice;\n";
        String csvTextReport = headers+
                        lotRepository
                        .findAllLotFullInfo()
                        .stream()
                        .map(LotInfoForCSV::fromLotFullInfo)
                        .map(this::serializeLotInfoForCSV)
                        .collect(Collectors.joining("\n"));

        String fileName = "Lot_report_as_" + LocalDateTime.now() +
                ".csv";
        Path path = Path.of(
                "src/main/java/org/ru/skypro/coursework/AuctionSystem",
                "/REPORTS",
                fileName);

        Files.writeString(path, csvTextReport);

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