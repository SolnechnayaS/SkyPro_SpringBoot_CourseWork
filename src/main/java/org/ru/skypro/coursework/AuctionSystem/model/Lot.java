package org.ru.skypro.coursework.AuctionSystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lot")
public class Lot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lot_id")
    private Long lotId;
    @Column(name = "title", nullable = false, length = 64)
    private String title;
    @Column(name = "description", nullable = false, length = 4096)
    private String description;
    @Column(name = "start_price", nullable = false)
    private Double startPrice;
    @Column(name = "bet", nullable = false)
    private Double bet;
    @Column(name = "bid_price")
    private Double bidPrice;
    @Column(name = "status", nullable = false)
    private String lotStatus = LotStatus.CREATED.toString();
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "lot")
    private List<Bid> bidList;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lot lot)) return false;
        return getLotId().equals(lot.getLotId()) && getTitle().equals(lot.getTitle()) && getDescription().equals(lot.getDescription()) && getStartPrice().equals(lot.getStartPrice()) && getBet().equals(lot.getBet());
    }
    @Override
    public int hashCode() {
        return Objects.hash(getLotId(), getTitle(), getDescription(), getStartPrice(), getBet());
    }
}