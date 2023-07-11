package org.ru.skypro.coursework.AuctionSystem.controller;

import org.ru.skypro.coursework.AuctionSystem.model.Bid;
import org.ru.skypro.coursework.AuctionSystem.model.dto.LotDTOnewLot;
import org.ru.skypro.coursework.AuctionSystem.model.projections.LotFullInfo;
import org.ru.skypro.coursework.AuctionSystem.service.BidService;
import org.ru.skypro.coursework.AuctionSystem.service.LotService;
import org.ru.skypro.coursework.AuctionSystem.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    private final LotService lotService;
    private final BidService bidService;
    private final ReportService reportService;
    public AdminController(LotService lotService, BidService bidService, ReportService reportService) {
        this.lotService = lotService;
        this.bidService = bidService;
        this.reportService = reportService;
    }

    //Имя первого ставившего и дата первой ставки
    @GetMapping("/lot/{id}/first")
    public String getFirstBidder(@PathVariable("id") Long id) {
        Bid firstBid = bidService.getFirstBid(id);
        logger.info("get First Bid by lot id="+id);
        return "Первую ставку на лот с id=" + id +
                " сделал участник: " + firstBid.getBidder().getBidderName() +
                ". Дата и время первой ставки на лот: " + firstBid.getBidDate();
    }

    //Возвращает имя ставившего на данный лот наибольшее количество раз
    @GetMapping("/lot/{id}/frequent")
    public String getMostFrequentBidder(@PathVariable("id") Long id) {
        logger.info("get the most frequent bidder by lot id="+id);
        return "Наибольшее число ставок на лот с id=" + id +
                " сделал участник: " + bidService.getMostFrequentBidder(id);
    }

    //Возвращает полную информацию о лоте с последним ставившим и текущей ценой
    @GetMapping("/lot/{id}")
    public LotFullInfo getFullLot(@PathVariable("id") Long id) {
        logger.info("get full information about the lot id="+id);
        return lotService.findAllLotFullInfoByLotId(id);
    }

    @GetMapping("/lot")
    public List<LotFullInfo> getFullLot() {
        logger.info("get full information about all lots");
        return lotService.findAllLotFullInfo();
    }

    //Переводит лот в состояние "начато", которое позволяет делать ставки на лот.
    //Если лот уже находится в состоянии "начато", то ничего не делает и возвращает 200
    @PostMapping("/lot/{id}/start")
    public void startAuction(@PathVariable("id") Long id) {
        logger.info("start auction by lot id="+id);
        lotService.startAuction(id);
    }

    //Переводит лот в состояние "остановлен", которое запрещает делать ставки на лот.
    // Если лот уже находится в состоянии "остановлен", то ничего не делает и возвращает 200
    @PostMapping("/lot/{id}/stop")
    public void stopAuction(@PathVariable("id") Long id) {
        logger.info("stop auction by lot id="+id);
        lotService.stopAuction(id);
    }

    //    Метод создания нового лота,
//    если есть ошибки в полях объекта лота - то нужно вернуть статус с ошибкой
    @PostMapping("/lot")
    public void createLot(@RequestBody LotDTOnewLot lotDTOnewLot) {
        logger.info("create new Lot");
        lotService.createLot(lotDTOnewLot);
    }

    //    Экспортировать все лоты в формате
//    id,title,status,lastBidder,currentPrice
//    в одном файле CSV
    @GetMapping("/lot/export")
    public ResponseEntity<Resource> getCSVFile() throws IOException {
        logger.info("export All Lot to CSV file");
        return reportService.getCSVFile();
    }

}
