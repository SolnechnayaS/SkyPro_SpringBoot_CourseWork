package org.ru.skypro.coursework.AuctionSystem.model.projections;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LotInfoForCSV {
    private Long lotId;
    private String title;
    private String lotStatus;
    private String lastBidder;
    private Double bidPrice;

    public static LotInfoForCSV fromLotFullInfo(LotFullInfo lotFullInfo) {
        return new LotInfoForCSV(
                lotFullInfo.getLotId(),
                lotFullInfo.getTitle(),
                lotFullInfo.getLotStatus(),
                lotFullInfo.getLastBidder(),
                lotFullInfo.getBidPrice());
    }

}
