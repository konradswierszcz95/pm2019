package pl.kspm.hello.tools;

public class FindExtension {

    public static String of(String filename) {
        String extension="";

        for(int i=filename.length()-1;i>=0;i--) {
            String character = String.valueOf(filename.charAt(i));
            if (character.equals(".")) {
                extension=character+extension;
                break;
            } else {
                extension=character+extension;
            }
        }
        return extension;
    }
}
