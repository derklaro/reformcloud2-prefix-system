package systems.reformcloud.reformcloud2.prefixes;

import org.bukkit.entity.Player;
import systems.reformcloud.reformcloud2.permissions.PermissionAPI;
import systems.reformcloud.reformcloud2.permissions.util.group.NodeGroup;
import systems.reformcloud.reformcloud2.permissions.util.group.PermissionGroup;

public final class Utils {

    private Utils() {
        throw new UnsupportedOperationException();
    }

    public static PermissionGroup getHighestGroup(final Player player) {
        PermissionGroup highest = null;

        for (NodeGroup group : PermissionAPI.getInstance().getPermissionUtil().loadUser(player.getUniqueId()).getGroups()) {
            if (!group.isValid()) {
                continue;
            }

            PermissionGroup permissionGroup = PermissionAPI.getInstance().getPermissionUtil().getGroup(group.getGroupName());
            if (permissionGroup == null) {
                continue;
            }

            if (highest == null) {
                highest = permissionGroup;
            } else if (permissionGroup.getPriority() < highest.getPriority()) {
                highest = permissionGroup;
            }
        }

        return highest;
    }
}