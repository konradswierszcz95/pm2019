package pl.kspm.hello.tools;

public class StringCheck {

    private String specialSigns = "!@#$%^&*()_-+={[]}:;|<,.>/?";

    public boolean containsSpecialSign(String text) {
        boolean contains = false;
        for (int i = 0; i < specialSigns.length(); i++) {
            if (text.contains("" + specialSigns.charAt(i))) {
                contains=true;
            }
        }
        return contains;
    }

    public boolean emailIsCorrect(String email) {
        if (!email.contains("@")) {
            return false;
        } else if (email.length()<5) {
            return false;
        } else if (!email.contains(".")) {
            return false;
        } else {
            return true;
        }
    }
}
