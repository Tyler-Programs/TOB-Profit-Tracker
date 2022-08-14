package tobProfitTracker;

import com.google.inject.Inject;
import lombok.AccessLevel;
import lombok.Getter;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.FontManager;
import net.runelite.client.util.AsyncBufferedImage;
import net.runelite.client.util.Text;
import net.runelite.http.api.item.ItemPrice;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TobProfitTrackerBox extends JPanel {
    private static final int ITEMS_PER_ROW = 5;
    private static final int TITLE_PADDING = 5;

    private final JPanel itemContainer = new JPanel();
    private final JLabel profitLabel = new JLabel();
    @Getter(AccessLevel.PACKAGE)
    private final String id;
    @Inject
    private ItemManager itemManager;
    private final List<SupplyItem> trackedItems = new ArrayList<>();

    public TobProfitTrackerBox(String id) {
        this.id = id;

        render();
    }

    private void render() {
        setLayout(new BorderLayout(0, 1));
        setBorder(new EmptyBorder(5, 0, 0, 0));

        final JPanel logTitle = new JPanel(new BorderLayout(5, 0));
        logTitle.setBorder(new EmptyBorder(7, 7, 7, 7));
        logTitle.setBackground(ColorScheme.DARKER_GRAY_COLOR.darker());

        final JLabel titleLabel = new JLabel(Text.removeTags(id));
        titleLabel.setFont(FontManager.getRunescapeSmallFont());
        titleLabel.setForeground(Color.WHITE);
        // TODO: Replace with ToB <kc> / ToB HM <kc>
        titleLabel.setText("ToB ");

        logTitle.add(titleLabel, BorderLayout.WEST);

        add(logTitle, BorderLayout.NORTH);
        add(itemContainer, BorderLayout.CENTER);

        // Create popup menu
        final JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.setBorder(new EmptyBorder(5, 5, 5, 5));
        setComponentPopupMenu(popupMenu);

        buildItems();
    }

    void buildItems() {
        trackedItems.sort((i1, i2) -> i2.getName().compareTo(i1.getName()));

        for (SupplyItem item : trackedItems) {
            // TODO: Totalling values here
        }
        
        // calculates how many rows need to be displayed to fit all item
        final int rowSize = ((trackedItems.size() % ITEMS_PER_ROW == 0) ? 0 : 1) + trackedItems.size() / ITEMS_PER_ROW;

        itemContainer.removeAll();
        itemContainer.setLayout(new GridLayout(rowSize, ITEMS_PER_ROW, 1, 1));

        for (int i = 0; i < rowSize * ITEMS_PER_ROW; i++) {
            final JPanel slotContainer = new JPanel();
            slotContainer.setBackground(ColorScheme.DARKER_GRAY_COLOR);

            if (i < trackedItems.size()) {
                final SupplyItem item = trackedItems.get(i);
                final JLabel imageLabel = new JLabel();
                // TODO: Implement buildToolTip
                //imageLabel.setToolTipText(buildTooltip(getModifiedItemId(item.getName(), item.getId()), item.getQuantity(), item));
                imageLabel.setVerticalAlignment(SwingConstants.CENTER);
                imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

                AsyncBufferedImage itemImage = itemManager.getImage(getModifiedItemId(item.getName(), item.getId()), item.getQuantity(), item.getQuantity() > 1);

                itemImage.addTo(imageLabel);
                slotContainer.add(imageLabel);

                if (item.getName() == null || item.getId() == 0
                        || item.getName().equalsIgnoreCase("null")
                        || getModifiedItemId(item.getName(), item.getId()) == 0
                        || itemManager.getImage(getModifiedItemId(item.getName(), item.getId()), item.getQuantity(), item.getQuantity() > 1) == null) {
                    continue;
                }

                // create popup menu
                final JPopupMenu popupMenu = new JPopupMenu();
                popupMenu.setBorder(new EmptyBorder(5, 5, 5, 5));
                slotContainer.setComponentPopupMenu(popupMenu);
            }
            itemContainer.add(slotContainer);
        }
        itemContainer.repaint();
    }

    private int getModifiedItemId(String name, int itemId) {
        if (name.endsWith("(4)") ||
                name.endsWith("(3)") ||
                name.endsWith("(2)") ||
                name.endsWith("(1)")) {
            return getSingleDose(name);
        }

        return itemId;
    }

    /**
     * Turns a potion itemid into the single dose id
     *
     * @param name potion name to be checked
     * @return itemid of single dose potion
     */
    private int getSingleDose(String name) {
        String nameModified = name.replace("(4)", "(1)");
        nameModified = nameModified.replace("(3)", "(1)");
        nameModified = nameModified.replace("(2)", "(1)");


        List<ItemPrice> prices = itemManager.search(nameModified);
        if (!prices.isEmpty()) {
            return prices.get(0).getId();
        }
        return 0;
    }
}
