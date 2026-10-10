
package com.example.urlshortner.dto;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidUrlValidator
        implements ConstraintValidator<ValidUrl, String> {
    @Override
    public boolean isValid(
            String value,
            ConstraintValidatorContext context) {

        if (value == null || value.isBlank()) {
            return true;
        }

        try {
            java.net.URI uri = new java.net.URI(value);
            String scheme = uri.getScheme();

            return scheme != null
                    && uri.getHost() != null
                    && ("http".equalsIgnoreCase(scheme)
                    || "https".equalsIgnoreCase(scheme));

        } catch (java.net.URISyntaxException e) {
            return false;
        }
    }

}
