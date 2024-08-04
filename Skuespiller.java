import java.util.ArrayList;


class Skuespiller{

    String Sid; 
    String navn;
    ArrayList<Film> filmer;
    

    public Skuespiller(String s, String n){
        Sid = s;
        navn = n;
        filmer = new ArrayList<>();
    }

    public String toString(){
        return navn;
    }

    public String id(){
        return Sid;
    }


}