package org.ru.skypro.coursework.AuctionSystem.security;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "authorities")
public class Authority {
    private static final Logger logger = LoggerFactory.getLogger(Authority.class);

    @Id
    @Column (name = "authority")
    private String authority;

    @OneToMany(mappedBy = "authority", orphanRemoval = true, cascade = CascadeType.ALL)
    protected List<AuthUser> authUsers;

}