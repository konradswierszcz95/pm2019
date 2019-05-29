package pl.kspm.hello.tools;

public class DiactricalSignReplacer {
    private char[][] polishLetters;

    public DiactricalSignReplacer(){
        polishLetters = new char[18][2];
        fillTable();
    }


    public String replace (String word) {
        for (int i=0;i<=17;i++) {
            word=word.replace(polishLetters[i][0], polishLetters[i][1]);
        }
        return word;
    }

    public void fillTable() {
        polishLetters[0][0]='ą';
        polishLetters[1][0]='Ą';
        polishLetters[2][0]='ć';
        polishLetters[3][0]='Ć';
        polishLetters[4][0]='ę';
        polishLetters[5][0]='Ę';
        polishLetters[6][0]='ł';
        polishLetters[7][0]='Ł';
        polishLetters[8][0]='ń';
        polishLetters[9][0]='Ń';
        polishLetters[10][0]='ó';
        polishLetters[11][0]='Ó';
        polishLetters[12][0]='ś';
        polishLetters[13][0]='Ś';
        polishLetters[14][0]='ź';
        polishLetters[15][0]='Ź';
        polishLetters[16][0]='ż';
        polishLetters[17][0]='Ż';

        polishLetters[0][1]='a';
        polishLetters[1][1]='A';
        polishLetters[2][1]='c';
        polishLetters[3][1]='C';
        polishLetters[4][1]='e';
        polishLetters[5][1]='E';
        polishLetters[6][1]='l';
        polishLetters[7][1]='L';
        polishLetters[8][1]='n';
        polishLetters[9][1]='N';
        polishLetters[10][1]='o';
        polishLetters[11][1]='O';
        polishLetters[12][1]='s';
        polishLetters[13][1]='S';
        polishLetters[14][1]='z';
        polishLetters[15][1]='Z';
        polishLetters[16][1]='z';
        polishLetters[17][1]='Z';
    }
}
