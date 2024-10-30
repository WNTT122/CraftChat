package com.yourusername.craftcord.social.party;

import java.util.*;

public class PartyManager {
    private static final Map<UUID, Party> parties = new HashMap<>();
    private static final Map<UUID, UUID> playerParties = new HashMap<>();

    public static Party createParty(UUID leader) {
        if (isInParty(leader)) {
            return null;
        }
        
        Party party = new Party(leader);
        parties.put(party.getPartyId(), party);
        playerParties.put(leader, party.getPartyId());
        
        return party;
    }

    public static boolean invitePlayer(UUID partyId, UUID inviter, UUID invited) {
        Party party = parties.get(partyId);
        if (party == null || !party.isLeader(inviter)) {
            return false;
        }
        
        // Send invitation through NetworkHandler
        NetworkHandler.sendPartyInvite(invited, partyId);
        return true;
    }

    public static boolean joinParty(UUID partyId, UUID player) {
        Party party = parties.get(partyId);
        if (party == null || isInParty(player)) {
            return false;
        }
        
        if (party.addMember(player)) {
            playerParties.put(player, partyId);
            return true;
        }
        return false;
    }

    public static void leaveParty(UUID player) {
        UUID partyId = playerParties.get(player);
        if (partyId == null) return;
        
        Party party = parties.get(partyId);
        if (party != null) {
            party.removeMember(player);
            if (party.getMembers().isEmpty()) {
                parties.remove(partyId);
            }
        }
        playerParties.remove(player);
    }

    public static boolean isInParty(UUID player) {
        return playerParties.containsKey(player);
    }
} 