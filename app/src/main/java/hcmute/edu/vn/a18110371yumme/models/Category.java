package hcmute.edu.vn.a18110371yumme.models;

public class Category {
    private String CategoryName;
    private int CategoryDecription;

    public Category(String mCategoryName, int mCategoryDecription) {
        this.CategoryName = mCategoryName;
        this.CategoryDecription = mCategoryDecription;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public int getCategoryDrawable() {
        return CategoryDecription;
    }
}
