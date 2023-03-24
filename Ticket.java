package cinema;

import java.util.UUID;

/**
 * Die Klasse Ticket bildet einen Sitz im Kinosaal inkl. eines Tokens zum Nachverfolgen des Ticketerwerbs ab.
 */
public class Ticket {

    private Seat seat; // Sitzplatz
    private UUID token; // Token

    /**
     * Konstruktor der Klasse Ticket
     * @param seat Sitzplatz
     * @param token Token
     */
    public Ticket(Seat seat, UUID token) {
        this.seat = seat;
        this.token = token;
    }

    /**
     * Gebe den Sitzplatz des Tickets zurück
     * @return Sitzplatz
     */
    public Seat getSeat() { return seat; }

    /**
     * Setze den Sitzplatz des Tickets
     * @param seat Sitzplatz
     */
    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    /**
     * Gebe den Token des Sitzplatzes zurück
     * @return Token des Sitzplatzes
     */
    public UUID getToken() {
        return token;
    }

    /**
     * Setze den Token des Sitzplatzes
     * @param token Token des Sitzplatzes
     */
    public void setToken(UUID token) {
        this.token = token;
    }
}