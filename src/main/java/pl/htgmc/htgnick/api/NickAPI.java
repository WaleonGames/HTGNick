package pl.htgmc.htgnick.api;

import org.bukkit.entity.Player;
import pl.htgmc.htgnick.store.NickStore;

import java.util.UUID;

public final class NickAPI {

    private final NickStore store;

    public NickAPI(NickStore store) {
        this.store = store;
    }

    // =========================
    // DOSTĘP DO DANYCH
    // =========================

    public String getColorRaw(UUID uuid) {
        return store.getColorRaw(uuid);
    }

    public boolean isVerified(UUID uuid) {
        return store.isVerified(uuid);
    }

    // =========================
    // GOTOWE FORMATY
    // =========================

    /** Zwraca kolor + nick + reset (już z §) */
    public String getColoredName(Player player) {
        return store.getColoredName(player);
    }

    /** Prefix do TAB: ✔/✖ */
    public String getVerifiedTabPrefix(UUID uuid) {
        return isVerified(uuid) ? "§a✔ " : "§c✖ ";
    }

    /** Prefix do chatu/tagu: tylko ✔ dla verified, inaczej pusto */
    public String getVerifiedChatPrefix(UUID uuid) {
        return isVerified(uuid) ? "§a✔ " : "";
    }

    /** Najczęściej używane w HTGChat */
    public String getChatDisplayName(Player player) {
        UUID id = player.getUniqueId();
        return getVerifiedChatPrefix(id) + getColoredName(player);
    }
}
