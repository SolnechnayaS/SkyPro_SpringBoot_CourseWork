package org.ru.skypro.coursework.AuctionSystem.service;

import org.ru.skypro.coursework.AuctionSystem.model.Lot;
import org.ru.skypro.coursework.AuctionSystem.model.dto.LotDTOnewLot;
import org.ru.skypro.coursework.AuctionSystem.model.projections.LotFullInfo;
import org.ru.skypro.coursework.AuctionSystem.model.projections.LotInfoForUser;

import java.util.List;

public interface LotService {
    List<Lot> findAllLot();
    List<LotInfoForUser> findAllLotInfoForUser();
    List<LotInfoForUser> findAllLotInfoForUserByStatus(String lotStatus);
    List<LotFullInfo> findAllLotFullInfo();
    LotFullInfo findAllLotFullInfoByLotId(Long lotId);
   List <LotInfoForUser> findAllLotInfoForUserByStatusPageable (String lotStatus, int pageIndex, int unitPerPage);
    Lot findLotByLotId(Long lotId);
    void createLot(LotDTOnewLot lotDTOnewLot);
    List<LotInfoForUser> findLots();
    void startAuction(Long lotId);
    void stopAuction(Long lotId);
}