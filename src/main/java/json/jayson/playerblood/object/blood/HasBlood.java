package json.jayson.playerblood.object.blood;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;

@Deprecated
public class HasBlood implements IHasBlood {

    public float amount;
    public String owner;
    public Entity entity;

    public HasBlood(float amount, String entityString, Entity entity) {
        this.amount = amount;
        this.owner = entityString;
        this.entity = entity;
    }

    @Override
    public float getAmount() {
        return amount;
    }

    @Override
    public Entity getEntity() {
        return entity;
    }

    @Override
    public String getEntityString() {
        return owner;
    }

    @Override
    public void setAmount(float amount) {
        this.amount = amount;
    }

    @Override
    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    @Override
    public void setEntityString(String entityString) {
        this.owner = entityString;
    }

    @Override
    public void decreaseAmount(float amount) {
        this.amount += amount;
    }

    @Override
    public void increaseAmount(float amount) {
        this.amount += amount;
    }

    @Override
    public void readNBT(CompoundNBT nbt, IHasBlood blood) {
        nbt.putFloat("Amount", blood.getAmount());
        nbt.putString("EntityString", blood.getEntityString());
    }

    @Override
    public void setNBT(CompoundNBT nbt, IHasBlood blood) {
        blood.setAmount(nbt.getFloat("Amount"));
        blood.setEntityString(nbt.getString("EntityString"));
    }
}
