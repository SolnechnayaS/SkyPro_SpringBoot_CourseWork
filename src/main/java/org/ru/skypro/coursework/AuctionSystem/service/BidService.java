package org.ru.skypro.coursework.AuctionSystem.service;

import org.ru.skypro.coursework.AuctionSystem.model.Bid;

public interface BidService {
    Bid getFirstBid(Long id);

    Bid getLastBid(Long id);

    void createBid(Long lotId, String bidderName);

    String getMostFrequentBidder(Long id);
}
