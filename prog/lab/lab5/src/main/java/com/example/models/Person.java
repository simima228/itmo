package com.example.models;

public class Person {
    private final String name; //Поле не может быть null, Строка не может быть пустой
    private final int height; //Значение поля должно быть больше 0
    private final Country nationality; //Поле не может быть null
    private final Location location; //Поле не может быть null

    public Person(String name, int height, Country nationality, Location location) {
        this.name = name;
        this.height = height;
        this.nationality = nationality;
        this.location = location;
    }

    public static boolean checkName(String checkName){
        return checkName != null && !checkName.isEmpty();
    }

    public static boolean checkHeight(int checkHeight){
        return checkHeight > 0;
    }

    public static boolean checkNationality(Country checkNationality){
        return checkNationality != null;
    }

    public static boolean checkLocation(Location checkLocation){
        return checkLocation != null;
    }

    public String getName() {
        return name;
    }

    public int getHeight() {
        return height;
    }
    public Country getNationality() {
        return nationality;
    }
    public String getLocation() {
        return location.getX() + "," + location.getY() + "," + location.getZ();
    }

    @Override
    public String toString() {
        return "Человек" + "[" + "Имя" + ": " + name + ", " + "Рост" + ": "
                + height + ", " + "Национальность" + ": " + nationality +
                ", " + location + "]";
    }
}
