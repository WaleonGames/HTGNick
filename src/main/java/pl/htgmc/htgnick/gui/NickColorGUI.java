package pl.htgmc.htgnick.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.htgmc.htgnick.HTGNick;

import java.util.*;

public final class NickColorGUI implements Listener {

    private final HTGNick plugin;
    private final String TITLE = "§9Kolor nicku";
    private final int SIZE = 27;

    // slot -> raw color
    private final Map<Integer, String> slotToColor = new HashMap<>();

    public NickColorGUI(HTGNick plugin) {
        this.plugin = plugin;
        initMap();
    }

    private void initMap() {
        // 16 klasycznych kolorów Minecraft (&0 - &f) + usuwanie
        // Uwaga: nie wszystkie materiały barwione istnieją w każdym MC, ale w 1.18 są.
        add(10, Material.LIME_STAINED_GLASS_PANE, "§aZielony", "&a");
        add(11, Material.LIGHT_BLUE_STAINED_GLASS_PANE, "§bJasny niebieski", "&b");
        add(12, Material.YELLOW_STAINED_GLASS_PANE, "§eŻółty", "&e");
        add(13, Material.PINK_STAINED_GLASS_PANE, "§dRóżowy", "&d");
        add(14, Material.ORANGE_STAINED_GLASS_PANE, "§6Złoty", "&6");
        add(15, Material.RED_STAINED_GLASS_PANE, "§cCzerwony", "&c");
        add(16, Material.PURPLE_STAINED_GLASS_PANE, "§5Fiolet", "&5");

        add(19, Material.WHITE_STAINED_GLASS_PANE, "§fBiały", "&f");
        add(20, Material.GRAY_STAINED_GLASS_PANE, "§7Szary", "&7");
        add(21, Material.BLACK_STAINED_GLASS_PANE, "§0Czarny", "&0");
        add(22, Material.BLUE_STAINED_GLASS_PANE, "§9Niebieski", "&9");
        add(23, Material.GREEN_STAINED_GLASS_PANE, "§2Ciemno-zielony", "&2");

        // Przykładowy HEX (możesz dodać więcej)
        add(24, Material.CYAN_STAINED_GLASS_PANE, "§bHEX: #55FFFF", "#55FFFF");

        // Usuwanie
        add(26, Material.BARRIER, "§cUsuń kolor", "");
    }

    private void add(int slot, Material mat, String name, String rawColor) {
        slotToColor.put(slot, rawColor);
    }

    public void open(Player p) {
        Inventory inv = Bukkit.createInventory(p, SIZE, TITLE);

        // tło
        ItemStack bg = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta bgm = bg.getItemMeta();
        bgm.setDisplayName(" ");
        bg.setItemMeta(bgm);
        for (int i = 0; i < SIZE; i++) inv.setItem(i, bg);

        // itemy kolorów
        for (var e : slotToColor.entrySet()) {
            int slot = e.getKey();
            String raw = e.getValue();

            Material mat = switch (slot) {
                case 10 -> Material.LIME_STAINED_GLASS_PANE;
                case 11 -> Material.LIGHT_BLUE_STAINED_GLASS_PANE;
                case 12 -> Material.YELLOW_STAINED_GLASS_PANE;
                case 13 -> Material.PINK_STAINED_GLASS_PANE;
                case 14 -> Material.ORANGE_STAINED_GLASS_PANE;
                case 15 -> Material.RED_STAINED_GLASS_PANE;
                case 16 -> Material.PURPLE_STAINED_GLASS_PANE;
                case 19 -> Material.WHITE_STAINED_GLASS_PANE;
                case 20 -> Material.GRAY_STAINED_GLASS_PANE;
                case 21 -> Material.BLACK_STAINED_GLASS_PANE;
                case 22 -> Material.BLUE_STAINED_GLASS_PANE;
                case 23 -> Material.GREEN_STAINED_GLASS_PANE;
                case 24 -> Material.CYAN_STAINED_GLASS_PANE;
                case 26 -> Material.BARRIER;
                default -> Material.GRAY_STAINED_GLASS_PANE;
            };

            ItemStack it = new ItemStack(mat);
            ItemMeta meta = it.getItemMeta();

            String display;
            if (slot == 26) display = "§cUsuń kolor";
            else if (raw.startsWith("#")) display = "§bUstaw HEX: §f" + raw;
            else display = "§aUstaw kolor: §f" + raw;

            meta.setDisplayName(display);
            meta.setLore(List.of("§7Kliknij, aby ustawić."));
            it.setItemMeta(meta);

            inv.setItem(slot, it);
        }

        p.openInventory(inv);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player p)) return;
        if (e.getView().getTitle() == null || !e.getView().getTitle().equals(TITLE)) return;

        e.setCancelled(true);

        int slot = e.getRawSlot();
        if (slot < 0 || slot >= SIZE) return;
        if (!slotToColor.containsKey(slot)) return;

        String raw = slotToColor.get(slot);

        if (raw == null || raw.isBlank()) {
            plugin.getStore().clearColor(p.getUniqueId());
            p.sendMessage("§aUsunięto kolor nicku.");
        } else {
            plugin.getStore().setColorRaw(p.getUniqueId(), raw);
            p.sendMessage("§aUstawiono kolor nicku: §f" + raw);
        }

        plugin.applyTabName(p);
        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1.2f);
        p.closeInventory();
    }
}
