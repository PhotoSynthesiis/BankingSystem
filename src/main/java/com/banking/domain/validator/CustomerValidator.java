package com.banking.domain.validator;

import java.util.regex.Pattern;

public class CustomerValidator {
    public static boolean isNicknameValid(String nickname) {
        Pattern pattern = Pattern.compile("[\\da-z]+");

        return pattern.matcher(nickname).matches();
    }

    public static boolean isProperNameValid(String properName) {
        Pattern pattern = Pattern.compile("^[a-zA-Z\\s?]+$");

        return  pattern.matcher(properName).matches();
    }

    public static boolean isBalanceValid(double balance) {
        Pattern pattern = Pattern.compile("^(0|([1-9]\\d*))(\\.\\d+)?$");

        return pattern.matcher(String.valueOf(balance)).matches();
    }
}
