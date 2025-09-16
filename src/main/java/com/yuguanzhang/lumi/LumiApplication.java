package com.yuguanzhang.lumi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class LumiApplication {

    public static void main(String[] args) {
        SpringApplication.run(LumiApplication.class, args);
    }

}
