package com.contactbook.util;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

/** Server-side validation helpers (never trust the browser alone). */
public final class Validator {

    private static final Pattern PHONE_CHARS = Pattern.compile("^[0-9+\\-() ]+$");
    private static final Pattern EMAIL =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private Validator() {
    }

    /** Returns the text trimmed, or "" when it is null. */
    public static String clean(String value) {
        return value == null ? "" : value.trim();
    }

    /** Returns a map of field name -> error message. Empty map means everything is valid. */
    public static Map<String, String> validate(String name, String phone, String email) {
        Map<String, String> errors = new LinkedHashMap<>();

        if (name.isEmpty()) {
            errors.put("name", "Name is required.");
        } else if (name.length() > 100) {
            errors.put("name", "Name must be 100 characters or fewer.");
        }

        if (phone.isEmpty()) {
            errors.put("phone", "Phone number is required.");
        } else if (phone.length() > 15) {
            errors.put("phone", "Phone number must be 15 characters or fewer.");
        } else if (!PHONE_CHARS.matcher(phone).matches() || countDigits(phone) < 7) {
            errors.put("phone", "Enter a valid phone number (at least 7 digits; digits, +, -, ( ) and spaces only).");
        }

        if (!email.isEmpty()) {
            if (email.length() > 100) {
                errors.put("email", "Email must be 100 characters or fewer.");
            } else if (!EMAIL.matcher(email).matches()) {
                errors.put("email", "Enter a valid email address, for example name@example.com.");
            }
        }

        return errors;
    }

    /** Parses a positive contact id. Returns null when the text is missing or not a valid id. */
    public static Integer parseId(String text) {
        if (text == null) {
            return null;
        }
        try {
            int id = Integer.parseInt(text.trim());
            return id > 0 ? id : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static int countDigits(String text) {
        int count = 0;
        for (int i = 0; i < text.length(); i++) {
            if (Character.isDigit(text.charAt(i))) {
                count++;
            }
        }
        return count;
    }
}
