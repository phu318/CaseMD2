package com.cg.model;

public enum ECategory {

    APPLE(1, "Apple"), SAMSUNG(2, "Samsung");
    private long id;
    private String name;
    ECategory(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static ECategory getBy(String name){
        for(ECategory eCategory : ECategory.values()){
            if (eCategory.toString().equals(name)) {
                return eCategory;
            }
        }
        return null;
    }
    public static ECategory getBy(long idCategory){
        for(ECategory eCategory : ECategory.values()){
            if (eCategory.getId() == idCategory) {
                return eCategory;
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
