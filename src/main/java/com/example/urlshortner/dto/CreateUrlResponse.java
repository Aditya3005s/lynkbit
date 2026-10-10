package com.example.urlshortner.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter@AllArgsConstructor
public class CreateUrlResponse {
    private String originalUrl;
    private String shortCode;
    private String shortUrl;
}
