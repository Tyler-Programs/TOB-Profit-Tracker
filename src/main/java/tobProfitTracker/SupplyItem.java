package tobProfitTracker;

import lombok.Data;

@Data
public class SupplyItem {
    private int id;
    private String name;
    private int quantity;
    // May use in the future
    private long price;
}
