import java.io.*;
import java.util.*;


class Graph {

    HashMap<Skuespiller,ArrayList<Pair<Skuespiller,Film>>> hash = new HashMap<>();
    
    HashSet<Skuespiller> harbesokt = new HashSet<>(); 
    
    HashMap<Skuespiller, Skuespiller> forrige;
    
    
    int antallKanter = 0;

    public int antKant(){
        return antallKanter;
    }
    

    HashMap<String,Skuespiller> skuespillerHash = new HashMap<>();

    public void lesSkuespiller(String fil) throws FileNotFoundException{
        
        try (Scanner sc = new Scanner(new File(fil), "utf8")) {
            while(sc.hasNextLine()){

                String[] liste = sc.nextLine().split("\t");
                Skuespiller nySkuespiller = new Skuespiller(liste[0],liste[1]);
                ArrayList<Film> filmerMedSkuespiller = new ArrayList<>();
                int teller = 1;

                while(teller<liste.length){
                    if(filmHash.containsKey(liste[teller])){                               
                        
                        filmerMedSkuespiller.add(filmHash.get(liste[teller]));
                        
                        Film leggTil = filmHash.get(liste[teller]);
                        leggTil.sListe.add(nySkuespiller);
                    }
                    teller++;
                }
                skuespillerHash.put(liste[0], nySkuespiller);
                nySkuespiller.filmer = filmerMedSkuespiller;
            }
        }
    }


    
    HashMap<String,Film> filmHash = new HashMap<>();

    public void lesFilm(String f) throws FileNotFoundException{
        
        try (Scanner sc = new Scanner(new File(f), "utf8")) {
            while(sc.hasNextLine()){
                String[] liste = sc.nextLine().split("\t");
            
                Film film = new Film(liste[0], liste[1], Float.parseFloat(liste[2]), Integer.parseInt(liste[3]));
                filmHash.put(liste[0], film);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }    
    }




    class Pair<a,b> {                   
        public a skuespiller;
        public b film;
    
        public Pair(a skuespiller, b film){
            this.skuespiller = skuespiller;
            this.film = film;
        }
    }


    public void bygge(){
        
        for(String s : skuespillerHash.keySet()){

            Skuespiller ja = skuespillerHash.get(s);
            ArrayList<Pair<Skuespiller,Film>> kanter = new ArrayList<>();
            
            for(Film f : ja.filmer){                         
                for(Skuespiller sp : f.sListe){              
                    if(!sp.equals(ja)){   
                        kanter.add(new Pair<Skuespiller,Film>(sp, f)); 
                    }
                }
            }
            hash.put(ja, kanter);
        }
        for(ArrayList<Pair<Skuespiller,Film>> p : hash.values()){
            antallKanter += p.size();
        }
    }


    public ArrayList<Skuespiller>  BFSVisit(Graph graf, Skuespiller s, Skuespiller maal){
        
        HashMap<Skuespiller, Skuespiller> forelder = new HashMap<>(); 
        LinkedList<Skuespiller> lenket = new LinkedList<>(); lenket.add(s);
        ArrayList<Skuespiller> resultat = new ArrayList<>();

        while(!lenket.isEmpty()){
            Skuespiller node = lenket.getFirst(); lenket.removeFirst();

            for(Pair<Skuespiller,Film> pa : hash.get(node)){
                Skuespiller p = pa.skuespiller;

                if(p.equals(maal)){forelder.put(p, node); 
                    resultat.add(p);
                    while(!s.equals(p)){
                    resultat.add(forelder.get(p));
                    p=forelder.get(p);
                    }

                    return resultat;
                }

                if(!forelder.keySet().contains(p)){
                    forelder.put(p, node);
                    lenket.add(p);
                }
            }
        }
        return resultat;
    } 



    public void DFSFull(){
        
        HashMap<Integer,Integer> komponenter = new HashMap<>();  
        int teller;
        for(Skuespiller s : hash.keySet()){  
            if(!harbesokt.contains(s)){ 
                teller = 0;                
                teller = DFSVisit(s, teller);
                if(!komponenter.containsKey(teller)){  
                    komponenter.put(teller, 1); 
                }
                else{
                    komponenter.put(teller, komponenter.get(teller)+1);  
                }
                
            }
        }
        for(Integer i : komponenter.keySet()){
            System.out.println("Det er " + komponenter.get(i) + " komponenter av str " + i);
        }
        
    }

    public int DFSVisit(Skuespiller start, int teller){
        Stack<Skuespiller> s = new Stack<>();  
        s.add(start);                          

        while(!s.isEmpty()){                     
            Skuespiller denne = s.pop();
            if(!harbesokt.contains(denne)){              
                harbesokt.add(denne);                   
                for(Pair<Skuespiller,Film> Pair : hash.get(denne)){
                    s.push(Pair.skuespiller);                
                }
                teller++;                           
            }
        }
        
        return teller;
    }
}
