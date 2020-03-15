package systems.reformcloud.reformcloud2.prefixes.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;
import systems.reformcloud.reformcloud2.executor.api.common.ExecutorAPI;
import systems.reformcloud.reformcloud2.executor.api.common.event.handler.Listener;
import systems.reformcloud.reformcloud2.permissions.util.events.user.PermissionUserDeleteEvent;
import systems.reformcloud.reformcloud2.permissions.util.events.user.PermissionUserUpdateEvent;
import systems.reformcloud.reformcloud2.permissions.util.group.PermissionGroup;
import systems.reformcloud.reformcloud2.prefixes.ReformCloud2Prefixes;
import systems.reformcloud.reformcloud2.prefixes.Utils;

import javax.annotation.Nonnull;

public final class ReformCloudPrefixListener {

    @Listener
    public void onUpdate(final PermissionUserUpdateEvent event) {
        final Player player = Bukkit.getPlayer(event.getPermissionUser().getUniqueID());
        if (player == null) {
            return;
        }

        initPrefix(player);
    }

    @Listener
    public void onDelete(final PermissionUserDeleteEvent event) {
        ExecutorAPI.getInstance().getSyncAPI().getPlayerSyncAPI().kickPlayer(event.getUuid(), "§7Ein §cFehler §7ist aufgetreten!");
    }

    static void initPrefix(final Player player) {
        Bukkit.getScheduler().runTaskLater(ReformCloud2Prefixes.getInstance(), () -> {
            PermissionGroup permissionGroup = Utils.getHighestGroup(player);
            if (permissionGroup == null) {
                return;
            }

            Bukkit.getOnlinePlayers().forEach(online -> {
                addTeam(player, online, permissionGroup);

                PermissionGroup onlinePermissionGroup = Utils.getHighestGroup(online);
                if (onlinePermissionGroup == null) {
                    return;
                }

                addTeam(online, player, onlinePermissionGroup);
            });
        }, 3L);
    }

    private static void addTeam(Player target, Player other, PermissionGroup group) {
        String teamName = group.getPriority() + group.getName();

        if (teamName.length() > 16) {
            teamName = teamName.substring(0, 16);
        }

        Team team = other.getScoreboard().getTeam(teamName);
        if (team == null) {
            team = other.getScoreboard().registerNewTeam(teamName);
        }

        final String teamPrefix = getGroupPrefix(group);

        team.setPrefix(teamPrefix);
        team.addEntry(target.getName());

        target.setDisplayName(teamPrefix + target.getName());
    }

    @Nonnull
    private static String getGroupPrefix(@Nonnull PermissionGroup group) {
        String prefix = ReformCloud2Prefixes.getInstance().getPrefixGetter().get(group.getName());
        return prefix == null ? "" : prefix.length() > 16 ? prefix.substring(0, 16) : prefix;
    }
}