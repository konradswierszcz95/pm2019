package pl.kspm.hello.tools;

public class CreateEmail {
    public static String form(String name,String surname,String login, String token) {
         String content="Witaj "+name+" "+surname+
                "\nTa wiadomość potwierdza założenie Twojego konta w systemie firmy Praca Magisterska" +
                "\nDane do logowania:"+
                "\nlogin: "+login+
                "\nhasło: "+token+
                "\nHasło zostało wygenerowane automatycznie i wymagana będzie jego zmiana podczas pierwszego logowania."+
                "\n================================================================================="+
                "\nJeżeli ta wiadomość nie była adresowana do Ciebie, prosimy zgłoś to pod adresem: zgłoś@firma.pl";
         return content;
    }
}
