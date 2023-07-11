package org.ru.skypro.coursework.AuctionSystem.repository;

import org.ru.skypro.coursework.AuctionSystem.model.Bid;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface BidRepository extends CrudRepository <Bid, Integer>,
        PagingAndSortingRepository<Bid, Integer> {

    @Query(value = "SELECT * FROM bid",
            nativeQuery = true)
    List<Bid> findAllBid();

    @Query(value = "SELECT * FROM bid WHERE lot_id= :lotId " +
            "GROUP BY bidder_name",
            nativeQuery = true)
    List<Bid> findAllBidByLotId(Long lotId);

    @Query (value="SELECT COUNT(bid_id) FROM bid WHERE lot_id= :lotId",
            nativeQuery = true)
    Integer countBid (Long lotId);

    @Query (value="SELECT * FROM bid " +
            "WHERE lot_id= :lotId " +
            "AND bid_date=" +
            "(SELECT MIN(bid_date) " +
            "FROM bid " +
            "WHERE lot_id= :lotId)",
            nativeQuery = true)
    Bid firstBid (Long lotId);

    @Query (value="SELECT * FROM bid " +
            "WHERE lot_id= :lotId " +
            "AND bid_date=" +
            "(SELECT MAX(bid_date) " +
            "FROM bid " +
            "WHERE lot_id= :lotId)",
            nativeQuery = true)
    Bid lastBid (Long lotId);

    @Query(value = "SELECT bidder_name FROM " +
            "(SELECT bidder_name, COUNT(bid_id) " +
            "FROM bid WHERE lot_id= :lotId " +
            "GROUP BY BIDDER_NAME) AS subselect1 " +
            "WHERE count=(SELECT MAX(count) " +
            "FROM (SELECT bidder_name, COUNT(bid_id) " +
            "FROM bid WHERE lot_id= :lotId " +
            "GROUP BY bidder_name) AS subselect2)",
            nativeQuery = true)
    String bidderName(Long lotId);

}
