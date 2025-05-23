package model.classes;

public class TokenInfo {
    public String access_token;
    public String refresh_token;
    public long expires_in;
    public long obtained_at;

    public TokenInfo(String access_token, String refresh_token, long expires_in) {
        this.access_token = access_token;
        this.refresh_token = refresh_token;
        this.expires_in = expires_in;
        this.obtained_at = System.currentTimeMillis();
    }

    // CONSTRUCTOR PARA CREAR UN TOKEN EXISTENTE
    public TokenInfo(String accessToken, String refreshToken, long expiresIn, long obtainedAt) {
        this.access_token = accessToken;
        this.refresh_token = refreshToken;
        this.expires_in = expiresIn;
        this.obtained_at = obtainedAt;
    }

    public TokenInfo() {
        this.obtained_at = System.currentTimeMillis();
    }

    public boolean isExpired() {
        long now = System.currentTimeMillis();
        long expires = obtained_at + (expires_in * 1000);
        return now >= (expires - 60 * 1000);
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
    }

    public long getObtained_at() {
        return obtained_at;
    }

    public void setObtained_at(long obtained_at) {
        this.obtained_at = obtained_at;
    }

    @Override
    public String toString() {
        return "TokenInfo{" +
                "access_token='" + access_token + '\'' +
                ", refresh_token='" + refresh_token + '\'' +
                ", expires_in=" + expires_in +
                ", obtained_at=" + obtained_at +
                '}';
    }
}
