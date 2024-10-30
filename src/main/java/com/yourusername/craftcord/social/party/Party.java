package com.yourusername.craftcord.social.party;

import java.util.*;
import net.minecraft.server.network.ServerPlayerEntity;

public class Party {
    private final UUID partyId;
    private UUID leader;
    private final Set<UUID> members;
    private final Map<UUID, PartyRole> roles;
    private int maxSize;

    public Party(UUID leader) {
        this.partyId = UUID.randomUUID();
        this.leader = leader;
        this.members = new HashSet<>();
        this.roles = new HashMap<>();
        this.maxSize = 5;
        
        // Add leader
        addMember(leader);
        roles.put(leader, PartyRole.LEADER);
    }

    public boolean addMember(UUID player) {
        if (members.size() >= maxSize) return false;
        members.add(player);
        if (!roles.containsKey(player)) {
            roles.put(player, PartyRole.MEMBER);
        }
        return true;
    }

    public void removeMember(UUID player) {
        members.remove(player);
        roles.remove(player);
        
        // If leader leaves, promote someone else
        if (player.equals(leader) && !members.isEmpty()) {
            leader = members.iterator().next();
            roles.put(leader, PartyRole.LEADER);
        }
    }

    public boolean isLeader(UUID player) {
        return player.equals(leader);
    }

    public Set<UUID> getMembers() {
        return Collections.unmodifiableSet(members);
    }

    public PartyRole getRole(UUID player) {
        return roles.getOrDefault(player, PartyRole.NONE);
    }
} 