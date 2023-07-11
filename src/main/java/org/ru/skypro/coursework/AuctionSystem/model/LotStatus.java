package org.ru.skypro.coursework.AuctionSystem.model;

public enum LotStatus {
    CREATED("CREATED"),
    STARTED("STARTED"),
    STOPPED ("STOPPED");

    private String lotStatus;

    LotStatus(String lotStatus) {
        this.lotStatus = lotStatus;
    }

    public String getLotStatus() {
        return lotStatus;
    }

    public void setLotStatus(String lotStatus) {
        this.lotStatus = lotStatus;
    }
    @Override
    public String toString() {
        return lotStatus;
    }

}
