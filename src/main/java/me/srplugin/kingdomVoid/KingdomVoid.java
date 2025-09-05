package me.srplugin.kingdomVoid;

import me.srplugin.kingdomVoid.commands.KingdomVoidCommand;
import me.srplugin.kingdomVoid.events.VoidFallListener;
import me.srplugin.kingdomVoid.utils.TeleportConfigLoader;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.plugin.java.JavaPlugin;

public final class KingdomVoid extends JavaPlugin {

    public void onEnable() {

        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&eKingdomVoid&8] &a¡El plugin ha sido activado!"));
        this.saveDefaultConfig();

        TeleportConfigLoader loader = new TeleportConfigLoader(this);
        Location teleportLocation = loader.loadTeleportLocation();
        String triggerWorld = loader.getTriggerWorld();
        int minBlockY = loader.getMinBlockY();

        String teleportMessage = loader.getTeleportMessage();
        Sound teleportSound = loader.getTeleportSound();
        Particle teleportParticle = loader.getTeleportParticle();

        Bukkit.getPluginManager().registerEvents(new VoidFallListener(this, teleportLocation, triggerWorld, minBlockY, teleportMessage, teleportSound, teleportParticle), this);

        getCommand("kingdomvoid").setExecutor(new KingdomVoidCommand(this));
    }

    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&eKingdomVoid&8] &c¡El plugin ha sido desactivado!"));
    }
}