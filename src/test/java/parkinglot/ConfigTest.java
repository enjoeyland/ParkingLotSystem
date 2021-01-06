package parkinglot;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import org.junit.jupiter.api.Test;
import parkinglot.configUtil.Config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;

/**
 * @author 민경현(Min Kyung Hyun)
 * contact : khmin1104@gmail.com
 */
class ConfigTest {
    Gson gson = new Gson();
    @Test
    void readFromJsonFile() throws FileNotFoundException {
        Path path = Path.of("src/main/java/config","parkinglotConfig.json");
        JsonReader reader = new JsonReader(new FileReader(path.toFile()));
    }

    @Test
    void ConfigObjectFromJsonFileByGson() throws FileNotFoundException {
        Path path = Path.of("src/main/java/config","parkinglotConfig.json");
        JsonReader reader = new JsonReader(new FileReader(path.toFile()));
        Config c = gson.fromJson(reader, Config.class);
        System.out.println(c);
    }

}