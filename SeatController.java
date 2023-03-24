package cinema;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 *  Die Klasse SeatController bedient die Endpoints
 *  a) /seats zum Anzeigen verfügbarer Sitzplätze
 *  b) /purchase zum Erwerb eines Tickets
 *  c) /return zur Rückgabe eines Tickets
 *  d) /stats zum Anzeigen einer Statistik
 *  Die Nachrichten zu den Endpoints werden im JSON-Format zurückgeliefert und
 *  dem Anwender im Web-Browser angezeigt.
 */
@RestController
public class SeatController {
    int totalRows = 9;
    int totalColumns = 9;
    Room seats = new Room(totalRows, totalColumns); // Vorbestimmte Anzahl der Sitzreihen und Sitzspalten
    List<Ticket> tickets = new ArrayList<>(); // Liste der Tickets
    String phrase = "super_secret"; // Passwort für Statistik

    /**
     * Der Endpoint seats gibt verfügbare Sitzplätze mit der jeweiligen Reihe, Spalte und
     * dem jeweiligen Preis aus. Ferner gibt es die Anzahl der Reihen und Spalten zurück.
     * Dies erfolgt aufbereitet im JSON-Format.
     * @return Anzahl der Reihen, Spalten und Anzeige der verfügbaren Sitze
     */
    @GetMapping("/seats")
    public String returnSeat() {
        // Start der Nachricht mit Anzahl der Reihen und Spalten
        String json = "{\n" +
                "\t\"total_rows\":" + seats.getTotalRows() + ",\n" +
                "\t\"total_columns\":" + seats.getTotalColumns() + ",\n" +
                "\t\"available_seats\":[\n";

        /*
            Durchlaufe jeden Sitzplatz und gib bei seiner Verfügbarkeit seine Reihennummer,
            seine Spaltennummer und seinen Preis aus. Für den letzten Sitzplatz wird das kein Komma
            am Ende angehängt.
         */
        for (int i = 1; i <= seats.getTotalRows(); i++) {
            for (int j = 1; j <= seats.getTotalColumns(); j++) {
                if (i == seats.getTotalRows() && j == seats.getTotalColumns()) {
                    if (seats.getAvailableSeats()[i-1][j-1].isAvailable()) {
                        json += "\t\t{\n" +
                                "\t\t\t\"row\":" + seats.getAvailableSeats()[i-1][j-1].getRow() + ",\n" +
                                "\t\t\t\"column\":" + seats.getAvailableSeats()[i-1][j-1].getColumn() + ",\n" +
                                "\t\t\t\"price\":" + seats.getAvailableSeats()[i-1][j-1].getPrice() + "\n" +
                                "\t\t}\n";
                    }
                } else {
                    if (seats.getAvailableSeats()[i-1][j-1].isAvailable()) {
                        json += "\t\t{\n" +
                                "\t\t\t\"row\":" + seats.getAvailableSeats()[i-1][j-1].getRow()  + ",\n" +
                                "\t\t\t\"column\":" + seats.getAvailableSeats()[i-1][j-1].getColumn() + ",\n" +
                                "\t\t\t\"price\":" + seats.getAvailableSeats()[i-1][j-1].getPrice() + "\n" +
                                "\t\t},\n";
                    }
                }
            }
        }

        // Abschluss
        json += "\t]\n" +
                "}";

        // Rückgabe der Nachricht
        return json;
    }

