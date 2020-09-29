package ar.com.fennoma.paymentezsdk.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Menu {

    private Long menuId;
    private List<Category> categories;

    public static Menu fromJSONObject(JSONObject json) {
        Menu menu = new Menu();
        if(json != null) {
            try {
                if(json.has("menu_id")) {
                    menu.setMenuId(json.getLong("menu_id"));
                }
                if(json.has("categories")) {
                    menu.setCategories(Category.fromJSONArray(json.getJSONArray("categories")));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return menu;
    }

    /*


     */

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
