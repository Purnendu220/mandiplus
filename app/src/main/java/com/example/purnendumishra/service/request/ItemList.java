
package com.example.purnendumishra.service.request;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemList {

    @SerializedName("hindi")
    @Expose
    private List<Hindi> hindi = new ArrayList<Hindi>();
    @SerializedName("english")
    @Expose
    private List<English> english = new ArrayList<English>();
    @SerializedName("gujarati")
    @Expose
    private List<Gujaratus> gujarati = new ArrayList<Gujaratus>();

    /**
     * 
     * @return
     *     The hindi
     */
    public List<Hindi> getHindi() {
        return hindi;
    }

    /**
     * 
     * @param hindi
     *     The hindi
     */
    public void setHindi(List<Hindi> hindi) {
        this.hindi = hindi;
    }

    /**
     * 
     * @return
     *     The english
     */
    public List<English> getEnglish() {
        return english;
    }

    /**
     * 
     * @param english
     *     The english
     */
    public void setEnglish(List<English> english) {
        this.english = english;
    }

    /**
     * 
     * @return
     *     The gujarati
     */
    public List<Gujaratus> getGujarati() {
        return gujarati;
    }

    /**
     * 
     * @param gujarati
     *     The gujarati
     */
    public void setGujarati(List<Gujaratus> gujarati) {
        this.gujarati = gujarati;
    }

}
