package tobProfitTracker;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import net.runelite.client.ui.components.DragAndDropReorderPane;

@RequiredArgsConstructor
public class TobProfitTrackerBox extends JPanel {
    private static final int ITEMS_PER_ROW = 5;
    private static final int TITLE_PADDING = 5;

    private final JPanel itemContainer = new JPanel();
    private final JLabel profitLabel = new JLabel();
    @Getter(AccessLevel.PACKAGE)
    private final String id;

    @PostConstruct
    void init() {
        setLayout(new BorderLayout(0, 1));
        setBorder(new EmptyBorder(5, 0, 0, 0));
    }
}
