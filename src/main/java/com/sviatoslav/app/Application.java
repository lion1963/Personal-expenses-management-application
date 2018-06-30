package com.sviatoslav.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StringUtils;

@SpringBootApplication
public class Application  {

    public static void main(String[] args) {
        String[] disabledCommands = {"--spring.shell.command.clear.enabled=false",
                                     "--spring.shell.command.quit.enabled=false"};
        String[] fullArgs = StringUtils.concatenateStringArrays(args, disabledCommands);
        SpringApplication.run(Application.class, fullArgs);
    }
}
