class Carta {
    private String nome;
    private String descrizione;

    public Carta(String nome, String descrizione) {
        this.nome = nome;
        this.descrizione = descrizione;
    }

    public String getNome() {
        return nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    @Override
    public String toString() {
        return nome; // Restituisce solo il nome della carta quando viene chiamato il metodo toString()
    }
}

class CartaArma extends Carta {
    private int distanza;

    public CartaArma(String nome, String descrizione, int distanza) {
        super(nome, descrizione);
        this.distanza = distanza;
    }

    public int getDistanza() {
        return distanza;
    }
}

class CartaBang extends Carta {
    public CartaBang() {
        super("BANG!", "Attacco con proiettile");
    }
}

class CartaMancato extends Carta {
    public CartaMancato() {
        super("Mancato!", "Gioca e Scarta");
    }
}
