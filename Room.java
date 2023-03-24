package cinema;

/**
 * Klasse Room zum Abbilden eines Kinosaals mit Anzahl der Spalten und Reihen und der Sitze
 */
public class Room {
    private int total_rows; // Anzahl der Sitzreihen
    private int total_columns; // Anzahl der Sitzspalten
    private Seat[][] available_seats; // Alle Sitze

    /**
     * Erstelle einen Kinosaals mit <totalRows> Sitzreihen und <totalColumns> Sitzspalten
     * @param totalRows Anzahl der Sitzreihen
     * @param totalColumns Anzahl der Sitzspalten
     */
    public Room(int totalRows, int totalColumns) {
        this.total_rows = totalRows;
        this.total_columns = totalColumns;
        configure(totalRows, totalColumns);
    }

    public Room() {
    }

    /**
     * Gebe die Anzahl der Sitzreihen eines Kinosaals zurück
     * @return Anzahl der Sitzreihen
     */
    public int getTotalRows() {
        return total_rows;
    }

    /**
     * Setze die Anzahl der Sitzreihen eines Kinosaals
     * @param totalRows Sitzreihen eines Kinosaals
     */
    public void setTotalRows(int totalRows) {
        this.total_rows = totalRows;
    }

    /**
     * Gebe die Anzahl der Sitzspalten eines Kinosaals
     * @return Anzahl der Sitzreihen
     */
    public int getTotalColumns() {
        return total_columns;
    }

    /**
     * Setze die Anzahl der Sitzspalten eines Kinosaals
     * @param totalColumns Anzahl der Sitzspalten
     */
    public void setTotalColumns(int totalColumns) {
        this.total_columns = totalColumns;
    }

    /**
     * Gebe alle Sitze des Kinosaals zurück
     * @return Sitze des Kinosaals
     */
    public Seat[][] getAvailableSeats() {
        return available_seats;
    }

    /**
     * Setze die Sitze des Kinosaals
     * @param availableSeats Sitze des Kinosaals
     */
    public void setAvailableSeats(Seat[][] availableSeats) {
        this.available_seats = availableSeats;
    }

    /**
     * Erstelle einen Kinosaals mit <totalRows> Sitzreihen und <totalColumns> Sitzspalten, mache sie verfügbar.
     * Alle vorderen Reihe bis einschließlich Sitzreihe 4 kosten 10 Euro, die hinteren Kosten 8 Euro
     * @param row Anzahl der zu erzeugenden Sitzreihen
     * @param column Anzahl der zu erzeugenden Sitzspalten
     */
    public void configure(int row, int column) {
        available_seats = new Seat[row][column];
        for (int i = 1; i <= row; i++) {
            for (int j = 1; j <= column; j++) {
                if (i <= 4) {
                    available_seats[i-1][j-1] = new Seat(i, j, 10, true);
                } else {
                    available_seats[i-1][j-1] = new Seat(i, j, 8, true);
                }
            }
        }
    }
}
