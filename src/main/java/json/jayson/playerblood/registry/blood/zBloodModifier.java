package json.jayson.playerblood.registry.blood;

import json.jayson.playerblood.bMod;
import json.jayson.playerblood.io.data.blood.BloodModify;
import json.jayson.playerblood.io.data.blood.BloodModifyHandler;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class zBloodModifier {
    public static HashMap<EntityType<?>, Float> blood_modifiers = new HashMap<>();
    public static HashMap<EntityType<?>, Float> blood_modifiers_max = new HashMap<>();
    public static void init() {
        if(!new File(bMod.BLOOD_MODIFIER_JSON).exists()) {
            BloodModifyHandler.saveAsFile("{\n" +
                    "  \"0.1\": {\n" +
                    "    \"minecraft:pillager\": {\n" +
                    "      \"blood\": 10.4,\n" +
                    "      \"maxBlood\": 10.4\n" +
                    "    },\n" +
                    "    \"minecraft:cave_spider\": {\n" +
                    "      \"blood\": 10.7,\n" +
                    "      \"maxBlood\": 10.7\n" +
                    "    },\n" +
                    "    \"minecraft:skeleton\": {\n" +
                    "      \"blood\": 10.4,\n" +
                    "      \"maxBlood\": 10.4\n" +
                    "    },\n" +
                    "    \"minecraft:player\": {\n" +
                    "      \"blood\": 10.4,\n" +
                    "      \"maxBlood\": 10.4\n" +
                    "    },\n" +
                    "    \"minecraft:enderman\": {\n" +
                    "      \"blood\": 10.7,\n" +
                    "      \"maxBlood\": 10.7\n" +
                    "    },\n" +
                    "    \"minecraft:zombie\": {\n" +
                    "      \"blood\": 10.2,\n" +
                    "      \"maxBlood\": 10.2\n" +
                    "    },\n" +
                    "    \"minecraft:creeper\": {\n" +
                    "      \"blood\": 10.3,\n" +
                    "      \"maxBlood\": 10.3\n" +
                    "    },\n" +
                    "    \"minecraft:wither\": {\n" +
                    "      \"blood\": 550.0,\n" +
                    "      \"maxBlood\": 550.0\n" +
                    "    },\n" +
                    "    \"minecraft:bee\": {\n" +
                    "      \"blood\": 10.0,\n" +
                    "      \"maxBlood\": 10.0\n" +
                    "    },\n" +
                    "    \"minecraft:ender_dragon\": {\n" +
                    "      \"blood\": 600.0,\n" +
                    "      \"maxBlood\": 600.0\n" +
                    "    },\n" +
                    "    \"minecraft:ghast\": {\n" +
                    "      \"blood\": 20.0,\n" +
                    "      \"maxBlood\": 20.0\n" +
                    "    }\n" +
                    "  }\n" +
                    "}");
        }

        BloodModify bloodModify = BloodModifyHandler.loadObject(BloodModifyHandler.loadJson(bMod.BLOOD_MODIFIER_JSON));
        for (Map.Entry<String, BloodModify.Modifier> stringModifierEntry : bloodModify.entity.entrySet()) {
            if(ForgeRegistries.ENTITIES.containsKey(new ResourceLocation(stringModifierEntry.getKey()))) {
                blood_modifiers.put(ForgeRegistries.ENTITIES.getValue(new ResourceLocation(stringModifierEntry.getKey())), stringModifierEntry.getValue().blood);
                blood_modifiers_max.put(ForgeRegistries.ENTITIES.getValue(new ResourceLocation(stringModifierEntry.getKey())), stringModifierEntry.getValue().maxBlood);
            } else {
                System.out.println("BloodModify: Entity " + stringModifierEntry.getKey() + " not found!");
            }
        }
    }
}
