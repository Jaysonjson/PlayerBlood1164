package json.jayson.playerblood.network.packet;

import json.jayson.playerblood.capability.data.EntityBlood;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class EntityBloodPacket {

    @Deprecated
	private UUID entity;
	private int entityID;
    private CompoundNBT data;

    public EntityBloodPacket(UUID entity, CompoundNBT data, int entityID) {
        this.entity = entity;
        this.entityID = entityID;
        this.data = data;
    }

    public static void encode(EntityBloodPacket message, PacketBuffer buffer) {
        buffer.writeUniqueId(message.entity);
        buffer.writeCompoundTag(message.data);
        buffer.writeInt(message.entityID);
    }

    public static EntityBloodPacket decode(PacketBuffer packetBuffer) {
        return new EntityBloodPacket(packetBuffer.readUniqueId(), packetBuffer.readCompoundTag(), packetBuffer.readInt());
    }

    public static void handle(EntityBloodPacket message, Supplier<NetworkEvent.Context> ctx) {
		Entity entity = Minecraft.getInstance().world.getEntityByID(message.entityID);
        if (entity != null)
            Minecraft.getInstance().deferTask(() -> EntityBlood.get(entity).ifPresent((data) -> data.deserializeNBT(message.data)));
            ctx.get().setPacketHandled(true);
    }
    
}
