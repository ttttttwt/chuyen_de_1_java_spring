package com.example.chuyen_de_1.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class Swagger {
    @Bean
    public OpenAPI myOpenAPI() {


        Contact contact = new Contact();
        contact.setEmail("quocla.21it@vku.udn.vn");
        contact.setName("twtwtwt");
        contact.setUrl("https://github.com/ttttttwt");

        Info info = new Info()
            .title("REST API Documentation")
                .version("1.0.0")
                .contact(contact)
                .description("This is a REST API of QLN Forum");

        return new OpenAPI().info(info);
    }
}