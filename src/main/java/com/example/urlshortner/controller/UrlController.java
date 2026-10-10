package com.example.urlshortner.controller;

import com.example.urlshortner.dto.CreateUrlRequest;
import com.example.urlshortner.dto.CreateUrlResponse;
import com.example.urlshortner.entity.Url;
import com.example.urlshortner.service.UrlService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/urls")
public class UrlController {
    private final UrlService urlService;
    @Value("${app.base-url}")
    private String baseUrl;

    @PostMapping
    public ResponseEntity<CreateUrlResponse> createShortUrl(@Valid @RequestBody CreateUrlRequest request){
        Url url = urlService.createShortUrl(request.getOriginalUrl());


        String shortUrl = baseUrl.replaceAll("/+$", "")
                + "/" + url.getShortCode();

        CreateUrlResponse response=new CreateUrlResponse(url.getOriginalUrl(),url.getShortCode(),shortUrl);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode){
        Url url=urlService.getByShortCode(shortCode);

        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(url.getOriginalUrl())).build();
    }
}
