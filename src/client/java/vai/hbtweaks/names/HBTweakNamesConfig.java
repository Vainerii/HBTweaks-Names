package vai.hbtweaks.names;

import net.minecraft.client.gui.screens.Screen;

import java.util.HashMap;
import java.util.Map;

public class HBTweakNamesConfig {

    private static final Map<String, Object> config = new HashMap<>();

    public static Object get(String key) {
        return HBTweakNamesConfig.config.get(key);
    }

    public static void set(String key, Object value) {
        HBTweakNamesConfig.config.put(key, value);
    }

    /*private static void readMainConfig(File file) throws IOException {
        JsonReader jr = new JsonReader(new FileReader(file));
        JsonElement jsonEl = new Gson().fromJson(jr, JsonElement.class);
        jr.close();
        if (jsonEl != null) {
            HerobrineCompanionConfig.config.clear();
            JsonObject json = jsonEl.getAsJsonObject();
            if(json.has("name.toggle"))
                HerobrineCompanionConfig.config.put("name.toggle", json.get("name.toggle").getAsBoolean());
        }
    }*/


    public static Screen setup(Screen parent){

        return null;
    }

}
