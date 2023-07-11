package org.ru.skypro.coursework.AuctionSystem.repository;

import org.ru.skypro.coursework.AuctionSystem.model.Bid;
import org.ru.skypro.coursework.AuctionSystem.model.Bidder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface BidderRepository extends CrudRepository <Bidder, Integer>,
        PagingAndSortingRepository<Bidder, Integer> {

    @Query(value = "SELECT * FROM bidders",
            nativeQuery = true)
    List<Bidder> findAllBidders();

    @Query(value = "SELECT * FROM bidders " +
            "WHERE bidder_name= :bidderName",
            nativeQuery = true)
    Bidder findBidderByBidderName(String bidderName);

}
