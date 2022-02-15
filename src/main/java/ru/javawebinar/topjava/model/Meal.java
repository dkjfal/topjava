package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Meal extends AbstractBaseEntity implements Comparable<Meal> {
    private LocalDateTime dateTime;
    private String description;
    private int calories;
    private Integer userId;
    private LocalTime localTime;
    private LocalDate localDate;

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.localDate = dateTime.toLocalDate();
        this.localTime = dateTime.toLocalTime();
    }

    public Meal (LocalDateTime dateTime, String description, int calories) {
        super(null);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.localDate = dateTime.toLocalDate();
        this.localTime = dateTime.toLocalTime();
    }

    public Meal() {
        super(null);
    }

    @Override
    public int compareTo(Meal that) {
        return that.getDateTime().compareTo(this.dateTime);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateTime() {
        if (localDate == null)
            return LocalDateTime.now();
        return dateTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalTime getLocalTime() {
        return localTime;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public LocalDate getDate() {
        return localDate;
    }

    public LocalTime getTime() {
        return localTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        this.localDate = dateTime.toLocalDate();
        this.localTime = dateTime.toLocalTime();
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", userId=" + userId +
                '}';
    }
}
