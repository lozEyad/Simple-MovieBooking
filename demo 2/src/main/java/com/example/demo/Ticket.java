package com.example.demo;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Ticket {

    private String Title;
    private int Booked ;

    public Ticket(String title, int booked) {
        Title = title;
        Booked = booked;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getBooked() {
        return Booked;
    }

    public void setBooked(int booked) {
        Booked = booked;
    }
}

