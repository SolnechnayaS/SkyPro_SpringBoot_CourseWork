package org.ru.skypro.coursework.AuctionSystem.service;

import org.ru.skypro.coursework.AuctionSystem.model.Bid;
import org.ru.skypro.coursework.AuctionSystem.model.Bidder;
import org.ru.skypro.coursework.AuctionSystem.model.Lot;
import org.ru.skypro.coursework.AuctionSystem.model.LotStatus;
import org.ru.skypro.coursework.AuctionSystem.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class BidServiceImpl implements BidService {

    private static final Logger logger = LoggerFactory.getLogger(BidServiceImpl.class);
    private final BidderRepository bidderRepository;
    private final LotRepository lotRepository;
    private final BidRepository bidRepository;

    public BidServiceImpl(BidderRepository bidderRepository, LotRepository lotRepository, BidRepository bidRepository) {
        this.bidderRepository = bidderRepository;
        this.lotRepository = lotRepository;
        this.bidRepository = bidRepository;
    }

    @Override
    public Bid getFirstBid(Long id) {
        try {
            logger.info("get First Bid");
            return bidRepository.firstBid(id);
        } catch (NullPointerException e) {
            logger.error("Bid by lot id=" + id + " is not found");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect lot id");
        }
    }

    @Override
    public Bid getLastBid(Long id) {
        try {
            logger.info("get Last Bid");
            return bidRepository.lastBid(id);
        } catch (NullPointerException e) {
            logger.error("Bid by lot id=" + id + " is not found");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect lot id");
        }
    }

    @Override
    public void createBid(Long lotId, String bidderName) {
        try {
            Lot lot = lotRepository.findLotByLotId(lotId);
            if (!lot.getLotStatus().equals(LotStatus.STARTED.toString())) {
                logger.error("Incorrect lot status");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect lot status");
            } else {
                if (bidderRepository.findBidderByBidderName(bidderName) == null) {
                    bidderRepository.save(new Bidder(bidderName, null));
                }

                Bid newBid = new Bid();
                newBid.setBidder(bidderRepository.findBidderByBidderName(bidderName));
                newBid.setLot(lot);
                bidRepository.save(newBid);
                logger.info("create new bid by lot id="+lotId);

                Integer countBids = bidRepository.countBid(lotId);
                lot.setBidPrice(countBids * (lot.getBet()) + lot.getStartPrice());
                lotRepository.save(lot);
                logger.info("update lot id="+lotId);
            }
        } catch (NullPointerException e) {
            logger.error("Lot with id=" + lotId + " not found");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lot with id=" + lotId + " not found");
        }
    }

    @Override
    public String getMostFrequentBidder(Long id) {
        try {
            logger.info("get Most Frequent Bidder");
            return bidRepository.bidderName(id);
        }
        catch (NullPointerException e)
        {
            logger.error("Bid by lot id=" + id + " is not found");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect lot id");
        }

    }
}
