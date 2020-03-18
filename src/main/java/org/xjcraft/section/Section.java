package org.xjcraft.section;

import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.xjcraft.CommonPlugin;
import org.xjcraft.section.listener.MessageListener;

public final class Section extends CommonPlugin implements PluginMessageListener {
    MessageListener listener;
    @Override
    public void onEnable() {
        // Plugin startup logic
        loadConfigs();

        listener = new MessageListener(this);
        getServer().getPluginManager().registerEvents(listener, this);
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
//        if (!channel.equals("BungeeCord")) {
//            return;
//        }
//        ByteArrayDataInput in = ByteStreams.newDataInput(message);
//        String subchannel = in.readUTF();
//        if (subchannel.equals("SomeSubChannel")) {
//            // Use the code sample in the 'Response' sections below to read
//            // the data.
//        }

    }
}
