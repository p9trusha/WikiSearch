import com.fasterxml.jackson.annotation.JsonSetter;
import java.util.List;

class Query {
    public List<String> title  = new java.util.ArrayList<>(List.of());;
    public List<String> pageid = new java.util.ArrayList<>(List.of());;
}

class JsonClass {
    @JsonSetter("query")
    Object query;
}