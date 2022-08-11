package tobProfitTracker;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.util.ImageUtil;

import java.awt.image.BufferedImage;

@Slf4j
@PluginDescriptor(
	name = "TOB Profit Tracker"
)
public class TobProfitTrackerPlugin extends Plugin
{
	private static final BufferedImage ICON = ImageUtil.loadImageResource(TobProfitTrackerPlugin.class, "panel_icon.png");
	@Inject
	private Client client;

	@Inject
	private TobProfitTrackerConfig config;
	@Inject
	private ClientToolbar clientToolBar;

	@Override
	protected void startUp() throws Exception
	{
		TobProfitTrackerPanel panel = new TobProfitTrackerPanel();
		NavigationButton navButton = NavigationButton.builder()
				.tooltip("TOB Profits")
		.icon(ICON)
		.priority(5)
		.panel(panel)
		.build();

		clientToolBar.addNavigation(navButton);
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info("Example stopped!");
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Example says " + config.greeting(), null);
		}
	}

	@Provides
	TobProfitTrackerConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(TobProfitTrackerConfig.class);
	}
}
