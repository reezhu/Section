package org.xjcraft.section.listener;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.server.TabCompleteEvent;
import org.bukkit.plugin.Plugin;
import org.xjcraft.annotation.RCommand;
import org.xjcraft.api.CommonCommandExecutor;
import org.xjcraft.section.Section;
import org.xjcraft.section.config.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MessageListener implements CommonCommandExecutor, TabCompleter {
    private Section plugin;

    public MessageListener(Section plugin) {
        this.plugin = plugin;
    }


    public void sendMessage(Player player, String server) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(server);
        player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
    }

    @RCommand(value = "join",sender = RCommand.Sender.PLAYER, desc = "加入某个坑")
    public void join(CommandSender player, String server) {
        join(server, (Player) player);
    }

    @RCommand(value = "join", defaultUser = RCommand.Permisson.OP, desc = "加入某个坑")
    public void join(String server, Player player) {
        Plugin plugin = this.plugin.getServer().getPluginManager().getPlugin("XJCraftAudit");
        if (plugin == null) {
            player.sendMessage("你需要回到主世界才能使用这个指令！");
            return;
        }
        org.jim.bukkit.audit.PlayerMeta playerMeta = ((org.jim.bukkit.audit.AuditPlugin) plugin).getHelper().getPlayerMeta(player);
        if (playerMeta.getStatus() == org.jim.bukkit.audit.Status.APPLIED_VILLAGE_BASE) {
            sendMessage(player, server);
        } else {
            player.sendMessage("通过考核之前你无法使用此指令！");
        }
    }


    @RCommand(value = "exit", sender = RCommand.Sender.PLAYER, desc = "回到主世界")
    public void quit(CommandSender player) {
        quit((Player) player);
    }

    @RCommand(value = "exit", defaultUser = RCommand.Permisson.OP, desc = "回到主世界")
    public void quit(Player player) {
        sendMessage(player, "main");
    }



    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length==1){
            return new ArrayList<String>(){{
                add("join");
                add("exit");
            }};
        }else if (args.length==2){
            switch (args[0]){
                case "join":
                    return Config.config.getWhitelist();
                default:
                    break;
            }
        }
        return null;
    }
}
