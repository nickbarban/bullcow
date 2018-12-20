package com.vaadinboot.bullcow;

import com.vaadinboot.bullcow.service.DictionaryResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BullcowApplication {

    public static void main(String[] args) {
        SpringApplication.run(BullcowApplication.class, args);
    }
}
