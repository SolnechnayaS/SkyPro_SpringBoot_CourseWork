package org.ru.skypro.coursework.AuctionSystem.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ru.skypro.coursework.AuctionSystem.model.Lot;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LotDTOnewLot {
    private String title;
    private String description;
    private Double startPrice;
    private Double bet;

    public static LotDTOnewLot fromLot(Lot lot) {
        LotDTOnewLot lotDTOnewLot = new LotDTOnewLot();
        lotDTOnewLot.setTitle(lot.getTitle());
        lotDTOnewLot.setDescription(lot.getDescription());
        lotDTOnewLot.setStartPrice(lot.getStartPrice());
        lotDTOnewLot.setBet(lot.getBet());
        return lotDTOnewLot;
    }

    public Lot toLot() {
        Lot lot = new Lot();
        lot.setTitle(this.getTitle());
        lot.setDescription(this.getDescription());
        lot.setStartPrice(this.getStartPrice());
        lot.setBet(this.getBet());

        return lot;
    }

}
