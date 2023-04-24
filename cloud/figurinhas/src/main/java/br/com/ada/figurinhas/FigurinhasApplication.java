package br.com.ada.figurinhas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.kafka.annotation.EnableKafka;

@EnableFeignClients
@SpringBootApplication
@EnableKafka
public class FigurinhasApplication {

    public static void main(String[] args) {
        SpringApplication.run(FigurinhasApplication.class, args);
    }

}
