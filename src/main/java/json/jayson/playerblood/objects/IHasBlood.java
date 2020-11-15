package json.jayson.playerblood.objects;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;

public interface IHasBlood {
    float getAmount();
    Entity getEntity();
    String getEntityString();

    void setAmount(float amount);
    void setEntity(Entity entity);
    void setEntityString(String entityString);

    void decreaseAmount(float amount);
    void increaseAmount(float amount);

    void readNBT(CompoundNBT nbt, IHasBlood blood);
    void setNBT(CompoundNBT nbt, IHasBlood blood);
}
