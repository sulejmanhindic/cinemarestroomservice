package cinema;

import org.springframework.lang.Nullable;

/**
 * Klasse Purchase:
 * Diese Klasse stellt den Erwerb eines Sitzes (Spalte (column) und Reihe (row) dar.
 */
public class Purchase {
    @Nullable private int row;
    @Nullable private int column;

    /**
     * Konstruktor der Klasse Purchase
     */
    public Purchase() {

    }

    /**
     * Gebe die Reihe des zu erwerbenden Sitzes zurück
     * @return Reihe des zu erwerbenden Sitzes
     */
    public int getRow() {
        return row;
    }

    /**
     * Gebe die Spalte des zu erwerbenden Sitzes zurück
     * @return Spalte des zu erwerbenden Sitzes
     */
    public int getColumn() {
        return column;
    }

    /**
     * Setze die Reihe des zu erwerbenden Sitzes
     * @param row Reihe des zu erwerbenden Sitzes
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Setze die Spalte des zu erwerbenden Sitzes
     * @param column Spalte des zu erwerbenden Sitzes
     */
    public void setColumn(int column) {
        this.column = column;
    }
}