package com.example.privalov;

import com.example.util.DoubleDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Valute {

    @JacksonXmlProperty(localName = "ID", isAttribute = true)
    private String id;

    @JacksonXmlProperty(localName = "Name")
    private String name;

    @JacksonXmlProperty(localName = "Value")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double value;

    // Публичный конструктор без параметров
    public Valute() {
    }

    // Конструктор с параметрами
    public Valute(String id, String name, Double value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }

    // Геттеры и сеттеры
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Valute{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}