package me.srplugin.kingdomVoid.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class TeleportConfigLoader {
    private final JavaPlugin plugin;

    public TeleportConfigLoader(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public Location loadTeleportLocation() {
        FileConfiguration config = this.plugin.getConfig();
        String tpWorldName = config.getString("teleport.world", "Spawn");
        double x = config.getDouble("teleport.x", 0.5D);
        double y = config.getDouble("teleport.y", 130.0D);
        double z = config.getDouble("teleport.z", 0.5D);
        float yaw = (float)config.getDouble("teleport.yaw", 0.0D);
        float pitch = (float)config.getDouble("teleport.pitch", 0.0D);
        World tpWorld = Bukkit.getWorld(tpWorldName);
        if (tpWorld == null) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cEl mundo &e" + tpWorldName + " &cno existe. Se usará el primer mundo cargado."));
            tpWorld = (World)Bukkit.getWorlds().get(0);
        }

        return new Location(tpWorld, x, y, z, yaw, pitch);
    }

    public int getMinBlockY() {
        return this.plugin.getConfig().getInt("teleport.min-block", 5);
    }

    public String getTeleportMessage() {
        return this.plugin.getConfig().getString("teleport.message", "&cHas sido teletransportado al Spawn por caer al vacío");
    }

    public String getTriggerWorld() {
        return this.plugin.getConfig().getString("trigger-world", "Spawn");
    }

    public Sound getTeleportSound() {
        String soundName = this.plugin.getConfig().getString("teleport.sound", "ENTITY_ENDERMAN_TELEPORT");

        try {
            return Sound.valueOf(soundName.toUpperCase());
        } catch (IllegalArgumentException var3) {
            return Sound.ENTITY_ENDERMAN_TELEPORT;
        }
    }

    public Particle getTeleportParticle() {
        String particleName = this.plugin.getConfig().getString("teleport.particle", "DRAGON_BREATH");

        try {
            return Particle.valueOf(particleName.toUpperCase());
        } catch (IllegalArgumentException var3) {
            return Particle.DRAGON_BREATH;
        }
    }
}