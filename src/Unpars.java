
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
public class Unpars {
    public static Query main(String[] args, String inputline) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JsonClass jsonClass = mapper.readValue(inputline, JsonClass.class);

        String query = jsonClass.query.toString();
        Query pages = new Query();

        while (query.indexOf("title") > 0) {
            int k = query.indexOf("title");
            query = query.substring(k);
            int t = query.indexOf("pageid");
            String title = query.substring(6,t-2);
            query = query.substring(t);
            int i = query.indexOf(",");
            String pageid = query.substring(7, i );

            pages.pageid.add(pageid);
            pages.title.add(title);
        }

        return (pages);
    }
}

