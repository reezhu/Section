package org.xjcraft.section;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.xjcraft.CommonPlugin;
import org.xjcraft.section.config.Config;
import org.xjcraft.section.listener.CommandListener;
import org.xjcraft.section.listener.MessageListener;

import java.io.IOException;

public final class Section extends CommonPlugin implements PluginMessageListener {
    MessageListener listener;

    @Override
    public void onEnable() {
        // Plugin startup logic
        loadConfigs();

        listener = new MessageListener(this);
        getServer().getPluginManager().registerEvents(listener, this);
        getServer().getPluginManager().registerEvents(new CommandListener(this, listener), this);
        registerCommand(listener);
        getCommand("section").setTabCompleter(listener);
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subchannel = in.readUTF();
        if (subchannel.equals("PlayerCount")) {
            String server = in.readUTF();
            int playercount = in.readInt();
            if (playercount > 0) {
                listener.sendMessage(player, server);
            } else if (Config.config.getWhitelist().containsKey(server)) {
                try {
                    Runtime.getRuntime().exec(new String[]{"bash", "-c", Config.config.getWhitelist().get(server)});
                    player.sendMessage(Config.config.getStarting());
                    getServer().getScheduler().runTaskLater(this, () -> listener.sendMessage(player, server), 20 * Config.config.getJoinDelay());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }
}
