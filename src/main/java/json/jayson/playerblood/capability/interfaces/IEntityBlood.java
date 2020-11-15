package json.jayson.playerblood.capability.interfaces;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public interface IEntityBlood extends INBTSerializable<CompoundNBT> {

    float getBlood();
    float getMaxBlood();

    void setBlood(float blood);
    void setMaxBlood(float maxBlood);

}
