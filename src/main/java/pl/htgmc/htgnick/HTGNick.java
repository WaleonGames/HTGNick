package pl.htgmc.htgnick;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import pl.htgmc.htgnick.cmd.VerifiedInfoCommand;
import pl.htgmc.htgnick.gui.NickColorGUI;
import pl.htgmc.htgnick.papi.HTGNickExpansion;
import pl.htgmc.htgnick.store.NickStore;

public final class HTGNick extends JavaPlugin {

    private NickStore store;
    private NickColorGUI gui;

    private boolean placeholderApiPresent;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        this.store = new NickStore(this);
        this.gui = new NickColorGUI(this);

        // komenda + tab
        var cmd = getCommand("nick");
        if (cmd != null) {
            var nc = new pl.htgmc.htgnick.cmd.NickCommand(this);
            cmd.setExecutor(nc);
            cmd.setTabCompleter(nc);
        }

        // ✅ /zweryfikowany (info)
        var vcmd = getCommand("zweryfikowany");
        if (vcmd != null) {
            vcmd.setExecutor(new VerifiedInfoCommand());
        }

        // listener GUI
        Bukkit.getPluginManager().registerEvents(gui, this);

        // PlaceholderAPI (sprawdzenie)
        this.placeholderApiPresent = isPlaceholderApiEnabled();
        if (placeholderApiPresent) {
            new HTGNickExpansion(this).register();
            getLogger().info("PlaceholderAPI wykryte: zarejestrowano placeholdery %htgnick_*%.");
        } else {
            getLogger().warning("Brak PlaceholderAPI - placeholdery %htgnick_*% NIE będą działać.");
            getLogger().warning("Zainstaluj PlaceholderAPI jeśli chcesz używać tego w TAB/chacie jako placeholder.");
        }

        // odśwież TAB po starcie (opcjonalnie)
        Bukkit.getOnlinePlayers().forEach(this::applyTabName);
    }

    private boolean isPlaceholderApiEnabled() {
        Plugin papi = Bukkit.getPluginManager().getPlugin("PlaceholderAPI");
        return papi != null && papi.isEnabled();
    }

    public boolean isPlaceholderApiPresent() {
        return placeholderApiPresent;
    }

    public NickStore getStore() {
        return store;
    }

    public void openGui(Player p) {
        gui.open(p);
    }

    public void applyTabName(Player p) {
        String colored = store.getColoredName(p);
        p.setPlayerListName(colored);
        p.setDisplayName(colored);
    }
}
