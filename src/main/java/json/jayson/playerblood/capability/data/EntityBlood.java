package json.jayson.playerblood.capability.data;

import json.jayson.playerblood.capability.interfaces.IEntityBlood;
import json.jayson.playerblood.registry.zCapability;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;

public class EntityBlood implements IEntityBlood {

    public float blood = 0.0f;
    public float maxBlood = 100.0f;

    @Nonnull
    public static LazyOptional<IEntityBlood> get(Entity player) {
        return player.getCapability(zCapability.ENTITY_BLOOD, null);
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putFloat("storedBlood", getBlood());
        nbt.putFloat("maxBlood", getMaxBlood());
        return nbt;
    }


    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        setBlood(nbt.getFloat("storedBlood"));
        setMaxBlood(nbt.getFloat("maxBlood"));
    }

    @Override
    public float getBlood() {
        return this.blood;
    }

    @Override
    public float getMaxBlood() {
        return this.maxBlood;
    }

    @Override
    public void setBlood(float blood) {
        this.blood = blood;
    }

    @Override
    public void setMaxBlood(float maxBlood) {
        this.maxBlood = maxBlood;
    }

}
