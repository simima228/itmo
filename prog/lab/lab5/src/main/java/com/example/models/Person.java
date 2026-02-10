package com.example.models;

import com.example.etc.CheckInterface;

public class Person implements CheckInterface {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private int height; //Значение поля должно быть больше 0
    private Country nationality; //Поле не может быть null
    private Location location; //Поле не может быть null

    public Person(String name, int height, Country nationality, Location location) {
        this.name = name;
        this.height = height;
        this.nationality = nationality;
        this.location = location;
    }

    @Override
    public boolean check(){
        return name != null && !name.isEmpty() && height > 0 && nationality != null && location != null;
    }
}
