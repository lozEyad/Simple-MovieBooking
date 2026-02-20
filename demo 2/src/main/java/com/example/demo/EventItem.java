package com.example.demo;

public class EventItem {
    private String title;
    private String category;
    private String date;
    private String time ;
    private String Location;
    private int capacity;
    private String description;

    public EventItem(String title, String category, String date, String time, String location, int capacity, String description) {
        this.title = title;
        this.category = category;
        this.date = date;
        this.time = time;
        this.Location = location;
        this.capacity = capacity;
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        this.Location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getCategory() {
        return category;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}