package com.example.privalov;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Optional;

public class StonksClient {
    public static void main(String[] args) {
        try {
            // Подключение к базе данных
            DatabaseService databaseService = new DatabaseServiceImpl();

            // Инициализация клиента Retrofit
            Retrofit client = new Retrofit.Builder()
                    .baseUrl("https://www.cbr.ru")
                    .addConverterFactory(JacksonConverterFactory.create(new XmlMapper()))
                    .build();

            // Дата рождения
            LocalDate birthDate = LocalDate.of(2003, 8, 19);

            // Запрос данных о курсе валют на дату рождения
            StonksService stonksService = client.create(StonksService.class);
            Response<DailyCurs> response = stonksService.getDailyCurs(birthDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))).execute();

            // Проверка ответа от сервера
            if (!response.isSuccessful()) {
                System.out.println("Ошибка при получении данных с сервера: " + response.message());
                return;
            }

            DailyCurs dailyCurs = response.body();
            if (dailyCurs == null || dailyCurs.getValutes() == null || dailyCurs.getValutes().isEmpty()) {
                System.out.println("Нет данных о валютах для указанной даты.");
                return;
            }

            // Поиск самой дорогой валюты
            Optional<Valute> maxValute = dailyCurs.getValutes().stream()
                    .filter(valute -> !valute.getName().equals("СДР (специальные права заимствования)"))
                    .max(Comparator.comparingDouble(Valute::getValue));

            // Сохранение в базу данных
            if (maxValute.isPresent()) {
                Valute mv = maxValute.get();
                databaseService.saveMaxValuteOfDate("privalovta", mv, birthDate);
                System.out.println("Самая дорогая валюта на дату рождения: " + mv);
            } else {
                System.out.println("Не удалось найти данные о самой дорогой валюте на указанную дату.");
            }
        } catch (IOException e) {
            System.out.println("Ошибка при выполнении HTTP-запроса: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Неизвестная ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
