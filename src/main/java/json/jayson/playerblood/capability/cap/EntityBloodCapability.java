package json.jayson.playerblood.capability.cap;

import json.jayson.playerblood.capability.interfaces.IEntityBlood;
import json.jayson.playerblood.registry.zCapability;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EntityBloodCapability implements ICapabilitySerializable<CompoundNBT> {

    public LazyOptional<IEntityBlood> entityBlood = LazyOptional.of(zCapability.ENTITY_BLOOD::getDefaultInstance);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == zCapability.ENTITY_BLOOD ? entityBlood.cast() : LazyOptional.empty();
    }

    @Override
    public CompoundNBT serializeNBT() {
        return (CompoundNBT) zCapability.ENTITY_BLOOD.getStorage().writeNBT(zCapability.ENTITY_BLOOD, entityBlood.orElseThrow(() -> new IllegalArgumentException("LazyOptional ist Leer! --> PlayerBlood")), null);
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
    	zCapability.ENTITY_BLOOD.getStorage().readNBT(zCapability.ENTITY_BLOOD, entityBlood.orElseThrow(() -> new IllegalArgumentException("LazyOptional ist Leer! --> PlayerBlood")), null, nbt);
    }

}
