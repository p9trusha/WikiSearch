import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import org.apache.commons.io.IOUtils;

class ConsoleInput {
    Scanner in = new Scanner(System.in);

    String getWikiRequest() {
        System.out.print("Введите поисковой запрос: ");
        String wikiRequest = this.in.nextLine();
        return wikiRequest;
    }

    void close() {
        this.in.close();
    }
}

public class Main {
    public static void main(String[] args) {
        ConsoleInput cip = new ConsoleInput();
        String wikiRequest = cip.getWikiRequest();
        cip.close();
        try {
            wikiRequest = URLEncoder.encode(wikiRequest, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        String apiLink = String.format(
                "https://ru.wikipedia.org/w/api.php?action=query&list=search&utf8=&format=json&srsearch=\"%s\"",
                wikiRequest
        );
        System.out.println(apiLink);
        try {
            String jsonContent = IOUtils.toString(new URL(apiLink), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}