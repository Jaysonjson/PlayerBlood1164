package json.jayson.playerblood.capability.storage;

import json.jayson.playerblood.capability.interfaces.IEntityBlood;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class EntityBloodStorage implements Capability.IStorage<IEntityBlood> {

    @Nullable
    @Override
    public INBT writeNBT(Capability<IEntityBlood> capability, IEntityBlood instance, Direction side) {
        return instance.serializeNBT();
    }

    @Override
    public void readNBT(Capability<IEntityBlood> capability, IEntityBlood instance, Direction side, INBT nbt) {
        instance.deserializeNBT((CompoundNBT) nbt);
    }

}
