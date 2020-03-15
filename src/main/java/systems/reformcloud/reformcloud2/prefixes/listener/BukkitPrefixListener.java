package systems.reformcloud.reformcloud2.prefixes.listener;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import systems.reformcloud.reformcloud2.prefixes.ReformCloud2Prefixes;

public final class BukkitPrefixListener implements Listener {

    @EventHandler
    public void handle(final PlayerJoinEvent event) {
        ReformCloudPrefixListener.initPrefix(event.getPlayer());
    }

    @EventHandler
    public void handle(final AsyncPlayerChatEvent event) {
        String message = event.getMessage().replace("%", "%%");
        if (event.getPlayer().hasPermission("reformcloud.chat.color")) {
            message = ChatColor.translateAlternateColorCodes('&', message);
        }

        event.setFormat(String.format(ReformCloud2Prefixes.getInstance().getChatFormat(), event.getPlayer().getDisplayName(), message));
    }
}