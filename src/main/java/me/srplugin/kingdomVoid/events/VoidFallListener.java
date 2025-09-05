package me.srplugin.kingdomVoid.events;

import java.util.HashSet;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class VoidFallListener implements Listener {
    private final JavaPlugin plugin;
    private final Location teleportLocation;
    private final String triggerWorld;
    private final int minBlockY;
    private final String teleportMessage;
    private final Sound teleportSound;
    private final Particle teleportParticle;
    private final Set<Player> teleportingPlayers = new HashSet();

    public VoidFallListener(JavaPlugin plugin, Location teleportLocation, String triggerWorld, int minBlockY, String teleportMessage, Sound teleportSound, Particle teleportParticle) {
        this.plugin = plugin;
        this.teleportLocation = teleportLocation;
        this.triggerWorld = triggerWorld;
        this.minBlockY = minBlockY;
        this.teleportMessage = teleportMessage;
        this.teleportSound = teleportSound;
        this.teleportParticle = teleportParticle;
    }

    @EventHandler
    public void onVoidDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            if (event.getCause() == DamageCause.VOID) {
                Player player = (Player)event.getEntity();
                if (player.getWorld().getName().equalsIgnoreCase(this.triggerWorld)) {
                    event.setCancelled(true);
                    if (player.getLocation().getY() <= (double)this.minBlockY && !this.teleportingPlayers.contains(player)) {
                        this.teleportPlayer(player);
                    }

                }
            }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (player.getWorld().getName().equalsIgnoreCase(this.triggerWorld)) {
            if (player.getLocation().getY() <= (double)this.minBlockY && !this.teleportingPlayers.contains(player)) {
                this.teleportPlayer(player);
            }

        }
    }

    private void teleportPlayer(Player player) {
        this.teleportingPlayers.add(player);
        boolean wasAllowFlight = player.getAllowFlight();
        boolean wasFlying = player.isFlying();
        Location safeLocation = this.teleportLocation.clone();
        World world = safeLocation.getWorld();
        if (world != null && !world.getBlockAt(safeLocation).isPassable()) {
            safeLocation.add(0.0D, 1.0D, 0.0D);
        }

        Bukkit.getScheduler().runTask(this.plugin, () -> {
            this.applyTeleport(player, safeLocation, wasAllowFlight, wasFlying);
        });
        Bukkit.getScheduler().runTaskLater(this.plugin, () -> {
            this.teleportingPlayers.remove(player);
        }, 2L);
    }

    private void applyTeleport(Player player, Location location, boolean wasAllowFlight, boolean wasFlying) {
        player.setVelocity(new Vector(0, 0, 0));
        player.setFallDistance(0.0F);
        player.teleport(location);
        player.setAllowFlight(wasAllowFlight);
        player.setFlying(wasAllowFlight && wasFlying);
        if (this.teleportMessage != null && !this.teleportMessage.isEmpty()) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.teleportMessage));
        }

        if (this.teleportSound != null) {
            player.playSound(location, this.teleportSound, 1.0F, 1.0F);
        }

        if (this.teleportParticle != null) {
            player.getWorld().spawnParticle(this.teleportParticle, location, 50, 0.5D, 1.0D, 0.5D, 0.05D);
        }

    }
}