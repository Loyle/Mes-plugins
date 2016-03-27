package com.gmail.loyle.shootcraft.libraries;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_9_R1.IChatBaseComponent;
import net.minecraft.server.v1_9_R1.PacketPlayOutChat;
import net.minecraft.server.v1_9_R1.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_9_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_9_R1.PlayerConnection;

public class NmsUtils {
	
	/*
	 * Only work for 1_9_R1 version.. 
	 */
	
	public static void sendTitle(Player p, String title, String subtitle, int fadein, int stay, int fadeout) {
		PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;

		IChatBaseComponent titleComponent = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}");
		IChatBaseComponent subtitleComponent = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");

		PacketPlayOutTitle titletimes = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, titleComponent,
				fadein, stay, fadeout);
		PacketPlayOutTitle subtitletimes = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES,
				subtitleComponent);

		PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE,
				titleComponent);
		PacketPlayOutTitle subtitelPacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE,
				subtitleComponent);

		connection.sendPacket(titletimes);
		connection.sendPacket(subtitletimes);
		connection.sendPacket(titlePacket);
		connection.sendPacket(subtitelPacket);
	}

	public static void sendTitleToAllPlayer(String title, String subtitle, int fadein, int stay, int fadeout) {

		IChatBaseComponent titleComponent = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}");
		IChatBaseComponent subtitleComponent = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");

		PacketPlayOutTitle titletimes = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, titleComponent,
				fadein, stay, fadeout);
		PacketPlayOutTitle subtitletimes = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES,
				subtitleComponent);

		PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE,
				titleComponent);
		PacketPlayOutTitle subtitelPacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE,
				subtitleComponent);

		for (Player pls : Bukkit.getOnlinePlayers()) {
			PlayerConnection connection = ((CraftPlayer) pls).getHandle().playerConnection;
			connection.sendPacket(titletimes);
			connection.sendPacket(subtitletimes);
			connection.sendPacket(titlePacket);
			connection.sendPacket(subtitelPacket);
		}
	}

	public static void sendActionBar(Player p, String msg) {

		IChatBaseComponent i = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + msg + "\"}");
		PacketPlayOutChat packet = new PacketPlayOutChat(i, (byte) 2);

		((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
	}

	public static  void sendHeaderAndFooter(Player p, String header, String footer) {
		
		IChatBaseComponent h = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + header + "\"}");
		IChatBaseComponent f = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + footer + "\"}");
		PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
		try {
			Field headerField = packet.getClass().getDeclaredField("a");
			headerField.setAccessible(true);
			headerField.set(packet, h);
			headerField.setAccessible(!headerField.isAccessible());

			Field footerField = packet.getClass().getDeclaredField("b");
			footerField.setAccessible(true);
			footerField.set(packet, f);
			footerField.setAccessible(!footerField.isAccessible());
		} catch (Exception e) {
			e.printStackTrace();
		}
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
	}

}
