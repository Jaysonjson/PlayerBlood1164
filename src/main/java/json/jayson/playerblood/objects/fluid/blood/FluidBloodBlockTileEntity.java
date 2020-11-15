package json.jayson.playerblood.objects.fluid.blood;

import json.jayson.playerblood.objects.IHasBlood;
import json.jayson.playerblood.registry.zTileEntity;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

import javax.annotation.Nullable;

public class FluidBloodBlockTileEntity extends TileEntity implements IHasBlood {

    public float amount = 1;
    public String owner = "Unknown";
    public int lifetime = 3000000;

    public FluidBloodBlockTileEntity(TileEntityType<?> p_i48289_1_) {
        super(p_i48289_1_);
    }

    public FluidBloodBlockTileEntity() {
        super(zTileEntity.BLOOD.get());
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt) {
        if (nbt == null) nbt = new CompoundNBT();
        nbt.putFloat("Amount", this.amount);
        nbt.putString("Owner", this.owner);
        nbt.putInt("Lifetime", this.lifetime);
        return super.write(nbt);
    }


    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.getPos(), 0, this.getUpdateTag());
    }
    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket packet) {
        this.readNBT(packet.getNbtCompound(), this);
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    public float getAmount() {
        return amount;
    }

    public Entity getEntity() {
        return null;
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
        this.amount = nbt.getFloat("Amount");
        this.owner = nbt.getString("Owner");
        this.lifetime = nbt.getInt("Lifetime");
    }

    @Override
    public void setNBT(CompoundNBT nbt, IHasBlood blood) {
        write(nbt);
    }
}
