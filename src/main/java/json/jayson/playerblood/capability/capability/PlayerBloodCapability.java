package json.jayson.playerblood.capability.capability;

import json.jayson.playerblood.capability.zCapability;
import json.jayson.playerblood.capability.interfaces.IPlayerBlood;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PlayerBloodCapability implements ICapabilitySerializable<CompoundNBT> {
    public LazyOptional<IPlayerBlood> playerBlood = LazyOptional.of(zCapability.PLAYER_BLOOD::getDefaultInstance);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == zCapability.PLAYER_BLOOD ? playerBlood.cast() : LazyOptional.empty();
    }

    @Override
    public CompoundNBT serializeNBT() {
        return (CompoundNBT) zCapability.PLAYER_BLOOD.getStorage().writeNBT(zCapability.PLAYER_BLOOD, playerBlood.orElseThrow(() -> new IllegalArgumentException("LazyOptional darf nicht Leer sein! --> PlayerBlood")), null);
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        zCapability.PLAYER_BLOOD.getStorage().readNBT(zCapability.PLAYER_BLOOD, playerBlood.orElseThrow(() -> new IllegalArgumentException("LazyOptional darf nicht Leer sein! --> PlayerBlood")), null, nbt);
    }
}
