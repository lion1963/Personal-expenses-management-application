package com.sviatoslav.app;

import com.sviatoslav.app.services.CurrencyRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.util.StringUtils;

@SpringBootApplication
public class Application {

    @Autowired
    CurrencyRateService currencyRateService;

    public static void main(String[] args) {
        String[] disabledCommands = {"--spring.shell.command.clear.enabled=false",
                                     "--spring.shell.command.quit.enabled=false"};
        String[] fullArgs = StringUtils.concatenateStringArrays(args, disabledCommands);
        SpringApplication.run(Application.class, fullArgs);
    }

}
