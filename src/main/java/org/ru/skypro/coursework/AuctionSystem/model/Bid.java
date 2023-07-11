package org.ru.skypro.coursework.AuctionSystem.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Entity
@Table(name = "bid")
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "bid_id")
    private Long bidId;

    @Column (name = "bid_date", nullable = false)
    private LocalDateTime bidDate;

    @ManyToOne  (fetch = FetchType.EAGER)
    @JoinColumn (name = "lot_id")
    private Lot lot;

    @ManyToOne  (fetch = FetchType.EAGER)
    @JoinColumn (name = "bidder_name")
    private Bidder bidder;

    public Bid() {
        this.bidDate = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bid bid)) return false;
        return getBidId().equals(bid.getBidId()) && getBidDate().equals(bid.getBidDate()) && Objects.equals(getLot(), bid.getLot());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBidId(), getBidDate(), getLot());
    }
}
