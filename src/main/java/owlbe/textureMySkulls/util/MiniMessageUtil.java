package owlbe.textureMySkulls.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.object.ObjectContents;
import net.kyori.adventure.text.object.PlayerHeadObjectContents;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public final class MiniMessageUtil {

	private static final Pattern SKULL_PATTERN = Pattern.compile("<skull_texture:([A-Za-z0-9+/=]+)>");

	/**
	 * Replaces all <skull_texture:base64> with the corresponding head sprite.
	 * @param component The component to add skull texture to
	 * @return The component after the skull texture has been added to it
	 */
	public static @NotNull Component format(Component component) {
		return component.replaceText(TextReplacementConfig.builder()
				.match(SKULL_PATTERN)
				.replacement((result, _) -> buildSkullComponent(result.group(1)))
				.build());
	}

	/**
	 * A utility to check if a component has a skull texture that needs to be formatted.
	 * @param component The component to check against
	 * @return Whether it needs to be formatted or not
	 */
	public static boolean needsFormatting(Component component) {
		if (component instanceof TextComponent textComponent && textComponent.content().contains("skull_texture:")) {
			return true;
		}
		for (Component child : component.children()) {
			if (needsFormatting(child)) {
				return true;
			}
		}
		return false;
	}

	private static Component buildSkullComponent(String texture) {
		PlayerHeadObjectContents contents = ObjectContents.playerHead()
				.profileProperty(PlayerHeadObjectContents.property("textures", texture))
				.build();
		return Component.object(contents);
	}

}
