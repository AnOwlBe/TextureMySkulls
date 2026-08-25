package owlbe.textureMySkulls.util;

import com.github.retrooper.packetevents.protocol.component.ComponentTypes;
import com.github.retrooper.packetevents.protocol.component.builtin.item.ItemLore;
import com.github.retrooper.packetevents.protocol.item.ItemStack;
import net.kyori.adventure.text.Component;

import java.util.List;

import static owlbe.textureMySkulls.TextureMySkulls.instance;
import static owlbe.textureMySkulls.util.MiniMessageUtil.format;
import static owlbe.textureMySkulls.util.MiniMessageUtil.needsFormatting;

public final class PacketEventsUtil {

	public static void applyItemFormat(ItemStack item) {
		Component name = item.getComponentOr(ComponentTypes.CUSTOM_NAME, Component.empty());
		if (needsFormatting(name) && instance.getConfig().getBoolean("listeners.inventory.item_name")) {
			item.setComponent(ComponentTypes.CUSTOM_NAME, format(name));
		}

		if (item.hasComponent(ComponentTypes.LORE) && instance.getConfig().getBoolean("listeners.inventory.item_lore")) {
			List<Component> lines = item.getComponentOr(ComponentTypes.LORE, new ItemLore(List.of())).getLines();
			if (lines.stream().anyMatch(MiniMessageUtil::needsFormatting)) {
				List<Component> formatted = lines.stream()
						.map(line -> needsFormatting(line) ? format(line) : line)
						.toList();
				item.setComponent(ComponentTypes.LORE, new ItemLore(formatted));
			}
		}
	}
}
