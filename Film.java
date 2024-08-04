import java.util.ArrayList;

public class Film {
    String Fid; 
    String tittel;
    float rating; 
    int antStemmer;
    ArrayList<Skuespiller> sListe;

    public Film(String f, String t, float r, int a){
        Fid = f;
        tittel = t;
        rating = r;
        antStemmer = a;
        sListe = new ArrayList<>();
    }

    public String toString(){
        return tittel + " , " + rating;
    }
}
