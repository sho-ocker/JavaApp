package hr.java.covidportal.main;

import hr.java.covidportal.enums.Simptomi;
import hr.java.covidportal.genericsi.KlinikaZaInfektivneBolesti;
import hr.java.covidportal.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Služi za izvođenje programa koji kroz unos podataka o županijama, simptomima, bolestima i osobama vrši određene
 * funkcionalnosti, te na kraju ispisuje sve na ekran.
 */

public class Glavna {
    private static final Logger logger = LoggerFactory.getLogger(Glavna.class);
    public static final String SERIALIZATION_FILE_NAME = "dat/zupanije.dat";

    protected static List<Zupanija> zupanijeIzDat = new ArrayList<>();
    protected static List<Simptom> simptomiIzDat = new ArrayList<>();
    protected static List<Bolest> bolestiIzDat = new ArrayList<>();
    protected static List<Virus> virusiIzDat = new ArrayList<>();
    protected static List<Osoba> osobeIzDat = new ArrayList<>();

    protected static File zupanijeDat = new File("dat/zupanije.txt");
    protected static File simptomiDat = new File("dat/simptomi.txt");
    protected static File bolestiDat = new File("dat/bolesti.txt");
    protected static File virusiDat = new File("dat/virusi.txt");
    protected static File osobeDat = new File("dat/osobe.txt");

    /**
     * Služi za pokretanje programa koji će od korisnika zatražiti određene unose, te uz pomoć njih napraviti ispis
     * podataka na ekran.
     *
     * @param args -> argumenti komandne linije koji se ovdje ne koriste
     */

