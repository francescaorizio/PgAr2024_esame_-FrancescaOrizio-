import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Mazzo {
    private List<Carta> carte;

    public void mescola() {
        Collections.shuffle(carte);
    }

    public List<Carta> getCarte() {
        return this.carte;
    }

    public Mazzo() {
        carte = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            carte.add(new Carta("BANG!", "Gioca e Scarta"));
        }
        for (int i = 0; i < 24; i++) {
            carte.add(new Carta("Mancato!", "Gioca e Scarta"));
        }
        carte.add(new Carta("Schofield", "Equipaggiabile"));
        carte.add(new Carta("Schofield", "Equipaggiabile"));
        carte.add(new Carta("Schofield", "Equipaggiabile"));
        carte.add(new Carta("Remington", "Equipaggiabile"));
        carte.add(new Carta("Rev. Carabine", "Equipaggiabile"));
        carte.add(new Carta("Winchester", "Equipaggiabile"));

    }

    /*public void mescola() {
        Collections.shuffle(carte);
    }
     */

    public void aggiungiCarta(Carta carta) {
        carte.add(carta);
    }

    public Carta pesca() {
        if (carte.isEmpty()) {
            System.out.println("Non ci sono carte nel mazzo!");
            return null;
        }
        return carte.remove(0); // Rimuovi e restituisci la prima carta nel mazzo
    }

    public boolean isEmpty() {
        return carte.isEmpty();
    }

    public int carteRimanenti() {
        return carte.size();
    }

}