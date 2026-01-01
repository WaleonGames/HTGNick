package pl.htgmc.htgnick.cmd;

import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import pl.htgmc.htgnick.HTGNick;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public final class NickCommand implements CommandExecutor, TabCompleter {

    private final HTGNick plugin;

    public NickCommand(HTGNick plugin) {
        this.plugin = plugin;
    }

    /* =========================
       COMMAND
    ========================= */

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // /nick (bez args) -> pokaż pomoc
        if (args.length == 0) {
            sender.sendMessage("§8/§bnick gui §7- wybór koloru nicku");
            sender.sendMessage("§8/§bnick verify <nick> <on|off> §7- verifi (admin)");
            return true;
        }

        String sub = args[0].toLowerCase(Locale.ROOT);

        // /nick gui
        if (sub.equals("gui")) {
            if (!(sender instanceof Player p)) {
                sender.sendMessage("Ta komenda jest tylko dla gracza.");
                return true;
            }
            if (!p.hasPermission("htgnick.use")) {
                p.sendMessage("§cNie masz uprawnień.");
                return true;
            }
            plugin.openGui(p);
            return true;
        }

        // /nick verify <nick> <on|off>
        if (sub.equals("verify")) {
            if (!sender.hasPermission("htgnick.verify.manage")) {
                sender.sendMessage("§cNie masz uprawnień (htgnick.verify.manage).");
                return true;
            }
            if (args.length < 3) {
                sender.sendMessage("§7Użycie: §f/nick verify <nick> <on|off>");
                return true;
            }

            Player target = Bukkit.getPlayerExact(args[1]);
            if (target == null) {
                sender.sendMessage("§cGracz offline lub nie istnieje.");
                return true;
            }

            boolean value;
            String mode = args[2].toLowerCase(Locale.ROOT);
            if (mode.equals("on") || mode.equals("true") || mode.equals("1")) value = true;
            else if (mode.equals("off") || mode.equals("false") || mode.equals("0")) value = false;
            else {
                sender.sendMessage("§cPodaj: on/off");
                return true;
            }

            plugin.getStore().setVerified(target.getUniqueId(), value);
            plugin.applyTabName(target);

            sender.sendMessage("§aUstawiono verifi dla §f" + target.getName() + "§a: §f" + (value ? "ON" : "OFF"));
            target.sendMessage("§7Verifi nicku: " + (value ? "§aWŁĄCZONE" : "§cWYŁĄCZONE"));
            return true;
        }

        // Nieznana subkomenda
        sender.sendMessage("§cNieznana opcja. Użyj: §f/nick gui §7lub §f/nick verify");
        return true;
    }

    /* =========================
       TAB COMPLETE
    ========================= */

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if (!command.getName().equalsIgnoreCase("nick")) return List.of();

        // /nick <...>
        if (args.length == 1) {
            List<String> base = new ArrayList<>();
            base.add("gui");
            if (sender.hasPermission("htgnick.verify.manage")) base.add("verify");
            return filterPrefix(base, args[0]);
        }

        // /nick verify <nick> <on|off>
        if (args.length == 2 && args[0].equalsIgnoreCase("verify")) {
            if (!sender.hasPermission("htgnick.verify.manage")) return List.of();
            return filterPrefix(
                    Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList()),
                    args[1]
            );
        }

        if (args.length == 3 && args[0].equalsIgnoreCase("verify")) {
            if (!sender.hasPermission("htgnick.verify.manage")) return List.of();
            return filterPrefix(List.of("on", "off"), args[2]);
        }

        return List.of();
    }

    private List<String> filterPrefix(List<String> list, String prefixRaw) {
        String prefix = (prefixRaw == null ? "" : prefixRaw).toLowerCase(Locale.ROOT);
        return list.stream()
                .filter(s -> s.toLowerCase(Locale.ROOT).startsWith(prefix))
                .collect(Collectors.toList());
    }
}
