package org.ru.skypro.coursework.AuctionSystem.model.projections;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LotInfoForUser {
    private Long lotId;
    private String title;
    private String description;
    private String lotStatus;
    private Double startPrice;
    private Double bet;

}
