package json.jayson.playerblood.network.packet;

import json.jayson.playerblood.capability.data.EntityBlood;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

@Deprecated
public class EntityBloodPacket {
    
	private UUID entity;
    private CompoundNBT data;

    public EntityBloodPacket(UUID entity, CompoundNBT data) {
        this.entity = entity;
        this.data = data;
    }

    public static void encode(EntityBloodPacket message, PacketBuffer buffer) {
        buffer.writeUniqueId(message.entity);
        buffer.writeCompoundTag(message.data);
    }

    public static EntityBloodPacket decode(PacketBuffer packetBuffer) {
        return new EntityBloodPacket(packetBuffer.readUniqueId(), packetBuffer.readCompoundTag());
    }

    public static void handle(EntityBloodPacket message, Supplier<NetworkEvent.Context> ctx) {
        @SuppressWarnings("resource")
		PlayerEntity player = Minecraft.getInstance().world.getPlayerByUuid(message.entity);
        if (player != null)
            Minecraft.getInstance().deferTask(() -> EntityBlood.get(player).ifPresent((data) -> data.deserializeNBT(message.data)));
            ctx.get().setPacketHandled(true);
    }
    
}
