package com.example.privalov;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class DailyCurs {

    @JacksonXmlProperty(localName = "Date", isAttribute = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private Date date;

    @JacksonXmlProperty(localName = "name", isAttribute = true)
    private String name;

    @JacksonXmlProperty(localName = "Valute")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Valute> valutes;

    // Публичный конструктор без параметров
    public DailyCurs() {
    }

    // Существующий конструктор с параметрами
    public DailyCurs(Date date, String name, List<Valute> valutes) {
        this.date = date;
        this.name = name;
        this.valutes = valutes;
    }

    // Геттеры и сеттеры
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Valute> getValutes() {
        return valutes;
    }

    public void setValutes(List<Valute> valutes) {
        this.valutes = valutes;
    }

    @Override
    public String toString() {
        return "DailyCurs{" +
                "date=" + date +
                ", name='" + name + '\'' +
                ", valutes=" + valutes +
                '}';
    }
}


