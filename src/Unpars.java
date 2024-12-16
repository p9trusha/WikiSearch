import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Unpars {
    public static Query unpars(String stringJson)  {
        //теги которые нам нужны
        String TagQuery = "query";
        String TagSearch = "search";
        String TagTitle = "title";
        String TagPageID = "pageid";

        try {
            //Создаем jsonObject в который добавляем считанный json архив
            JsonObject jsonObject = (JsonObject) JsonParser.parseString(stringJson);
            //Создаем queryObject в который добавляем объект query из архива
            JsonObject queryObject = (JsonObject) jsonObject.get(TagQuery);
            //Создаем searchArray в который добавляем список из объекта query
            JsonArray searchArray = (JsonArray) queryObject.get(TagSearch);
            
            //getQuery возвращает класс Query
            //данная функция добавляет в Query данные из архива
            return (getQuery(searchArray, TagTitle, TagPageID));
        } catch (Exception _) {
            return null;
        }
    }

    private static Query getQuery(JsonArray searchArray, String TagTitle, String TagPageID) {
        Query query = new Query();
        //перебираем объекты внутри списка
        for (Object item: searchArray) {
            //создаем буферный объект
            JsonObject searchObject = (JsonObject) item;
            //из буфера достаем нужные нам данные
            Object pageTitle = searchObject.get(TagTitle);
            Object pageID = searchObject.get(TagPageID);
            //добавляем их в класс query

            query.putPageId(pageID.toString());
            query.putTitle(pageTitle.toString());
        }
        //возвращаем класс
        return query;
    }
}
