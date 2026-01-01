package pl.htgmc.htgnick.store;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import pl.htgmc.htgnick.HTGNick;

import java.util.UUID;

public final class NickStore {

    private final HTGNick plugin;

    public NickStore(HTGNick plugin) {
        this.plugin = plugin;
    }

    public String getColorRaw(UUID uuid) {
        FileConfiguration cfg = plugin.getConfig();
        return cfg.getString("colors." + uuid.toString(), "");
    }

    public void setColorRaw(UUID uuid, String raw) {
        plugin.getConfig().set("colors." + uuid.toString(), raw);
        plugin.saveConfig();
    }

    public void clearColor(UUID uuid) {
        plugin.getConfig().set("colors." + uuid.toString(), null);
        plugin.saveConfig();
    }

    public String getColoredName(Player p) {
        String raw = getColorRaw(p.getUniqueId());
        String name = p.getName();
        if (raw == null || raw.isBlank()) return name;

        // HEX jako "#RRGGBB"
        if (raw.startsWith("#") && raw.length() == 7) {
            try {
                return ChatColor.of(raw) + name + ChatColor.RESET;
            } catch (Throwable ignored) {
                return name;
            }
        }

        // klasyczne &a &b &6 itd.
        String translated = org.bukkit.ChatColor.translateAlternateColorCodes('&', raw);
        return translated + name + org.bukkit.ChatColor.RESET;
    }

    public boolean isVerified(UUID uuid) {
        return plugin.getConfig().getBoolean("verified." + uuid.toString(), false);
    }

    public void setVerified(UUID uuid, boolean value) {
        plugin.getConfig().set("verified." + uuid.toString(), value);
        plugin.saveConfig();
    }
}
