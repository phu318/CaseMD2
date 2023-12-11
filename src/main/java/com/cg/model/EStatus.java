package com.cg.model;

public enum EStatus {
    PENDING(1, "PENDING"), PAYED(2, "PAYED"), SHIPPED(2, "SHIPPED");
    private long id;
    private String name;

    EStatus(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
