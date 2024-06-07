import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Turno {
    private Giocatore giocatore;
    private List<Giocatore> giocatori;
    private Mazzo mazzo;
    private Mazzo scarti;
    private Scanner scanner;
    private boolean bangGiocato;

    public Turno(Giocatore giocatore, List<Giocatore> giocatori, Mazzo mazzo, Mazzo scarti) {
        this.giocatore = giocatore;
        this.giocatori = giocatori;
        this.mazzo = mazzo;
        this.scarti = scarti;
        this.scanner = new Scanner(System.in);
        this.bangGiocato = false;
    }

    public void gestisciTurno() {
        pescaDueCarte();
        giocatore.stampaInformazioni();
        boolean fineTurno = false;

        while (!fineTurno) {
            System.out.println("1. Gioca carta");
            System.out.println("2. Invia provocazione");
            System.out.println("3. Fine turno");

            int scelta = leggiScelta();
            switch (scelta) {
                case 1:
                    selezionaECartaDaGiocare();
                    break;
                case 2:
                    inviaProvocazione();
                    break;
                case 3:
                    fineTurno = true;
                    break;
                default:
                    System.out.println("Scelta non valida.");
            }
        }

        scartaCarteInEccesso();
        bangGiocato = false; // resetta il controllo per il prossimo turno
    }

    private int leggiScelta() {
        int scelta = 0;
        boolean inputValido = false;
        while (!inputValido) {
            try {
                System.out.print("Scelta: ");
                String input = scanner.nextLine();
                scelta = Integer.parseInt(input);
                inputValido = true;
            } catch (NumberFormatException e) {
                System.out.println("Inserisci un numero valido.");
            }
        }
        return scelta;
    }

    private void inviaProvocazione() {
        System.out.println("Scegli il giocatore a cui inviare la provocazione:");
        for (int i = 0; i < giocatori.size(); i++) {
            System.out.println((i + 1) + ". " + giocatori.get(i).getNome());
        }
        int sceltaGiocatore = scanner.nextInt();
        scanner.nextLine(); // Consuma il newline

        if (sceltaGiocatore >= 1 && sceltaGiocatore <= giocatori.size()) {
            Giocatore giocatoreDestinatario = giocatori.get(sceltaGiocatore - 1);

            System.out.println("Inserisci il testo della provocazione:");
            String testoProvocazione = scanner.nextLine();
            System.out.println("Inserisci la chiave di cifratura (più parole separate da spazio):");
            String chiaveCifratura = scanner.nextLine();

            String provocazioneCifrata = Taunt.encrypt(testoProvocazione.toLowerCase(), chiaveCifratura.toLowerCase());

            giocatoreDestinatario.riceviProvocazione(provocazioneCifrata);
            System.out.println("Provocazione inviata con successo a " + giocatoreDestinatario.getNome() + "!");
        } else {
            System.out.println("Scelta non valida.");
        }
    }


    private void pescaDueCarte() {
        giocatore.pescaCarta(mazzo.pesca());
        giocatore.pescaCarta(mazzo.pesca());
        System.out.println(giocatore.getNome() + " ha pescato due carte.");
    }

    private void selezionaECartaDaGiocare() {
        System.out.println("Carte in mano:");
        for (int i = 0; i < giocatore.getMano().size(); i++) {
            System.out.println((i + 1) + ". " + giocatore.getMano().get(i).getNome());
        }

        System.out.print("Seleziona una carta da giocare: ");
        int indiceCarta = scanner.nextInt() - 1;
        scanner.nextLine();

        if (indiceCarta >= 0 && indiceCarta < giocatore.getMano().size()) {
            Carta cartaDaGiocare = giocatore.getMano().get(indiceCarta);

            if (cartaDaGiocare.getNome().equals("BANG!") && bangGiocato) {
                System.out.println("Hai già giocato una carta BANG! in questo turno.");
            } else {
                giocaCarta(cartaDaGiocare);
                if (cartaDaGiocare.getNome().equals("BANG!")) {
                    bangGiocato = true;
                }
            }
        } else {
            System.out.println("Indice non valido.");
        }
    }

    private void giocaCarta(Carta carta) {
        giocatore.giocaCarta(carta);
        scarti.aggiungiCarta(carta);
        System.out.println("Hai giocato: " + carta.getNome());

        if (carta.getNome().equals("BANG!")) {
            System.out.println("Seleziona il giocatore da attaccare:");
            List<Giocatore> bersagliValidi = getBersagliValidi();
            for (int i = 0; i < bersagliValidi.size(); i++) {
                Giocatore target = bersagliValidi.get(i);
                System.out.println((i + 1) + ". " + target.getNome());
            }
            int indiceBersaglio = scanner.nextInt() - 1;
            scanner.nextLine();

            if (indiceBersaglio >= 0 && indiceBersaglio < bersagliValidi.size()) {
                Giocatore bersaglio = bersagliValidi.get(indiceBersaglio);
                bersaglioAttaccato(bersaglio);
            } else {
                System.out.println("Indice bersaglio non valido.");
            }
        }
    }

    private List<Giocatore> getBersagliValidi() {
        List<Giocatore> bersagliValidi = new ArrayList<>();
        int indiceGiocatore = giocatori.indexOf(giocatore);
        int indiceSinistra = (indiceGiocatore - 1 + giocatori.size()) % giocatori.size();
        int indiceDestra = (indiceGiocatore + 1) % giocatori.size();
        if (giocatori.get(indiceSinistra).isVivo()) {
            bersagliValidi.add(giocatori.get(indiceSinistra));
        }
        if (giocatori.get(indiceDestra).isVivo()) {
            bersagliValidi.add(giocatori.get(indiceDestra));
        }
        return bersagliValidi;
    }

    private void bersaglioAttaccato(Giocatore bersaglio) {
        System.out.println(bersaglio.getNome() + " è stato colpito!");

        if (bersaglio.haCarta("Mancato!")) {
            bersaglio.stampaCarteInMano();
            System.out.println("Vuoi usare una carta Mancato? (si/no)");
            String risposta = scanner.nextLine();
            if (risposta.equalsIgnoreCase("si")) {
                Carta cartaMancato = bersaglio.getCarta("Mancato!");
                bersaglio.giocaCarta(cartaMancato);
                        scarti.aggiungiCarta(cartaMancato);
                System.out.println(bersaglio.getNome() + " ha giocato una carta Mancato! e ha evitato il colpo.");
                return;
            }
        }
        bersaglio.perdePuntoFerita();
        System.out.println(bersaglio.getNome() + " ha perso un punto ferita. PF rimanenti: " + bersaglio.getPuntiFerita());
        if (bersaglio.getPuntiFerita() == 0) {
            giocatori.remove(bersaglio);
            System.out.println(bersaglio.getNome() + " è stato eliminato dal gioco!");
        }
    }

    private void scartaCarteInEccesso() {
        while (giocatore.getMano().size() > giocatore.getPuntiFerita()) {
            giocatore.stampaCarteInMano();
            System.out.println("Devi scartare una carta. Seleziona una carta da scartare:");
            int indiceCarta = scanner.nextInt() - 1;
            scanner.nextLine();

            if (indiceCarta >= 0 && indiceCarta < giocatore.getMano().size()) {
                Carta cartaDaScartare = giocatore.getMano().get(indiceCarta);
                giocatore.giocaCarta(cartaDaScartare);
                scarti.aggiungiCarta(cartaDaScartare);
                System.out.println("Hai scartato: " + cartaDaScartare.getNome());
            } else {
                System.out.println("Indice non valido.");
            }
        }
    }
}

