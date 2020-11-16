package json.jayson.playerblood.io.data.blood;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import json.jayson.playerblood.bMod;
import net.minecraft.entity.EntityType;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class BloodModifyHandler {

    public static String newObject(String json, EntityType<?> entity, float blood, float maxBlood) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        BloodModify bloodModifier = gson.fromJson(json, BloodModify.class);
        bloodModifier.entity.put(entity.getRegistryName().getNamespace() + ":" + entity.getRegistryName().getPath(), new BloodModify.Modifier(blood, maxBlood));
        return gson.toJson(bloodModifier);
    }

    public static BloodModify loadObject(String json) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        BloodModify bloodModify = gson.fromJson(json, BloodModify.class);
        return bloodModify;
    }

    public static String loadJson(String path) {
        String content = "{}";
        try {
            content = FileUtils.readFileToString(new File(path), "utf-8");
        } catch (IOException ioexc) {
            System.out.println("IOException - file " + path + " not found!");
        }
        return content;
    }

    public static void saveAsFile(String json) {
        try {
            new File(bMod.BLOOD_MODIFIER_DIR).mkdirs();
            FileOutputStream fileOutputStream = new FileOutputStream(bMod.BLOOD_MODIFIER_JSON);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            outputStreamWriter.append(json);
            outputStreamWriter.close();
            fileOutputStream.close();
        } catch (Exception exc) {

        }
    }
}
