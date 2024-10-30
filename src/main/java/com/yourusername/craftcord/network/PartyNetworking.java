package com.yourusername.craftcord.network;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import com.yourusername.craftcord.CraftCord;

public class PartyNetworking {
    public static final Identifier PARTY_INVITE = new Identifier(CraftCord.MOD_ID, "party_invite");
    public static final Identifier PARTY_JOIN = new Identifier(CraftCord.MOD_ID, "party_join");
    public static final Identifier PARTY_LEAVE = new Identifier(CraftCord.MOD_ID, "party_leave");
    public static final Identifier PARTY_KICK = new Identifier(CraftCord.MOD_ID, "party_kick");
    public static final Identifier PARTY_CHAT = new Identifier(CraftCord.MOD_ID, "party_chat");

    public static void registerHandlers() {
        // Handle party invites
        ServerPlayNetworking.registerGlobalReceiver(PARTY_INVITE, (server, player, handler, buf, responseSender) -> {
            UUID targetPlayer = buf.readUuid();
            UUID partyId = buf.readUuid();
            
            server.execute(() -> {
                PartyManager.handleInvite(player.getUuid(), targetPlayer, partyId);
            });
        });

        // Handle party joins
        ServerPlayNetworking.registerGlobalReceiver(PARTY_JOIN, (server, player, handler, buf, responseSender) -> {
            UUID partyId = buf.readUuid();
            
            server.execute(() -> {
                PartyManager.joinParty(partyId, player.getUuid());
                broadcastPartyUpdate(partyId);
            });
        });

        // Additional handlers...
    }

    public static void sendPartyInvite(UUID targetPlayer, UUID partyId) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeUuid(targetPlayer);
        buf.writeUuid(partyId);
        ServerPlayNetworking.send(PARTY_INVITE, buf);
    }

    private static void broadcastPartyUpdate(UUID partyId) {
        Party party = PartyManager.getParty(partyId);
        if (party == null) return;

        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeUuid(partyId);
        party.writeToPacket(buf);

        for (UUID member : party.getMembers()) {
            ServerPlayerEntity player = server.getPlayerManager().getPlayer(member);
            if (player != null) {
                ServerPlayNetworking.send(player, PARTY_UPDATE, buf);
            }
        }
    }
} 