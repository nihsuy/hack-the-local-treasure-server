package com.taikenfactory.htlt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.taikenfactory.htlt.task.RegisterDBTask;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
//        SpringApplication.run(Application.class, args);
        
    	ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
    	RegisterDBTask task = context.getBean(RegisterDBTask.class);
    	task.execute();
    }
}
