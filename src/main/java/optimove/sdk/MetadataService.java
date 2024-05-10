package optimove.sdk;
import com.google.gson.Gson;

public class MetadataService {
    public static Metadata jsonStringToMetadata(String jsonString) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(jsonString , Metadata.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
