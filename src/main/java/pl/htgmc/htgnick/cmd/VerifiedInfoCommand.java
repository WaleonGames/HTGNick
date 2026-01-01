package pl.htgmc.htgnick.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public final class VerifiedInfoCommand implements CommandExecutor {

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

    sender.sendMessage("§8§m----------------------------------------");
    sender.sendMessage("§b§lAstraCraft.pl §8» §7Weryfikacja");
    sender.sendMessage(" ");
    sender.sendMessage("§7Zweryfikowany §8= §aoznaczenie ✔ §7przy nicku.");
    sender.sendMessage("§7Daje: §fwyróżnienie §7w TAB/tagu/czacie.");
    sender.sendMessage("§7Jak zdobyć: §fnapisz do administracji na §bDiscordzie§f.");
    sender.sendMessage("§8§m----------------------------------------");

    return true;
  }
}
