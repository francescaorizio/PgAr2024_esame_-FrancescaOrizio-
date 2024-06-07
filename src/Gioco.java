import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


class Gioco {
    private List<Giocatore> giocatori;
    private Mazzo mazzo;
    private Mazzo scarti;
    private int turnoCorrente;

    public Gioco(List<String> nomiGiocatori) {
        this.giocatori = new ArrayList<>();
        this.mazzo = new Mazzo();
        this.scarti = new Mazzo();
        this.turnoCorrente = 0;

        inizializzaGiocatori(nomiGiocatori);
        mazzo.mescola();
        distribuisciCarteIniziali();
    }

    private void inizializzaGiocatori(List<String> nomiGiocatori) {
        List<String> ruoli = generaRuoli(nomiGiocatori.size());
        Collections.shuffle(ruoli);

        for (int i = 0; i < nomiGiocatori.size(); i++) {
            String ruolo = ruoli.get(i);
            int puntiFerita = ruolo.equals("Sceriffo") ? 5 : 4;
            Giocatore giocatore = new Giocatore(nomiGiocatori.get(i), ruolo, puntiFerita);
            giocatori.add(giocatore);
        }

        // Il primo giocatore è sempre lo Sceriffo
        Giocatore sceriffo = giocatori.stream().filter(g -> g.getRuolo().equals("Sceriffo")).findFirst().get();
        giocatori.remove(sceriffo);
        giocatori.add(0, sceriffo);
    }

    private List<String> generaRuoli(int numGiocatori) {
        List<String> ruoli = new ArrayList<>();
        ruoli.add("Sceriffo");
        ruoli.add("Rinnegato");

        if (numGiocatori >= 4) {
            ruoli.add("Fuorilegge");
            ruoli.add("Fuorilegge");
        }
        if (numGiocatori >= 5) {
            ruoli.add("Vice");
        }
        if (numGiocatori >= 6) {
            ruoli.add("Fuorilegge");
        }
        if (numGiocatori >= 7) {
            ruoli.add("Vice");
        }

        return ruoli;
    }

    private void distribuisciCarteIniziali() {
        for (Giocatore giocatore : giocatori) {
            for (int i = 0; i < giocatore.getPuntiFerita(); i++) {
                giocatore.pescaCarta(mazzo.pesca());
            }
        }
    }



    public void inizia() {
        while (!isPartitaConclusa()) {
            Giocatore giocatoreCorrente = giocatori.get(turnoCorrente);
            System.out.println("\nTurno di " + giocatoreCorrente.getNome());
            Turno turno = new Turno(giocatoreCorrente, giocatori, mazzo, scarti);
            turno.gestisciTurno();
            turnoCorrente = (turnoCorrente + 1) % giocatori.size();
        }
        annunciaVincitori();
    }

    private boolean isPartitaConclusa() {
        boolean sceriffoVivo = giocatori.stream().anyMatch(g -> g.getRuolo().equals("Sceriffo") && g.isVivo());
        boolean fuorileggeRinnegatoVivi = giocatori.stream().anyMatch(g -> (g.getRuolo().equals("Fuorilegge") || g.getRuolo().equals("Rinnegato")) && g.isVivo());

        return !sceriffoVivo || !fuorileggeRinnegatoVivi;
    }

    private void annunciaVincitori() {
        boolean sceriffoVivo = giocatori.stream().anyMatch(g -> g.getRuolo().equals("Sceriffo") && g.isVivo());
        boolean rinnegatoVivo = giocatori.stream().anyMatch(g -> g.getRuolo().equals("Rinnegato") && g.isVivo());

        if (!sceriffoVivo) {
            if (rinnegatoVivo) {
                System.out.println("Il Rinnegato vince!");
            } else {
                System.out.println("I Fuorilegge vincono!");
            }
        } else {
            System.out.println("Lo Sceriffo e i suoi Vice vincono!");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Scelta del numero di giocatori
        int numGiocatori;
        do {
            System.out.print("Inserisci il numero di giocatori (da 4 a 7): ");
            numGiocatori = scanner.nextInt();
            scanner.nextLine(); // Consuma il resto della riga
        } while (numGiocatori < 4 || numGiocatori > 7);

        // Creazione della lista dei nomi dei giocatori
        List<String> nomiGiocatori = new ArrayList<>();
        for (int i = 0; i < numGiocatori; i++) {
            System.out.print("Inserisci il nome del giocatore " + (i + 1) + ": ");
            String nomeGiocatore = scanner.nextLine();
            nomiGiocatori.add(nomeGiocatore);
        }

        // Creazione dell'istanza del gioco con i nomi dei giocatori scelti dall'utente
        Gioco gioco = new Gioco(nomiGiocatori);
        gioco.inizia();
    }
}