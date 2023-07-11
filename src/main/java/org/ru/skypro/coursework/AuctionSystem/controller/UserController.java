package org.ru.skypro.coursework.AuctionSystem.controller;

import org.ru.skypro.coursework.AuctionSystem.model.projections.LotInfoForUser;
import org.ru.skypro.coursework.AuctionSystem.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final LotService lotService;
    private final BidService bidService;
    public UserController(LotService lotService, BidService bidService) {
        this.lotService = lotService;
        this.bidService = bidService;
    }

    //Сделать ставку по лоту
    @PostMapping("/lot/{id}/bid")
    public void createBid(@PathVariable("id") Long lotId,
                          @RequestBody String bidderName) {
        logger.info("create new Bid by lot id="+lotId);
        bidService.createBid(lotId, bidderName);
    }

//    Получить все лоты, основываясь на фильтре статуса и номере страницы
//    Возвращает все записи о лотах без информации о текущей цене и победителе постранично.
//    Если страница не указана, то возвращается первая страница.
//    Номера страниц начинаются с 0.
//    Лимит на количество лотов на странице - 10 штук.
    @GetMapping("/lot")
    public List<LotInfoForUser> findLots(
            @RequestParam(value = "page", defaultValue = "0") Integer pageIndex,
            @RequestParam(value = "size", defaultValue = "10", required = false) int unitPerPage,
            @RequestParam(value = "status", required = false) String status) {
        logger.info("find Lots by Status="+status);
        return lotService.findAllLotInfoForUserByStatusPageable(status, pageIndex, unitPerPage);
    }

}
