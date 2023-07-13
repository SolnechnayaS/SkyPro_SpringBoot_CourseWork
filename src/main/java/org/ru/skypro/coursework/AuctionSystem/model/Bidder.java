package org.ru.skypro.coursework.AuctionSystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ru.skypro.coursework.AuctionSystem.security.Authority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bidders")
public class Bidder {

    @Id
    @Column(name = "bidder_name", nullable = false, unique = true, length = 100)
    private String bidderName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bidder")
    private List<Bid> bidList;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bidder bidder)) return false;
        return getBidderName().equals(bidder.getBidderName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBidderName());
    }
}