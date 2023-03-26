package br.com.ada.albuns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class AlbunsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlbunsApplication.class, args);
    }

}
