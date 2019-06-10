package pl.kspm.hello.tools;

public class StringCheck {

    private static String specialSigns = "!@#$%^&*()_-+={[]}:;|<,.>/?";

    public static boolean containsSpecialSign(String text) {
        boolean contains = false;
        for (int i = 0; i < specialSigns.length(); i++) {
            if (text.contains("" + specialSigns.charAt(i))) {
                contains=true;
            }
        }
        return contains;
    }

    public static boolean emailIsCorrect(String email) {
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
