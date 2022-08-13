package tobProfitTracker;

import com.google.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.Item;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static net.runelite.client.RuneLite.RUNELITE_DIR;

public class DataHandler {
    private static final File SESSION_DIR = new File(RUNELITE_DIR, "tob-profit-tracker");
    private final Client client;

    /**
     * Map of supplies gained/lost.
     * Key: ItemId
     * Value: Units lost (e.g: 1 = 1 dose or 1 hard food)
     */
    private final Map<Integer, Integer> supplies = new HashMap<>();

    @Inject
    public DataHandler(Client client)
    {
        this.client = client;
        SESSION_DIR.mkdir();
    }

    /**
     * Adds an item to the {@link DataHandler#supplies supplies} map and rewrites session data. If the {@link Item#id itemId} is already present
     * then the quantity will be modified by {@code quantity}.
     * @param itemId The item ID.
     * @param quantity The quantity to add/remove.
     */
    public void addItem(int itemId, int quantity)
    {
        Map<Integer, Integer> map = supplies;
        map.put(itemId, map.getOrDefault(itemId, 0) + quantity);
        buildSessionFile(this.supplies);
    }

    /**
     * Removes an item from the {@link DataHandler#supplies supplies} map and rewrites session data.
     * @param itemId The item ID.
     */
    public void removeItem(int itemId)
    {
        this.supplies.remove(itemId);
        buildSessionFile(this.supplies);
    }

    /**
     * Removes all items from the {@link DataHandler#supplies supplies} map and rewrites session data.
     * @param itemId The item ID.
     */
    public void clearSupplies()
    {
        this.supplies.clear();
        buildSessionFile(this.supplies);
    }

    // TODO: This function and its name are both ugly af. Fix it at some point.
    private void buildSessionFile(Map<Integer, Integer> supplies)
    {
        try
        {
            File sessionFile = new File(RUNELITE_DIR + "/supplies-tracker/" + client.getAccountHash() + ".txt");

            if (!sessionFile.createNewFile())
            {
                sessionFile.delete();
                sessionFile.createNewFile();
            }

            try (FileWriter f = new FileWriter(sessionFile, true); BufferedWriter b = new BufferedWriter(f); PrintWriter p = new PrintWriter(b))
            {
                for (int id : supplies.keySet())
                {
                    p.println(id + ":" + supplies.get(id));
                }
            }
            catch (IOException i)
            {
                i.printStackTrace();
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void clearSession()
    {
        supplies.clear();
    }
}