    public static final void main(String[] args){

        Scanner unos = new Scanner(System.in);
        Map<Bolest, List<Osoba>> mapaPoBolesti = new HashMap<>();

        logger.info("Pokrenuta aplikacija");

        unosZupanija(zupanijeIzDat, zupanijeDat);
        unosSimptoma(simptomiIzDat, simptomiDat);
        unosBolesti(bolestiIzDat, bolestiDat, simptomiIzDat);
        unosVirusa(virusiIzDat, virusiDat, simptomiIzDat);
        unosOsoba(osobeIzDat, osobeDat, zupanijeIzDat, bolestiIzDat, virusiIzDat);

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        for(Osoba oso : osobeIzDat) {
            if (mapaPoBolesti.containsKey(oso.getZarazenBolescu())){
                mapaPoBolesti.get(oso.getZarazenBolescu()).add(oso);
            }else{
                mapaPoBolesti.put(oso.getZarazenBolescu(), new ArrayList<>());
                mapaPoBolesti.get(oso.getZarazenBolescu()).add(oso);
            }
        }

        ispisOsoba(osobeIzDat);

        for(Bolest bol : mapaPoBolesti.keySet()){
            if(bol instanceof Virus) {
                System.out.print("\nOd virusa " + bol.getNaziv() + " boluju: | ");
                mapaPoBolesti.get(bol).stream()
                        .forEach(oso -> System.out.print(oso.getIme() + " " + oso.getPrezime() + " | "));
            }
        }

        zupanijeIzDat.stream()
                .sorted(Comparator.comparing(Zupanija::getPostotakZarazenosti).reversed())
                .findFirst()
                .map(zup -> System.out.printf("%nNajviše zaraženih ima u županiji %s: %.2f%% %n",
                        zup.getNaziv(), zup.getPostotakZarazenosti()));


        List<Virus> listaVirusa = virusiIzDat.stream()
                .filter(vir -> vir instanceof Virus)
                .map(vir -> (Virus)vir)
                .collect(Collectors.toList());

        List<Osoba> listaOsobaSaVirusom = osobeIzDat.stream()
                .filter(osoba -> osoba.getZarazenBolescu() instanceof Virus)
                .collect(Collectors.toList());

        KlinikaZaInfektivneBolesti<Virus, Osoba> klinika = new KlinikaZaInfektivneBolesti<>(listaVirusa, listaOsobaSaVirusom);

        System.out.println("\nVirusi sortirani po nazivu suprotno od poretka abecede(SA LAMBDOM): ");
        long startTime = System.nanoTime();
        klinika.SortVirus();
        long stopTime = System.nanoTime();
        long vrijemeL = (stopTime - startTime)/(int)(Math.pow(10,6));


        System.out.println("\nVirusi sortirani po nazivu suprotno od poretka abecede(BEZ LAMBDE): ");
        startTime = System.nanoTime();


        klinika.SortVirusBezLambde();
        stopTime = System.nanoTime();
        long vrijemeB = (stopTime - startTime)/(int)(Math.pow(10,6));


        System.out.println("\nSortiranje objekata korištenjem lambdi traje " + vrijemeL + " milisekundi, " +
                "a bez lambda traje " + vrijemeB +" milisekundi. \n");

        System.out.println("Unesite string za pretragu po prezimenu: ");
        String unosFiltera = unos.nextLine();
        System.out.println("Osobe čije prezime sadrži '" + unosFiltera + "' su sljedeće: ");
        List<Osoba> OsobeFiltriranePoPrezimenu = Optional.ofNullable(osobeIzDat)
                .stream()
                .flatMap(List::stream)
                .filter(oso -> oso.getPrezime().contains(unosFiltera))
                .collect(Collectors.toList());


        if (OsobeFiltriranePoPrezimenu.isEmpty()) {
            System.out.println("\nNe postoji osoba sa traženim filterom!");
        }else{
            OsobeFiltriranePoPrezimenu.stream()
                    .forEach(oso -> System.out.println(oso.getIme() + " " + oso.getPrezime()));
        }

        bolestiIzDat.stream()
                .map(bolest -> "\n" + bolest.getNaziv() + " ima " + bolest.getSimptomi().size() + " simptom/a" )
                .forEach(System.out::print);

        virusiIzDat.stream()
                .map(bolest -> "\n" + bolest.getNaziv() + " ima " + bolest.getSimptomi().size() + " simptom/a" )
                .forEach(System.out::print);



        List<Zupanija> odabraneZupanije = new ArrayList<>();
        for(Zupanija zup : zupanijeIzDat)
            if(zup.getPostotakZarazenosti() > 2)
                odabraneZupanije.add(zup);


        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SERIALIZATION_FILE_NAME))) {
            out.writeObject(odabraneZupanije);
        } catch (IOException ex) {
            System.err.println(ex);
        }

        System.out.println("\n\nDeserijalizacija(provjera): ");
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("dat/zupanije.dat"));
            List<Zupanija> deser = (List<Zupanija>) in.readObject();
            System.out.println();

            deser.stream()
                    .sorted(Comparator.comparing(Zupanija::getNaziv))
                    .map(zup -> zup.getNaziv() + " ima " + zup.getPostotakZarazenosti() + "% zaraženih")
                    .forEach(System.out::println);

            in.close();
        } catch (IOException ex) {
            System.err.println(ex);
        } catch (ClassNotFoundException ex) {
            System.err.println(ex);
        }

        logger.info("Završeno izvođenje aplikacije");
    }

    ///////////////////////////////////////////////ISPIS OSOBA//////////////////////////////////////////////////////////
    private static void ispisOsoba(List<Osoba> osobe) {
        System.out.println("\nPopis osoba: \n");
        for(int i=0; i<osobe.size(); i++){
            System.out.println("Ime i prezime: " + osobe.get(i).getIme() + " " + osobe.get(i).getPrezime());
            System.out.println("Starost: " + osobe.get(i).getStarost());
            System.out.println("Županija prebivališta: " + osobe.get(i).getZupanija().getNaziv());
            System.out.println("Zaražen bolešću: " + osobe.get(i).getZarazenBolescu().getNaziv());
            System.out.println("Kontaktirane osobe:");
            if(osobe.get(i).getKontaktiraneOsobe() == null)
                System.out.println("Nema kontaktiranih osoba");
            else if(osobe.get(i).getKontaktiraneOsobe() != null){
                for(Osoba oso : osobe.get(i).getKontaktiraneOsobe())
                    System.out.println(oso.getIme() + " " + oso.getPrezime());
            }
            System.out.print("\n");
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void unosZupanija(List<Zupanija> zupanijeIzDat, File zupanijeDat){
        System.out.println("Učitavanje podataka o županijama...");
        try (BufferedReader reader = new BufferedReader(new FileReader(zupanijeDat))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Long id = Long.parseLong(line);
                String nazivZupanije = reader.readLine();
                line = reader.readLine();
                Integer brojStanovnika = Integer.parseInt(line);
                line = reader.readLine();
                Integer brojZarazenih = Integer.parseInt(line);
                zupanijeIzDat.add(new Zupanija(nazivZupanije, brojStanovnika, brojZarazenih, id));
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void unosSimptoma(List<Simptom> simptomiIzDat, File simptomiDat) {
        System.out.println("Učitavanje podataka o simptomima...");
        try (BufferedReader reader = new BufferedReader(new FileReader(simptomiDat))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Long id = Long.parseLong(line);
                String nazivSimptoma = reader.readLine();
                String vrijednostSimptoma = reader.readLine();
                String enumm = Simptomi.valueOf(vrijednostSimptoma).getVrijednost();
                simptomiIzDat.add(new Simptom(nazivSimptoma, enumm, id));
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void unosBolesti(List<Bolest> bolestiIzDat, File bolestiDat, List<Simptom> simptomiIzDat){
        System.out.println("Učitavanje podataka o bolestima...");
        try (BufferedReader reader = new BufferedReader(new FileReader(bolestiDat))) {
            String line;
            while ((line = reader.readLine()) != null) {
                List<Simptom> listaSimptoma = new ArrayList<>();
                Long id = Long.parseLong(line);
                String nazivBolesti = reader.readLine();
                String[] simptomID = reader.readLine().split(",");

                for(Simptom simp : simptomiIzDat) {
                    for(String stringSimp : simptomID)
                        if (simp.getId().toString().equals(stringSimp)){
                            listaSimptoma.add(simp);
                        }
                }
                bolestiIzDat.add(new Bolest(nazivBolesti, listaSimptoma, id));
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void unosVirusa(List<Virus> virusiIzDat, File virusiDat, List<Simptom> simptomiIzDat){
        System.out.println("Učitavanje podataka o virusima...");
        try (BufferedReader reader = new BufferedReader(new FileReader(virusiDat))) {
            String line;
            while ((line = reader.readLine()) != null) {
                List<Simptom> listaSimptoma = new ArrayList<>();
                Long id = Long.parseLong(line);
                String nazivVirusa = reader.readLine();
                String[] simptomID = reader.readLine().split(",");

                for(Simptom simp : simptomiIzDat)
                    for(String stringSimp : simptomID)
                        if (simp.getId().toString().equals(stringSimp))
                            listaSimptoma.add(simp);


                virusiIzDat.add(new Virus(nazivVirusa, listaSimptoma, id));
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void unosOsoba(List<Osoba> osobeIzDat, File osobeDat, List<Zupanija> zupanijeIzDat,
                                 List<Bolest> bolestiIzDat, List<Virus> virusiIzDat){
        System.out.println("Učitavanje osoba...");
        try (BufferedReader reader = new BufferedReader(new FileReader(osobeDat))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Long id = Long.parseLong(line);
                String ime = reader.readLine();
                String prezime = reader.readLine();
                line = reader.readLine();
                Integer starost = Integer.parseInt(line);
                line = reader.readLine();
                Long pomZupanija = Long.parseLong(line);
                String[] bolestIime = reader.readLine().split(",");
                String[] odabraneOsobe = reader.readLine().split(",");


                Zupanija odabranaZupanija = null;
                for(Zupanija zup : zupanijeIzDat)
                    if(zup.getId() == pomZupanija) {
                        odabranaZupanija = zup;
                        break;
                    }

                Bolest odabranaBolest = null;
                if(bolestIime[0].equals("virus")) {
                    for (Virus vir : virusiIzDat)
                        if (vir.getId().toString().equals(bolestIime[1])) {
                            odabranaBolest = vir;
                            break;
                        }
                }else{
                    for (Bolest bol : bolestiIzDat)
                        if (bol.getId().toString().equals(bolestIime[1])) {
                            odabranaBolest = bol;
                            break;
                        }
                }

                List<Osoba> listaOsoba = new ArrayList<>();
                for(Osoba oso : osobeIzDat)
                    for(String strOso : odabraneOsobe)
                        if(oso.getId().toString().equals(strOso))
                            listaOsoba.add(oso);

                osobeIzDat.add(new Osoba.Builder(ime,prezime)
                        .setStarost(starost)
                        .setZupanija(odabranaZupanija)
                        .setZarazenBolescu(odabranaBolest)
                        .setKontaktiraneOsobe(listaOsoba)
                        .setId(id)
                        .build()
                );

            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

}