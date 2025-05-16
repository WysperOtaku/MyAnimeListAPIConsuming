package model.classes;

public class TokenInfo {
    public String access_token;
    public String refresh_token;
    public long expires_in;
    public transient long obtained_at;

    public TokenInfo(String access_token, String refresh_token, long expires_in) {
        this.access_token = access_token;
        this.refresh_token = refresh_token;
        this.expires_in = expires_in;
        this.obtained_at = System.currentTimeMillis();
    }

    public TokenInfo() {
        this.obtained_at = System.currentTimeMillis();
    }

    public boolean isExpired() {
        long now = System.currentTimeMillis();
        long expires = obtained_at + (expires_in * 1000);
        return now >= (expires - 60 * 1000);
    }
}
