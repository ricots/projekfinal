package com.projectzero.renatto.aplikasifinalfix.data;

public class DataRs {
    private String id, nama, alamat, notelp, image, nofaskes, poly, lat, lng;

    public DataRs() {
    }

    public DataRs(String id, String nama, String alamat, String notelp, String image, String nofaskes, String poly, String lat, String lng) {
        this.id = id;
        this.nama = nama;
        this.alamat = alamat;
        this.image = image;
        this.notelp = notelp;
        this.nofaskes = nofaskes;
        this.poly = poly;
        this.lat = lat;
        this.lng =lng;
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

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNotelp() {
        return notelp;
    }

    public void setNotelp(String notelp) {
        this.notelp = notelp;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNofaskes() {
        return nofaskes;
    }

    public void setNofaskes(String nofaskes) {
        this.nofaskes = nofaskes;
    }

    public String getPoly() {
        return poly;
    }

    public void setPoly(String poly) {
        this.poly = poly;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}