    /**
     * Über den Endpoint purchase kann der Anwender ein Ticket erwerben, sofern sein gewünschter Sitzplatz
     * verfügbar ist. Falls der Sitzplatz belegt oder nicht aufzufinden ist, wird eine Fehlermeldung
     * ausgegeben.
     * @param purchase JSON-Nachricht mit Wunschsitzplatz aus Reihe und Spalte
     * @return Meldung über erfolgreichen Erwerb des gewünschten Sitzplatzes oder
     * Fehlermeldung wegen nicht verfügbaren Sitzplatz
     */
    @PostMapping("/purchase")
    public ResponseEntity<String> purchase(@RequestBody @Nullable Purchase purchase) {
        String message = ""; // Nachricht
        int totalRows = 9;
        int totalColumns = 9;

        /*
            Bestimme Reihe und Spalte aus dem übergebenen JSON
         */
        int row = purchase.getRow();
        int column = purchase.getColumn();

        /*
            Prüfen, ob Sitzreihe und Sitzspalte im Bereich des Kinosaals liegen
         */
        if ((column <= totalColumns && column >= 1) && (row <= totalRows && row >= 1)) {
            /*
                Prüfen, ob der gewünschte Sitzplatz bereits belegt ist.
                Bei verfügbaren Sitzplatz wird der gewünschte Sitzplatz als belegt gekennzeichnet,
                ein neues Ticket mit Token wird erstellt, und die Sitzreihe, Sitzspalte und
                der Preis in der Nachricht an den Anwender geliefert.
             */
            if (seats.getAvailableSeats()[row - 1][column - 1].isAvailable()) {
                seats.getAvailableSeats()[row - 1][column - 1].setAvailable(false);

                UUID token = UUID.randomUUID();

                tickets.add(new Ticket(seats.getAvailableSeats()[row - 1][column - 1], token));

                message =   "{\n" +
                        "\t\"token\": \"" + token + "\"," +
                        "\t\"ticket\": {" +
                        "\t\t\"row\": " + seats.getAvailableSeats()[row-1][column-1].getRow() + ",\n" +
                        "\t\t\"column\": " + seats.getAvailableSeats()[row-1][column-1].getColumn() + ",\n" +
                        "\t\t\"price\": " + seats.getAvailableSeats()[row-1][column-1].getPrice() + "\n" +
                        "\t}" +
                        "}";

                return ResponseEntity.status(HttpStatus.OK).body(message);
            } else {
                /*
                    Rückgabe der Nachricht, dass das Ticket bereits erworben ist bzw.
                    dass der Sitzplatz bereits reserviert ist.
                 */
                message =   "{\n" +
                        "\t\"error\": \"The ticket has been already purchased!\"\n" +
                        "}";

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
            }
        } else {
            // Rückgabe der Nachricht, dass der Sitzplatz außerhalb des möglichen Bereiches liegt.
            message =   "{\n" +
                    "\t\"error\": \"The number of a row or a column is out of bounds!\"\n" +
                    "}";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
    }

    /**
     * Der Endpoint return gibt dem Anwender zu einem Token das Ticket zurück und gibt den Sitz frei.
     * Ticket wird aus Liste der gekauften Tickets entfernt.
     * Wird zu einem Token kein Ticket gefunden, gibt der Endpoint eine Fehlermeldung zurück.
     * @param token Token
     * @return Meldung über erfolgreiche Rückgabe des Tickets oder
     * Fehlermeldung, wenn kein Ticket gefunden wurde zu dem übergebenen Token.
     */
    @PostMapping("/return")
    public ResponseEntity<String> returnTicket(@RequestBody Token token) {
        UUID ticketToken = token.getToken(); // Ermittle den Token aus dem RequestBody
        String message = ""; // Nachricht
        Ticket ticket = null;

        /*
            Durchsuche die Liste der Tickets nach dem passenden Element über den Token
         */
        for (Ticket elem : tickets) {
            if (elem.getToken().equals(ticketToken)) {
                ticket = elem;
                break;
            }
        }

        /*
            Wenn kein passendes Ticket zu dem Token gefunden wurde, dann melde dem Anwender
            "Wrong token!" zurück.
            Mit passendem Ticket gibt der Endpoint die Meldung zurück, dass das Ticket zurückgegeben wurde
            mit Angabe der Reihe, der Spalte und dem Preis.
            Gebe den Sitzplatz des Sitzes des ehemals reservierten Tickets frei.
            Entferne das Ticket aus der Liste.
         */
        if (ticket != null) {
            message =   "{\n" +
                    "\t\"returned_ticket\": {" +
                    "\t\t\"row\": " + seats.getAvailableSeats()[ticket.getSeat().getRow()-1][ticket.getSeat().getColumn()-1].getRow() + ",\n" +
                    "\t\t\"column\": " + seats.getAvailableSeats()[ticket.getSeat().getRow()-1][ticket.getSeat().getColumn()-1].getColumn() + ",\n" +
                    "\t\t\"price\": " + seats.getAvailableSeats()[ticket.getSeat().getRow()-1][ticket.getSeat().getColumn()-1].getPrice() + "\n" +
                    "\t}" +
                    "}";

            seats.getAvailableSeats()[ticket.getSeat().getRow()-1][ticket.getSeat().getColumn()-1].setAvailable(true);
            tickets.remove(ticket);

            return ResponseEntity.status(HttpStatus.OK).body(message);
        } else {
            message =   "{\n" +
                    "\t\"error\": \"Wrong token!\"\n" +
                    "}";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
    }

    /**
     * Gebe eine Statistik zum Aufrufzeitpunkt des Endpoints stats aus:
     * Die Statistik besteht aus dem generierten Einkommen, den noch freien Sitzplätzen
     * und den erworbenen Tickets.
     * Die Statistik darf nur dann ausgegeben werden, wenn der Anwender das korrekte Passwort
     * über den Webaufruf eingibt.
     * @param password Das einzugebende Passwort, um die Statistik aufrufen zu können.
     * @return Bei erfolgreicher Eingabe des Passworts gibt der Endpoint die Statistik aus,
     * sonst eine Fehlermeldung, dass das Passwort falsch ist.
     */
    @PostMapping("/stats")
    public ResponseEntity<String> showStatistics(@RequestParam Optional<String> password) {
        String message = ""; // Antwort
        int availableSeats = 0; // Freie Sitzplätze
        int purchasedTickets = 0; // Erworbene Tickets
        int current_income = 0; // Generiertes Einkommen durch belegte Sitzplätze

        // Prüfe, ob ein Passwort übergeben wurde
        if (password.isPresent()) {
            // Prüfe, ob das Passwort korrekt ist
            if (password.get().equals(phrase)) {
                /*
                    Prüfe für jeden Sitzplatz im Kinosaal, ob der Sitzplatz frei ist.
                    Für jeden belegten Sitzplatz wird sein Preis ermittelt und
                    dem generierten Einkommen mitgezählt.
                    Freie Sitzplätze werden gesondert gezählt.
                 */
                for (Seat[] row : seats.getAvailableSeats()) {
                    for (Seat seat : row) {
                        if (seat.isAvailable()) {
                            availableSeats++;
                        } else {
                            current_income += seat.getPrice();
                        }
                    }
                }

                // Ermittle die Anzahl gekaufter Tickets
                purchasedTickets = tickets.size();

                // Bereite die Statistik auf und liefere Sie zurück.
                message =   "{\n" +
                        "\t\"current_income\": " + current_income + ",\n" +
                        "\t\"number_of_available_seats\": " + availableSeats + ",\n" +
                        "\t\"number_of_purchased_tickets\": " + purchasedTickets + "\n" +
                        "}";


                return ResponseEntity.status(HttpStatus.OK).body(message);
            } else {
                /*
                    Passwort falsch:
                    Rückmeldung, dass das Passwort falsch ist und gebe einen HTTP-Code 401 zurück
                */
                message = "{\n" +
                        "\t\"error\": \"The password is wrong!\"\n" +
                        "}";
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
            }
        } else {
            /*
                Passwort nicht vorhanden:
                Rückmeldung, dass das Passwort falsch ist und gebe einen HTTP-Code 401 zurück
             */
            message = "{\n" +
                    "\t\"error\": \"The password is wrong!\"\n" +
                    "}";
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
        }
    }
}
