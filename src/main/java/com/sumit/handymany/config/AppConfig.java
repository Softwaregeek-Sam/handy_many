package com.sumit.handymany.config;

import com.sumit.handymany.user.dtos.UserDto;
import com.sumit.handymany.user.model.Role;
import com.sumit.handymany.user.model.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.stream.Collectors;

@Configuration
public class AppConfig {
    @Bean
    public RestTemplate restTemplate(){
         return new RestTemplate();
    }
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<User, UserDto>() {
            @Override
            protected void configure() {
                using(ctx -> {
                    User source = (User) ctx.getSource();
                    return source.getRoles()
                            .stream()
                            .map(Role::getName)
                            .collect(Collectors.toList());
                }).map(source, destination.getRoles());
            }
        });



        return modelMapper;
    }
}
