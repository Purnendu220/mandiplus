
package com.example.purnendumishra.service.request;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Example {

    @SerializedName("selected_lang")
    @Expose
    private SelectedLang selectedLang;
    @SerializedName("rest_lang_list")
    @Expose
    private List<RestLangList> restLangList = new ArrayList<RestLangList>();
    @SerializedName("item_list")
    @Expose
    private List<ItemList> itemList = new ArrayList<ItemList>();

    /**
     * 
     * @return
     *     The selectedLang
     */
    public SelectedLang getSelectedLang() {
        return selectedLang;
    }

    /**
     * 
     * @param selectedLang
     *     The selected_lang
     */
    public void setSelectedLang(SelectedLang selectedLang) {
        this.selectedLang = selectedLang;
    }

    /**
     * 
     * @return
     *     The restLangList
     */
    public List<RestLangList> getRestLangList() {
        return restLangList;
    }

    /**
     * 
     * @param restLangList
     *     The rest_lang_list
     */
    public void setRestLangList(List<RestLangList> restLangList) {
        this.restLangList = restLangList;
    }

    /**
     * 
     * @return
     *     The itemList
     */
    public List<ItemList> getItemList() {
        return itemList;
    }

    /**
     * 
     * @param itemList
     *     The item_list
     */
    public void setItemList(List<ItemList> itemList) {
        this.itemList = itemList;
    }

}
