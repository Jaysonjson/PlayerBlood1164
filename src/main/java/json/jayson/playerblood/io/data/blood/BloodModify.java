package json.jayson.playerblood.io.data.blood;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class BloodModify {
    @SerializedName(value = "client", alternate = "0.1")
    public HashMap<String, Modifier> entity = new HashMap<>();

    public static class Modifier {

        public float blood;
        public float maxBlood;

        public Modifier(float blood, float maxBlood) {
            this.blood = blood;
            this.maxBlood = maxBlood;
        }
    }
}
