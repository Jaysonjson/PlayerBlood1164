package json.jayson.playerblood.capability.interfaces;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.util.INBTSerializable;

public interface IPlayerBlood extends INBTSerializable<CompoundNBT> {
   
	float getBlood();
    float getMaxBlood();

    void setBlood(float blood);
    void setMaxBlood(float maxBlood);

    void adjustMaxBlood(float adjustment);
    void adjustBlood(float adjustment);

    void decreaseMaxBlood(float decreasement);
    void decreaseBlood(float decreasement);

    void adjustBlood(float adjustment, boolean checkMax);

    void decreaseBlood(float decreasement, boolean checkMax);

    void resetBlood();
    void resetMaxBlood();

    boolean isOverflow(float blood);
    float getOverflow(float blood);
   
    @Deprecated
    void sync(PlayerEntity player);
    void syncRemote(PlayerEntity player);
    @Deprecated
    void syncServer(PlayerEntity player, MinecraftServer server);
    
}
