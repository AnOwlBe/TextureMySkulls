package owlbe.textureMySkulls;

import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.item.ItemStack;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.server.*;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import static owlbe.textureMySkulls.TextureMySkulls.instance;
import static owlbe.textureMySkulls.util.MiniMessageUtil.format;
import static owlbe.textureMySkulls.util.MiniMessageUtil.needsFormatting;
import static owlbe.textureMySkulls.util.PacketEventsUtil.applyItemFormat;

public final class Listener implements PacketListener {

	@Override
	public void onPacketSend(@NotNull PacketSendEvent event) {
		switch (event.getPacketType()) {
			case PacketType.Play.Server.SYSTEM_CHAT_MESSAGE -> {
				WrapperPlayServerSystemChatMessage packet = new WrapperPlayServerSystemChatMessage(event);
				Component message = packet.getMessage();
				if (needsFormatting(message))
					packet.setMessage(format(message));
			}
			case PacketType.Play.Server.ACTION_BAR -> {
				WrapperPlayServerActionBar packet = new WrapperPlayServerActionBar(event);
				Component text = packet.getActionBarText();
				if (needsFormatting(text) && instance.getConfig().getBoolean("listeners.action_bar"))
					packet.setActionBarText(format(text));
			}
			case PacketType.Play.Server.BOSS_BAR -> {
				WrapperPlayServerBossBar packet = new WrapperPlayServerBossBar(event);
				Component title = packet.getTitle();
				if (needsFormatting(title) && instance.getConfig().getBoolean("listeners.boss_bar.title"))
					packet.setTitle(format(title));
			}

			case PacketType.Play.Server.PLAYER_LIST_HEADER_AND_FOOTER -> {
				WrapperPlayServerPlayerListHeaderAndFooter packet = new WrapperPlayServerPlayerListHeaderAndFooter(event);
				Component header = packet.getHeader();
				Component footer = packet.getFooter();
				if (needsFormatting(header) && instance.getConfig().getBoolean("listeners.tab.header"))
					packet.setHeader(format(header));
				if (needsFormatting(footer) && instance.getConfig().getBoolean("listeners.tab.footer"))
					packet.setFooter(format(footer));
			}
			case PacketType.Play.Server.PLAYER_INFO_UPDATE -> {
				WrapperPlayServerPlayerInfoUpdate packet = new WrapperPlayServerPlayerInfoUpdate(event);
				for (var entry : packet.getEntries()) {
					Component displayName = entry.getDisplayName();
					if (displayName != null) {
						if (needsFormatting(displayName) && instance.getConfig().getBoolean("listeners.tab.player_display_name")) {
							entry.setDisplayName(format(displayName));
						}
					}
				}
			}
			case PacketType.Play.Server.TEAMS -> {
				WrapperPlayServerTeams packet = new WrapperPlayServerTeams(event);
				packet.getTeamInfo().ifPresent(teamInfo -> {
					Component displayName = teamInfo.getDisplayName();
					Component prefix = teamInfo.getPrefix();
					Component suffix = teamInfo.getSuffix();
					if (needsFormatting(displayName) && instance.getConfig().getBoolean("listeners.team.display_name"))
						teamInfo.setDisplayName(format(displayName));
					if (needsFormatting(prefix) && instance.getConfig().getBoolean("listeners.team.prefix"))
						teamInfo.setPrefix(format(prefix));
					if (needsFormatting(suffix) && instance.getConfig().getBoolean("listeners.team.suffix"))
						teamInfo.setSuffix(format(suffix));

				});
			}
			case PacketType.Play.Server.TITLE -> {
				WrapperPlayServerTitle packet = new WrapperPlayServerTitle(event);
				Component title = packet.getTitle();
				Component subtitle = packet.getSubtitle();
				if (title != null && needsFormatting(title) && instance.getConfig().getBoolean("listeners.title.main_title"))
					packet.setTitle(format(title));
				if (subtitle != null && needsFormatting(subtitle) && instance.getConfig().getBoolean("listeners.title.subtitle"))
					packet.setSubtitle(format(subtitle));
			}
			case PacketType.Play.Server.SCOREBOARD_OBJECTIVE -> {
				WrapperPlayServerScoreboardObjective packet = new WrapperPlayServerScoreboardObjective(event);
				Component displayName = packet.getDisplayName();
				if (needsFormatting(displayName) && instance.getConfig().getBoolean("listeners.scoreboard.title"))
					packet.setDisplayName(format(displayName));
			}
			case PacketType.Play.Server.OPEN_WINDOW -> {
				WrapperPlayServerOpenWindow packet = new WrapperPlayServerOpenWindow(event);
				Component title = packet.getTitle();
				if (needsFormatting(title) && instance.getConfig().getBoolean("listeners.inventory.title"))
					packet.setTitle(format(title));
			}
			case PacketType.Play.Server.DISCONNECT -> {
				WrapperPlayServerDisconnect packet = new WrapperPlayServerDisconnect(event);
				Component reason = packet.getReason();
				if (needsFormatting(reason) && instance.getConfig().getBoolean("listeners.disconnect.message"))
					packet.setReason(format(reason));
			}
			case PacketType.Play.Server.RESOURCE_PACK_SEND -> {
				WrapperPlayServerResourcePackSend packet = new WrapperPlayServerResourcePackSend(event);
				Component prompt = packet.getPrompt();
				if (needsFormatting(prompt) && instance.getConfig().getBoolean("listeners.resource_pack.prompt"))
					packet.setPrompt(format(prompt));
			}
			case PacketType.Play.Server.SET_SLOT -> {
				WrapperPlayServerSetSlot packet = new WrapperPlayServerSetSlot(event);
				applyItemFormat(packet.getItem());
			}
			case PacketType.Play.Server.WINDOW_ITEMS -> {
				WrapperPlayServerWindowItems packet = new WrapperPlayServerWindowItems(event);
				for (ItemStack item : packet.getItems()) {
					applyItemFormat(item);
				}
			}
			default -> {}
		}
	}

}
