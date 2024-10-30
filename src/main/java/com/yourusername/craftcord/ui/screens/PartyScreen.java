package com.yourusername.craftcord.ui.screens;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import com.yourusername.craftcord.social.party.Party;
import com.yourusername.craftcord.social.party.PartyManager;

public class PartyScreen extends Screen {
    private final Party party;
    private final boolean isLeader;

    public PartyScreen(Party party) {
        super(Text.literal("Party Management"));
        this.party = party;
        this.isLeader = party.isLeader(MinecraftClient.getInstance().player.getUuid());
    }

    @Override
    protected void init() {
        int y = 40;
        
        // Show party members
        for (UUID memberId : party.getMembers()) {
            String memberName = getPlayerName(memberId);
            PartyRole role = party.getRole(memberId);
            
            Text memberText = Text.literal(memberName + " (" + role + ")");
            
            if (isLeader && !party.isLeader(memberId)) {
                // Add kick button for leader
                this.addDrawableChild(ButtonWidget.builder(
                    Text.literal("Kick"),
                    button -> kickMember(memberId))
                    .dimensions(this.width / 2 + 60, y, 40, 20)
                    .build());
            }
            
            drawTextWithShadow(matrices, textRenderer, memberText, 
                this.width / 2 - 100, y + 5, 0xFFFFFF);
            
            y += 25;
        }

        // Party controls
        if (isLeader) {
            this.addDrawableChild(ButtonWidget.builder(
                Text.literal("Invite Player"),
                button -> this.client.setScreen(new PartyInviteScreen(this)))
                .dimensions(this.width / 2 - 100, y, 200, 20)
                .build());
            y += 25;
        }

        // Leave party button
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("Leave Party"),
            button -> leaveParty())
            .dimensions(this.width / 2 - 100, y, 200, 20)
            .build());
    }

    private void kickMember(UUID memberId) {
        PartyManager.removeMember(party.getPartyId(), memberId);
        this.init(); // Refresh screen
    }

    private void leaveParty() {
        PartyManager.leaveParty(MinecraftClient.getInstance().player.getUuid());
        this.client.setScreen(null);
    }
} 