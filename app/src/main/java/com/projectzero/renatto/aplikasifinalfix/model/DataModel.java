package com.projectzero.renatto.aplikasifinalfix.model;

public class DataModel {
    private String id, nama, alamat, notelp, nofaskes, poly;

    public DataModel() {
    }

    public DataModel(String id, String nama, String alamat, String notelp, String nofaskes, String poly) {
        this.id = id;
        this.nama = nama;
        this.alamat = alamat;
        this.notelp = notelp;
        this.nofaskes = nofaskes;
        this.poly = poly;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
    public String getAlamat(){
        return alamat;
    }
    public void setAlamat(String alamat){
        this.alamat = alamat;
    }
    public String getNotelp(){
        return notelp;
    }
    public void setNotelp(String notelp){
        this.notelp = notelp;
    }
    public String getNofaskes(){
        return nofaskes;
    }
    public void setNofaskes(String nofaskes){
        this.notelp = nofaskes;
    }
    public String getPoly(){
        return poly;
    }
    public void setPoly(String poly){
        this.poly = poly;
    }
}