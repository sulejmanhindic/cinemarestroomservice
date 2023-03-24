package cinema;

import java.util.UUID;

/**
 * Klasse für ein Token zum Nachvollziehen eines Verkaufs
 */
public class Token {
    private UUID token;

    /**
     * Gebe den Token zurück
     * @return Token
     */
    public UUID getToken() {
        return token;
    }

    /**
     * Setze einen Token
     * @param token Token
     */
    public void setToken(UUID token) {
        this.token = token;
    }
}