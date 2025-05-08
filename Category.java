
import java.util.List;

public class Category {
    private int categoryId;
    private String categoryName;
    private static String[] categories = {"Cleaning", "Facility", "Academic", "Transport"};

    public Category(int categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public int getCategoryId() { return categoryId; }
    public String getCategoryName() { return categoryName; }

    public static String[] getAvailableCategories() {
        return categories;
    }
}
