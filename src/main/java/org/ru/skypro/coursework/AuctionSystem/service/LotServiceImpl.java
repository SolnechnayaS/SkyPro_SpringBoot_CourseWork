package org.ru.skypro.coursework.AuctionSystem.service;

import org.ru.skypro.coursework.AuctionSystem.model.Lot;
import org.ru.skypro.coursework.AuctionSystem.model.LotStatus;
import org.ru.skypro.coursework.AuctionSystem.model.dto.LotDTOnewLot;
import org.ru.skypro.coursework.AuctionSystem.model.projections.LotFullInfo;
import org.ru.skypro.coursework.AuctionSystem.model.projections.LotInfoForUser;
import org.ru.skypro.coursework.AuctionSystem.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LotServiceImpl implements LotService {
    private static final Logger logger = LoggerFactory.getLogger(LotServiceImpl.class);
    private final LotRepository lotRepository;

    public LotServiceImpl(LotRepository lotRepository) {
        this.lotRepository = lotRepository;
    }

    @Override
    public List<Lot> findAllLot() {
        logger.info("find All Lot");
        return lotRepository.findAllLot();
    }

    @Override
    public List<LotInfoForUser> findAllLotInfoForUser() {
        logger.info("find All Lot");
        return lotRepository.findAllLotInfoForUser();
    }

    @Override
    public List<LotInfoForUser> findAllLotInfoForUserByStatus(String lotStatus) {
        try {
            logger.info("find All LotInfoForUser By Status=" + lotStatus);
            return lotRepository.findAllLotInfoForUserByStatus(lotStatus);
        } catch (NullPointerException e) {
            logger.error("Incorrect status lot");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lot status=" + lotStatus + " not found");
        }
    }

    @Override
    public List<LotFullInfo> findAllLotFullInfo() {
        logger.info("find All LotFullInfo");
        return lotRepository.findAllLotFullInfo();
    }

    @Override
    public LotFullInfo findAllLotFullInfoByLotId(Long lotId) {
        try {
            logger.info("find All LotFullInfo by lot id="+lotId);
            return lotRepository.findAllLotFullInfoByLotId(lotId);
        } catch (NullPointerException e)
        {
            logger.error("Incorrect lot id");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lot id=" + lotId + " not found");
        }

    }

    @Override
    public List<LotInfoForUser> findAllLotInfoForUserByStatusPageable(String lotStatus, int pageIndex, int unitPerPage) {
        PageRequest lotOfConcretePage = PageRequest.of(pageIndex, unitPerPage);

        Page<LotInfoForUser> page;
        if (lotStatus != null) {
            logger.info("find All LotInfoForUser By Status="+lotStatus);
            page = lotRepository.findAllLotInfoForUserByStatusPageable(lotStatus.toUpperCase(), lotOfConcretePage);
        } else {
            logger.info("find All LotInfoForUser");
            page = lotRepository.findAllLotInfoForUser(lotOfConcretePage);
        }
        return page
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public Lot findLotByLotId(Long lotId) {
        try {
            logger.info("find Lot By Lot Id="+lotId);
            return lotRepository.findLotByLotId(lotId);
        } catch (NullPointerException e)
        {
            logger.info("Lot By Lot Id="+lotId+ " not found");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lot id=" + lotId + " not found");
        }
    }

    @Override
    public void createLot(LotDTOnewLot lotDTOnewLot) {
        Lot lot = lotDTOnewLot.toLot();
        lot.setBidPrice(lotDTOnewLot.getStartPrice());
        lot.setLotStatus(LotStatus.CREATED.toString());
        lotRepository.save(lot);
        logger.info("create new lot");
    }

    @Override
    public List<LotInfoForUser> findLots() {
        logger.info("find All LotInfoForUser");
        return lotRepository.findAllLotInfoForUser();
    }

    @Override
    public void startAuction(Long lotId) {
        try {
            Lot lotToStart = lotRepository.findLotByLotId(lotId);
            if (!lotToStart.getLotStatus().equals(LotStatus.STARTED.toString())) {
                lotToStart.setLotStatus(LotStatus.STARTED.toString());
                lotToStart.setBidPrice(lotToStart.getStartPrice());
                lotRepository.save(lotToStart);
                logger.info ("Lot with id="+lotId+" started");
            }
        } catch (NullPointerException e) {
            logger.error("Lot with id=" + lotId + " not found");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lot with id=" + lotId + " not found");
        }
    }

    @Override
    public void stopAuction(Long lotId) {
        try {
            Lot lotToStop = lotRepository.findLotByLotId(lotId);
            if (!lotToStop.getLotStatus().equals(LotStatus.STOPPED.toString())) {
                lotToStop.setLotStatus(LotStatus.STOPPED.toString());
                lotRepository.save(lotToStop);
                logger.info ("Lot with id="+lotId+" stopped");
            }
        } catch (NullPointerException e) {
            logger.error("Lot with id=" + lotId + " not found");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lot with id=" + lotId + " not found");
        }
    }

}
