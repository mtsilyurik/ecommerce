package ru.miaat.Ecommerce.Dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {
    private Long id;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String country;
    private UserDto user;
    private LocalDateTime createdAt;
}
