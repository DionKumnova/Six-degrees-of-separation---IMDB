import java.io.*;
import java.util.ArrayList;

class Innleveringsoppgave3 {
    
    public static void main(String[] args) throws FileNotFoundException {
        long start = System.currentTimeMillis();
        Graph graf = new Graph();
        graf.lesFilm("movies.tsv");
        graf.lesSkuespiller("actors.tsv");
        graf.bygge();

        //Oppgave 1
        System.out.println("Oppgave 1 \n");
        System.out.println("Kanter:" + graf.antKant()/2);
        System.out.println("Noder: " + graf.hash.size() +"\n \n");
        
        //Oppgave 2
        System.out.println("Oppgave 2 \n"); 
        
        ArrayList<Skuespiller> rute = graf.BFSVisit(graf, graf.skuespillerHash.get("nm0000243"), graf.skuespillerHash.get("nm0424060")); 
        
        boolean breakMark = false;

        for(int i = 0; i<rute.size()-1; i++){
            for(Graph.Pair<Skuespiller, Film> p : graf.hash.get(rute.get(i))){

                if(breakMark == true){breakMark= false; break;}

                for(Graph.Pair<Skuespiller, Film> p2 : graf.hash.get(rute.get(i+1))){

                    if(p.film.equals(p2.film)){

                        System.out.println(rute.get(i) +"="+ p.film + " ----> "+ rute.get(i+1));
                        breakMark = true; break; 
                    
                    }
                }
            }
        }
      
        System.out.println("\n \n"); 

        //Oppgave 3
        System.out.println("Oppgave 3: \n");               

        graf.DFSFull();

        long slutt = System.currentTimeMillis();
        System.out.println("Totaltid:  " + (slutt-start) + "ms");
    }
}
