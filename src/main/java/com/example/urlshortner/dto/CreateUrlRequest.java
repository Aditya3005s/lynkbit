package com.example.urlshortner.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.net.URI;

@Getter@Setter
public class CreateUrlRequest {
    @NotBlank(message="Original URL is required")
    @ValidUrl
    private String originalUrl;

    public boolean isValidUrl(){
        try {
            URI uri = new URI(originalUrl);

            return ("http".equalsIgnoreCase(uri.getScheme())
            ||"https".equalsIgnoreCase(uri.getScheme()))
            && uri.getHost() != null
            && uri.getHost().isBlank();
        }catch (Exception e){
            return false;
        }
    }
}
