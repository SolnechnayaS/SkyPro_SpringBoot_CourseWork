package org.ru.skypro.coursework.AuctionSystem.repository;

import org.ru.skypro.coursework.AuctionSystem.model.Lot;
import org.ru.skypro.coursework.AuctionSystem.model.projections.LotFullInfo;
import org.ru.skypro.coursework.AuctionSystem.model.projections.LotInfoForUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface LotRepository extends CrudRepository<Lot, Integer>,
        PagingAndSortingRepository<Lot, Integer> {

    @Query(value = "SELECT * FROM lot",
            nativeQuery = true)
    List<Lot> findAllLot();

    @Query("SELECT new org.ru.skypro.coursework.AuctionSystem.model.projections" +
            ".LotFullInfo(l.lotId, " +
            "l.title, " +
            "l.description, " +
            "l.lotStatus, " +
            "l.startPrice, " +
            "l.bidPrice, " +
            "(SELECT b.bidder.bidderName FROM Bid b WHERE b.bidDate=(SELECT MAX(b.bidDate) FROM Bid b WHERE b.lot.lotId = l.lotId))) " +
            "FROM Lot l " +
            "order by l.lotId")
    List<LotFullInfo> findAllLotFullInfo();

    @Query("SELECT new org.ru.skypro.coursework.AuctionSystem.model.projections" +
            ".LotFullInfo(l.lotId, " +
            "l.title, " +
            "l.description, " +
            "l.lotStatus, " +
            "l.startPrice, " +
            "l.bidPrice, " +
            "(SELECT b.bidder.bidderName FROM Bid b WHERE b.bidDate=(SELECT MAX(b.bidDate) FROM Bid b WHERE b.lot.lotId = l.lotId))) " +
            "FROM Lot l " +
            "WHERE l.lotId= :lotId")
    LotFullInfo findAllLotFullInfoByLotId(Long lotId);

    @Query("SELECT new org.ru.skypro.coursework.AuctionSystem.model.projections" +
            ".LotInfoForUser(" +
            "l.lotId, " +
            "l.title, " +
            "l.description, " +
            "l.lotStatus, " +
            "l.startPrice, " +
            "l.bet)" +
            "FROM Lot l " +
            "order by l.lotId")
    List<LotInfoForUser> findAllLotInfoForUser();

    @Query("SELECT new org.ru.skypro.coursework.AuctionSystem.model.projections" +
            ".LotInfoForUser(" +
            "l.lotId, " +
            "l.title, " +
            "l.description, " +
            "l.lotStatus, " +
            "l.startPrice, " +
            "l.bet)" +
            "FROM Lot l " +
            "order by l.lotId")
    Page<LotInfoForUser> findAllLotInfoForUser(PageRequest pageRequest);

    @Query(value = "SELECT * FROM lot WHERE lot_id= :lotId",
            nativeQuery = true)
    Lot findLotByLotId(Long lotId);

    @Query("SELECT new org.ru.skypro.coursework.AuctionSystem.model.projections" +
            ".LotInfoForUser(" +
            "l.lotId, " +
            "l.title, " +
            "l.description, " +
            "l.lotStatus, " +
            "l.startPrice, " +
            "l.bet)" +
            "FROM Lot l " +
            "WHERE l.lotStatus = :lotStatus " +
            "order by l.lotId")
    List<LotInfoForUser> findAllLotInfoForUserByStatus(String lotStatus);

    @Query("SELECT new org.ru.skypro.coursework.AuctionSystem.model.projections" +
            ".LotInfoForUser(" +
            "l.lotId, " +
            "l.title, " +
            "l.description, " +
            "l.lotStatus, " +
            "l.startPrice, " +
            "l.bet)" +
            "FROM Lot l " +
            "WHERE l.lotStatus = :lotStatus " +
            "order by l.lotId")
    Page<LotInfoForUser> findAllLotInfoForUserByStatusPageable(String lotStatus, PageRequest pageRequest);

}
