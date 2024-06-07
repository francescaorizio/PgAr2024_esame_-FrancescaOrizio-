import java.util.List;

public class Partita {
    private List<Giocatore> giocatori;
    private Mazzo mazzo;
    private Mazzo scarti;

    public Partita(List<Giocatore> giocatori, Mazzo mazzo, Mazzo scarti) {
        this.giocatori = giocatori;
        this.mazzo = mazzo;
        this.scarti = scarti;
    }

    // Metodi per gestire la partita...

    public void iniziaPartita() {
        Giocatore sceriffo = giocatori.get(0); // Lo Sceriffo è il primo giocatore
        System.out.println("Lo Sceriffo è: " + sceriffo.getNome());

        mescolaMazzo();
        distribuisciCarteIniziali();

        // Inizia il gioco...
    }

    private void mescolaMazzo() {
        mazzo.mescola();
    }

    private void distribuisciCarteIniziali() {
        for (Giocatore giocatore : giocatori) {
            giocatore.pescaCarta(mazzo.pesca());
            giocatore.pescaCarta(mazzo.pesca());
        }
    }
}
