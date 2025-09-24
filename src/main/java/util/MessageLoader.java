package util;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class MessageLoader {
    private static Map<String, String> messages;

    static {
        try (InputStream is = MessageLoader.class.getClassLoader().getResourceAsStream("responses/messages.json")) {
            ObjectMapper mapper = new ObjectMapper();
            messages = mapper.readValue(is, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        return messages.getOrDefault(key, "Messages not found");
    }
}
