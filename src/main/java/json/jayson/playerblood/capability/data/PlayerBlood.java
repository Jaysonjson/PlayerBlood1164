package json.jayson.playerblood.capability.data;

import json.jayson.playerblood.capability.interfaces.IPlayerBlood;
import json.jayson.playerblood.network.packet.PlayerBloodPacket;
import json.jayson.playerblood.network.zNetwork;
import json.jayson.playerblood.registry.zCapability;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;

public class PlayerBlood implements IPlayerBlood {

    public float blood = 0.0f;
    public float maxBlood = 100.0f;
    public PlayerBlood() {
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
    @Deprecated
    @Override
    public void sync(PlayerEntity player) {
        IPlayerBlood data = get(player).orElse(null);
        zNetwork.sendPacketToAll(new PlayerBloodPacket(player.getUniqueID(), data.serializeNBT()));
        //PBNetwork.sendTo(new PlayerBloodPacket(entity.getUniqueID(), data.serializeNBT()), entity);
    }

    @Override
    public void syncRemote(PlayerEntity player) {
        if(!player.getEntityWorld().isRemote()) {
            IPlayerBlood data = get(player).orElse(null);
            zNetwork.sendPacketToAll(new PlayerBloodPacket(player.getUniqueID(), data.serializeNBT()));
        }
    }
    @Deprecated
    @Override
    public void syncServer(PlayerEntity player, MinecraftServer server) {
        IPlayerBlood data = get(player).orElse(null);
        zNetwork.sendPacketToAll(new PlayerBloodPacket(player.getUniqueID(), data.serializeNBT()), server);
    }

    @Nonnull
    public static LazyOptional<IPlayerBlood> get(Entity player) {
        return player.getCapability(zCapability.PLAYER_BLOOD, null);
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

}
