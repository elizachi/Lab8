package ru.itmo.common.model;

public class Car {

    private String name; //Поле может быть null
    private boolean cool;

    public Car(){}

    public Car(String name, boolean cool){
        this.name = name;
        this.cool = cool;
    }

    public void setCarName(String name) {
        this.name = name;
    }

    public void setCarCool(boolean cool) {
        this.cool = cool;
    }

    public String getCarName() {
        return name;
    }

    public Boolean getCarCool(){
        return cool;
    }

    @Override
    public String toString() {
        String carInfo = "";
        if (getCarName() != null) {
            carInfo += getCarName() + ", ";
        }
        if (getCarCool()) {
            carInfo += "крутая тачка";
        } else {
            carInfo += "среднячок";
        }
        return carInfo;
    }
}

