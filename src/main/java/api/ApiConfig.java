package api;
import com.google.gson.Gson;
import java.io.FileReader;
import java.io.IOException;

public class ApiConfig {
    public String client_id;
    public String client_secret;
    public String redirect_uri;
    public String code_challenge;

    public static ApiConfig load(String path) throws IOException {
        try (FileReader reader = new FileReader(path)) {
            return new Gson().fromJson(reader, ApiConfig.class);
        }
    }
}
