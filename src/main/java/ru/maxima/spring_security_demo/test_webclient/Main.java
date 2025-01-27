package ru.maxima.spring_security_demo.test_webclient;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.maxima.spring_security_demo.test_rest_template.model.User;

import java.net.http.HttpClient;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {


        String url = "https://reqres.in";

        User user = new User("Alex", "Tech artist");


        // Создаем экземпляр WebClient
        WebClient webClient = WebClient.create("https://reqres.in");

        // Отправляем GET-запрос и получаем ответ
        Mono<String> responseMono = webClient.get()
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class);

        // Подписываемся на результат и выводим его в консоль
        responseMono.subscribe(responseBody -> System.out.println(responseBody));

    }
}

record Post(Integer userId, Integer id, String title, String body) {}
