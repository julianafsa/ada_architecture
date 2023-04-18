package br.com.ada.albuns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.kafka.annotation.EnableKafka;

@EnableFeignClients
@SpringBootApplication
@EnableKafka
public class AlbunsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlbunsApplication.class, args);
    }

}
