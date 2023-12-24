package com.oop.backend.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class EmailValidator {
    private static EmailValidator instance;

    private EmailValidator() {}

    public static EmailValidator getInstance() {
        if (instance == null) {
            instance = new EmailValidator();
        }
        return instance;
    }
    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    private static final Pattern pattern = Pattern.compile(EMAIL_REGEX);

    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }

        Matcher matcher = pattern.matcher(email);

        if (!matcher.matches()) {
            return false;
        }

        // Additional checks
        String[] parts = email.split("@");
        String username = parts[0];
        String domain = parts[1];

        // Check if domain has valid DNS records
        if (!isValidDomain(domain)) {
            return false;
        }

        // Check for specific username constraints
        if (username.length() > 64) {
            return false;
        }

        return true;
    }

    private static boolean isValidDomain(String domain) {
        try {
            InetAddress address = InetAddress.getByName(domain);
            return !address.getHostAddress().equals(domain);
        } catch (UnknownHostException e) {
            return false;
        }
    }
}
