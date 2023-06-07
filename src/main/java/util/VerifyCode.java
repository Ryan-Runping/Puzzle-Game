package util;

import java.util.Random;

public class VerifyCode {

    public VerifyCode() {
    }

    public String Code(){
        String code = "";
        String alpha = "abcdefghigklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            code = code + alpha.charAt(random.nextInt(alpha.length()));
        }
        int index = random.nextInt(5);
        char oldChar = code.charAt(index);
        char newChar = "0123456789".charAt(random.nextInt(0,10));
        code = code.replace(oldChar, newChar);
        return code;
    }
}
