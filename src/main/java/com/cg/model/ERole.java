package com.cg.model;

public enum ERole {
    USER(1, "User"), ADMIN(2, "Admin");
    private long id;
    private String name;
    ERole(long id, String name) {
        this.id = id;
        this.name = name;
    }
    public static ERole getBY(String name){
        for (ERole eRole : ERole.values()){
            if (eRole.toString().equals(name)){
                return eRole;
            }
        }
        return null;
    }
    public static ERole getBy(String str){
        for (ERole erole: ERole.values()) {
            if (erole.toString().equals(str)){
                return erole;
            }
        }
        return null;
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
}
