package tobProfitTracker;

import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.PluginPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TobProfitTrackerPanel extends PluginPanel {
    private final List<TobProfitTrackerBox> boxes = new ArrayList<>();
    private final JPanel totalProfitPanel = buildTotalProfitPanel();

    TobProfitTrackerPanel(final TobProfitTrackerPlugin plugin, final TobProfitTrackerConfig config)
    {
        setBorder(new EmptyBorder(6, 6, 6, 6));
        setBackground(ColorScheme.DARK_GRAY_COLOR);
        setLayout(new BorderLayout());

        // Create layout panel for wrapping
        final JPanel layoutPanel = new JPanel();
        layoutPanel.setLayout(new BoxLayout(layoutPanel, BoxLayout.Y_AXIS));
        layoutPanel.add(totalProfitPanel);
        add(layoutPanel, BorderLayout.NORTH);
    }

    private JPanel buildTotalProfitPanel()
    {
        final JPanel totalProfitContainer = new JPanel();
        totalProfitContainer.setLayout(new BorderLayout());
        totalProfitContainer.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        totalProfitContainer.setPreferredSize(new Dimension(0, 30));
        totalProfitContainer.setBorder(new EmptyBorder(5, 5, 5, 10));
        totalProfitContainer.setVisible(true);

        final JPanel viewControls = new JPanel(new GridLayout(1, 3, 10, 0));
        viewControls.setBackground(ColorScheme.DARKER_GRAY_COLOR);

        final JPanel leftTitleContainer = new JPanel(new BorderLayout(5, 0));
        leftTitleContainer.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        leftTitleContainer.setVisible(true);

        totalProfitContainer.add(viewControls, BorderLayout.EAST);
        totalProfitContainer.add(leftTitleContainer, BorderLayout.WEST);

        return totalProfitContainer;
    }
}
