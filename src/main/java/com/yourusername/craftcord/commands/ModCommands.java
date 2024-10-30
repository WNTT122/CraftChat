package com.yourusername.craftcord.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.ServerCommandSource;
import static net.minecraft.server.command.CommandManager.literal;
import static net.minecraft.server.command.CommandManager.argument;
import net.minecraft.server.network.ServerPlayerEntity;
import com.mojang.brigadier.arguments.EntityArgumentType;
import com.yourusername.craftcord.friends.FriendSystem;
import com.yourusername.craftcord.party.PartyManager;
import net.minecraft.text.Text;

public class ModCommands {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        // Party commands
        dispatcher.register(literal("party")
            .then(literal("create")
                .executes(context -> createParty(context)))
            .then(literal("invite")
                .then(argument("player", EntityArgumentType.player())
                    .executes(context -> invitePlayer(context))))
            .then(literal("leave")
                .executes(context -> leaveParty(context)))
            .then(literal("list")
                .executes(context -> listPartyMembers(context)))
        );

        // Friend commands
        dispatcher.register(literal("friend")
            .then(literal("add")
                .then(argument("player", EntityArgumentType.player())
                    .executes(context -> addFriend(context))))
            .then(literal("remove")
                .then(argument("player", EntityArgumentType.player())
                    .executes(context -> removeFriend(context))))
            .then(literal("list")
                .executes(context -> listFriends(context)))
        );

        // Chat commands
        dispatcher.register(literal("chat")
            .then(literal("channel")
                .then(argument("channel", StringArgumentType.word())
                    .executes(context -> switchChannel(context))))
            .then(literal("clear")
                .executes(context -> clearChat(context)))
        );
    }

    private static int createParty(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = context.getSource().getPlayer();
        PartyManager.createParty(player.getUuid());
        context.getSource().sendFeedback(() -> 
            Text.literal("Party created!"), false);
        return 1;
    }

    // Implement other command handlers...
} 