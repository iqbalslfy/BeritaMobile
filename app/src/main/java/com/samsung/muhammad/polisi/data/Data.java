package com.samsung.muhammad.polisi.data;

/**
 * Created by SONY on 06/09/2017.
 */

public class Data {
    private String id,kriteria;

    public Data() {

    }

    public Data(String id, String kriteria) {
        this.id = id;
        this.kriteria = kriteria;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKriteria() {
        return kriteria;
    }

    public void setKriteria(String kriteria) {
        this.kriteria = kriteria;
    }
}
