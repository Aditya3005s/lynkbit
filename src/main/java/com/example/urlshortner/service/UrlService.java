package com.example.urlshortner.service;

import com.example.urlshortner.entity.Url;
import com.example.urlshortner.repository.UrlRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.SecureRandom;

@Service
@AllArgsConstructor
public class UrlService {
    private UrlRepository urlRepository;
    private static final String CHARACTERS="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private static final SecureRandom RANDOM = new SecureRandom();

    public Url createShortUrl(String originalUrl){
        String shortCode;
        do {
            shortCode=generateShortCode();
        }while (urlRepository.existsByShortCode(shortCode));

        Url url = new Url();
        url.setOriginalUrl(originalUrl);
        url.setShortCode(shortCode);
        return urlRepository.save(url);
    }

    public Url getByShortCode(String shortCode){
        return urlRepository.findByShortCode(shortCode).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Short Url Not Found"));
    }


    private String generateShortCode(){
        StringBuilder code=new StringBuilder();

        for(int i=0;i<6;i++){
            int index=RANDOM.nextInt(CHARACTERS.length());
            code.append(CHARACTERS.charAt(index));
        }
        return code.toString();
    }

}
