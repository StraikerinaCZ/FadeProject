package sk.m3ii0.fadeproject.code.bukkit.permissions;

import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;
import sk.m3ii0.fadeproject.code.bukkit.CodeBukkit;
import sk.m3ii0.fadeproject.code.shared.permissions.Group;
import sk.m3ii0.fadeproject.code.shared.permissions.PermissionWorth;
import sk.m3ii0.fadeproject.code.shared.user.User;

import java.util.Map;

public class Permissions {

    public static void syncPermissions(User user, Player player) {
        Group group = user.getGroup();
        for (PermissionAttachmentInfo info : player.getEffectivePermissions()) {
            if (info.getAttachment() == null) {
                continue;
            }
            player.removeAttachment(info.getAttachment());
        }
        if (group != null) {
            for (Map.Entry<String, PermissionWorth> var : group.getPermissions().entrySet()) {
                player.addAttachment(CodeBukkit.getInstance(), var.getKey(), var.getValue().isEnabled());
            }
        }
        player.updateCommands();
    }

}