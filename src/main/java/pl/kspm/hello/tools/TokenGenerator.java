package pl.kspm.hello.tools;

import java.util.Random;

public class TokenGenerator {
    public String generateToken() {
        Random random = new Random();
        String token="";

        for (int i=0;i<6;i++) {
            token += random.nextInt(10);
        }

        return token;
    }
}
