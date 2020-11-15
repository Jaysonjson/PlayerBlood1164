package json.jayson.playerblood.network;

import json.jayson.playerblood.bMod;
import json.jayson.playerblood.network.packet.PlayerBloodPacket;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class zNetwork {
	
    private static final String PROTOCOL_VERSION = Integer.toString(1);
    public static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder
            .named(new ResourceLocation(bMod.MOD_ID, "main_channel"))
            .clientAcceptedVersions(PROTOCOL_VERSION::equals)
            .serverAcceptedVersions(PROTOCOL_VERSION::equals)
            .networkProtocolVersion(() -> PROTOCOL_VERSION)
            .simpleChannel();


    public static void init() {
        int id = 0;
        //INSTANCE.registerMessage(id++, EntityBloodPacket.class, EntityBloodPacket::encode, EntityBloodPacket::decode, EntityBloodPacket::handle);
        INSTANCE.registerMessage(id++, PlayerBloodPacket.class, PlayerBloodPacket::encode, PlayerBloodPacket::decode, PlayerBloodPacket::handle);
    }

    public static void sendToServer(Object msg) {
        INSTANCE.sendToServer(msg);
    }

    public static void sendTo(Object msg, ServerPlayerEntity player) {
        if (!(player instanceof FakePlayer)) {
            INSTANCE.sendTo(msg, player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
        }
    }

    public static void sendPacketToAll(Object packet) {
        sendPacketToAll(packet, ServerLifecycleHooks.getCurrentServer());
    }

    public static void sendPacketToAll(Object packet, MinecraftServer server) {
        for (ServerPlayerEntity player : server.getPlayerList().getPlayers()) {
            sendTo(packet, player);
        }
    }

}
