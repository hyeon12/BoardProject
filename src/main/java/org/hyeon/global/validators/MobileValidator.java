package org.hyeon.global.validators;

public interface MobileValidator {
    default boolean mobileCheck(String mobile){
        /***
         * 01[0/1/6]-[0000/000]-[0000]
         * 01[016]-\\d[3,4]-\\d[4]
         * 010.1111.1111 / 010 1111 1111 / 010-1111-1111 010111111111
         * 1. 숫자만 남긴다. 2. 패턴 만들기 3. 체크
         */
        mobile = mobile.replaceAll("\\D", "");
        String pattern = "01[016]\\d{3,4}\\d{4}$";

        return mobile.matches(pattern);
    }

}
