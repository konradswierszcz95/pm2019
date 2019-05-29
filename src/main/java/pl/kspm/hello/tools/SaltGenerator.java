package pl.kspm.hello.tools;

import java.util.Random;

public class SaltGenerator {

    //Znaki do generowania soli
    private String smallLetters = "qwertyuiopasdfghjklzxcvbnm";
    private String bigLetters = smallLetters.toUpperCase();
    private String numbers = "1234567890";
    private String specialSigns = "`~!@#$%^&*()_-+={[}]:;'|<,>.?/";
    private String salt="";

    public SaltGenerator() {
    }

    private void generateSalt() {
        Random random = new Random();
        int signNumber;
        for (int i=0;i<100;i++) {
            int x = random.nextInt(4);

            switch (x) {
                case 0:
                    signNumber = random.nextInt((smallLetters.length()-1));
                    salt += smallLetters.charAt(signNumber);
                    break;
                case 1:
                    signNumber = random.nextInt((bigLetters.length()-1));
                    salt += bigLetters.charAt(signNumber);
                    break;
                case 2:
                    signNumber = random.nextInt((numbers.length()-1));
                    salt += numbers.charAt(signNumber);
                    break;
                case 3:
                    signNumber = random.nextInt((specialSigns.length()-1));
                    salt += specialSigns.charAt(signNumber);
                    break;
            }
        }
    }

    public String getSalt() {
        salt="";
        generateSalt();
        return salt;
    }
}
