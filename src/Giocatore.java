import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class Giocatore {
    private String nome;
    private String ruolo;
    private int puntiFerita;
    private List<Carta> mano;
    private List<Carta> equipaggiamento;

    public Giocatore(String nome, String ruolo, int puntiFerita) {
        this.nome = nome;
        this.ruolo = ruolo;
        this.puntiFerita = puntiFerita;
        this.mano = new ArrayList<>();
        this.equipaggiamento = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public String getRuolo() {
        return ruolo;
    }

    public int getPuntiFerita() {
        return puntiFerita;
    }

    public List<Carta> getMano() {
        return mano;
    }

    public List<Carta> getEquipaggiamento() {
        return equipaggiamento;
    }

    public void pescaCarta(Carta carta) {
        if (carta != null) {
            mano.add(carta);
        } else {
            System.out.println("Non ci sono carte nel mazzo!");
        }
    }

    public void giocaCarta(Carta carta) {
        mano.remove(carta);
    }

    public void perdePuntoFerita() {
        puntiFerita--;
    }

    public boolean isVivo() {
        return puntiFerita > 0;
    }

    public boolean haCarta(String nomeCarta) {
        for (Carta carta : mano) {
            if (carta.getNome().equals(nomeCarta)) {
                return true;
            }
        }
        return false;
    }

    public Carta getCarta(String nomeCarta) {
        for (Carta carta : mano) {
            if (carta.getNome().equals(nomeCarta)) {
                return carta;
            }
        }
        return null;
    }

    public void stampaInformazioni() {
        System.out.println(nome + " (" + ruolo + ") - PF: " + puntiFerita);
        System.out.println("Carte in mano: " + mano);
        System.out.println("Equipaggiamento: " + equipaggiamento);
    }

    public void stampaCarteInMano() {
        System.out.println("Carte in mano:");
        for (int i = 0; i < mano.size(); i++) {
            System.out.println((i + 1) + ". " + mano.get(i).getNome());
        }
    }

    @Override
    public String toString() {
        return nome + " (" + ruolo + ")";
    }

    private String provocazioneRicevuta;

    public void riceviProvocazione(String provocazione) {
        this.provocazioneRicevuta = provocazione;
        System.out.println("Hai ricevuto una provocazione crittografata! Decifrala al prossimo turno.");
    }

    public void decifraProvocazione(String chiave) {
        if (provocazioneRicevuta != null) {
            String provocazioneDecifrata = Taunt.encrypt(provocazioneRicevuta, chiave);
            System.out.println("Provocazione ricevuta: " + provocazioneDecifrata);
        } else {
            System.out.println("Nessuna provocazione ricevuta.");
        }
    }
}