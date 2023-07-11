package org.ru.skypro.coursework.AuctionSystem.model.projections;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LotFullInfo {
    private Long lotId;
    private String title;
    private String description;
    private String lotStatus;
    private Double startPrice;
    private Double bidPrice;
    private String lastBidder;

}
