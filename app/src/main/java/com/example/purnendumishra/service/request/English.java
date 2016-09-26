
package com.example.purnendumishra.service.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class English {

    @SerializedName("lang_real")
    @Expose
    private String langReal;
    @SerializedName("lang_real_eng")
    @Expose
    private String langRealEng;


    @SerializedName("lang_in_english")
    @Expose
    private String langEng;

    public String getLangEng() {
        return langEng;
    }

    public void setLangEng(String langEng) {
        this.langEng = langEng;
    }
    /**
     * 
     * @return
     *     The langReal
     */
    public String getLangReal() {
        return langReal;
    }

    /**
     * 
     * @param langReal
     *     The lang_real
     */
    public void setLangReal(String langReal) {
        this.langReal = langReal;
    }

    /**
     * 
     * @return
     *     The langRealEng
     */
    public String getLangRealEng() {
        return langRealEng;
    }

    /**
     * 
     * @param langRealEng
     *     The lang_real_eng
     */
    public void setLangRealEng(String langRealEng) {
        this.langRealEng = langRealEng;
    }

}
