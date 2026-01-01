package pl.htgmc.htgnick.papi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.htgmc.htgnick.HTGNick;

public final class HTGNickExpansion extends PlaceholderExpansion {

    private final HTGNick plugin;

    public HTGNickExpansion(HTGNick plugin) {
        this.plugin = plugin;
    }

    @Override public @NotNull String getIdentifier() { return "htgnick"; }
    @Override public @NotNull String getAuthor() { return "HTGMC"; }
    @Override public @NotNull String getVersion() { return "1.0.0"; }

    @Override
    public String onPlaceholderRequest(Player p, @NotNull String params) {
        if (p == null) return "";

        boolean v = plugin.getStore().isVerified(p.getUniqueId());

        return switch (params.toLowerCase()) {
            case "name" -> plugin.getStore().getColoredName(p);                 // %htgnick_name%
            case "color" -> plugin.getStore().getColorRaw(p.getUniqueId());     // %htgnick_color%

            // surowe
            case "verified" -> v ? "true" : "false";                            // %htgnick_verified%

            // ✅ TAB: zawsze pokazuje stan (zweryfikowany / nie)
            case "verified_tab" -> v ? "§a✔ " : "§c✖ ";                           // %htgnick_verified_tab%

            // ✅ TAG/CHAT: tylko zweryfikowani (jak nie, to nic)
            case "verified_tag" -> v ? "§a✔ " : "";                              // %htgnick_verified_tag%

            default -> "";
        };
    }
}
