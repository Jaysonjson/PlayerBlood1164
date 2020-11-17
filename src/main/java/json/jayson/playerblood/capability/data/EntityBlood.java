package json.jayson.playerblood.capability.data;

import json.jayson.playerblood.capability.zCapability;
import json.jayson.playerblood.capability.interfaces.IEntityBlood;
import json.jayson.playerblood.network.packet.EntityBloodPacket;
import json.jayson.playerblood.network.zNetwork;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;

public class EntityBlood implements IEntityBlood {

    public float blood = 1.0f;
    public float maxBlood = 100.0f;

    @Nonnull
    public static LazyOptional<IEntityBlood> get(Entity entity) {
        return entity.getCapability(zCapability.ENTITY_BLOOD, null);
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

    @Override
    public void adjustMaxBlood(float adjustment) {
        this.maxBlood = this.maxBlood + adjustment;
    }

    @Override
    public void adjustBlood(float adjustment) {
        this.adjustBlood(adjustment, false);
    }

    //Might also just do adjustMaxBlood(-int)
    @Override
    public void decreaseMaxBlood(float decreasement) {
        this.maxBlood = this.maxBlood - decreasement;
    }

    //Might also just do adjustBlood(-int)
    @Override
    public void decreaseBlood(float decreasement) {
        this.decreaseBlood(decreasement, false);
    }

    @Override
    public void adjustBlood(float adjustment, boolean checkMax) {
        if(checkMax) {
            if((this.blood + adjustment) > this.maxBlood) {
                // return this.blood = this.blood + adjustment - maxBlood;
                this.blood = this.maxBlood;
                return;
            }
        }
        this.blood = this.blood + adjustment;
    }

    @Override
    public void decreaseBlood(float decreasement, boolean checkMax) {
        if(checkMax) {
            if((this.blood - decreasement) < 0) {
                return;
            }
        }
        this.blood = this.blood - decreasement;
    }

    @Override
    public void resetBlood() {
        this.blood = 0;
    }

    @Override
    public void resetMaxBlood() {
        this.maxBlood = 100;
    }

    @Override
    public boolean isOverflow(float blood) {
        if((this.blood + blood) > this.maxBlood) {
            return true;
        }
        return false;
    }

    @Override
    public float getOverflow(float blood) {
        if(isOverflow(blood)) {
            return (this.blood + blood) - this.maxBlood;
        }
        return 0;
    }

    @Override
    public void syncRemote(Entity entity) {
        if(!entity.getEntityWorld().isRemote()) {
            IEntityBlood data = get(entity).orElse(null);
            zNetwork.sendPacketToAll(new EntityBloodPacket(entity.getUniqueID(), data.serializeNBT(), entity.getEntityId()));
        }
    }

}
