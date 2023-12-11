package com.cg.model;

import com.cg.utils.DateUtils;

import java.time.LocalDate;

public class User implements IParser {
    private long id;
    private String name;
    private String password;
    private ERole eRole;
    private LocalDate createAt;
    public static long currentID = 0;


    public User(long id, String name, String password, ERole eRole, LocalDate parse) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.eRole = eRole;
        this.createAt = createAt;
    }
    public User(String name, String password, ERole eRole, LocalDate createAt) {
        this.name = name;
        this.password = password;
        this.eRole = eRole;
        this.createAt = createAt;
    }
    public User(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ERole geteRole() {
        return eRole;
    }

    public void seteRole(ERole eRole) {
        this.eRole = eRole;
    }

    public LocalDate getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }



    @Override
    public void parse(String line) {
        String[] items = line.split(",");
        this.id = Long.parseLong(items[0]);
        this.name = items[1];
        this.password = items[2];

        ERole eRole = ERole.getBy(items[3]);
        this.eRole = eRole;

        this.createAt = DateUtils.parse(items[4]);
    }
    //1,quocphu,123123,ADMIN,2023-09-09
    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%s", this.id, this.name, this.password, this.eRole, this.createAt);
    }
}
