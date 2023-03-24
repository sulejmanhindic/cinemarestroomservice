package cinema;

/**
 * KLasse Sitz zum Darstellen eines Sitzes mit Reihe, Spalte, Preis und Verfügbarkeit
 */
public class Seat {
    private int row;
    private int column;
    private int price;
    private boolean available;

    /**
     * Konstruktor der Klasse Seat
     * @param row Sitzreihe
     * @param column Sitzspalte
     * @param price Preis des Sitzes
     * @param available Verfügbarkeit des Sitzes (true = verfügbar, false = belegt)
     */
    public Seat(int row, int column, int price, boolean available) {
        this.row = row;
        this.column = column;
        this.price = price;
        this.available = available;
    }

    /**
     * Setze die Reihennummer eines Sitzes
     * @param row Reihennummer eines Sitzes
     */
    public void setRow(int row) { this.row = row; }

    /**
     * Setze die Spaltennummer eines Sitzes
     * @param column Spaltennummer eines Sitzes
     */
    public void setColumn(int column) {
        this.column = column;
    }

    /**
     * Setze den Preis eines Sitzes
     * @param price Preis eines Sitzes
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Setze die Verfügbarkeit eines Sitzes
     * @param available Verfügbarkeit eines Sitzes
     */
    public void setAvailable(boolean available) {
        this.available = available;
    }

    /**
     * Gebe die Reihennummer eines Sitzes zurück
     * @return Reihennummer eines Sitzes
     */
    public int getRow() {
        return row;
    }

    /**
     * Gebe die Spaltennummer eines Sitzes zurück
     * @return Spaltennummer eines Sitzes
     */
    public int getColumn() {
        return column;
    }

    /**
     * Gebe den Preis eines Sitzes zurück
     * @return Preis eines Sitzes
     */
    public int getPrice() {
        return price;
    }

    /**
     * Gebe die Verfügbarkeit eines Sitzes zurück
     * @return Verfügbarkeit eines Sitzes
     */
    public boolean isAvailable() {
        return available;
    }




}
