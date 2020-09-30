package ar.com.fennoma.paymentezsdk.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class PmzMenu {

    private Long menuId;
    private List<PmzCategory> categories;

    public static PmzMenu fromJSONObject(JSONObject json) {
        PmzMenu menu = new PmzMenu();
        if(json != null) {
            try {
                if(json.has("menu_id")) {
                    menu.setMenuId(json.getLong("menu_id"));
                }
                if(json.has("categories")) {
                    menu.setCategories(PmzCategory.fromJSONArray(json.getJSONArray("categories")));
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

    public List<PmzCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<PmzCategory> categories) {
        this.categories = categories;
    }
}
