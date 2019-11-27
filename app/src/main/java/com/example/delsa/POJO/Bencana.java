package com.example.delsa.POJO;

public class Bencana {

    private String kategori, judul, alamat, deskripsi, fotoBencana;

    public Bencana() {
    }

    public Bencana(String kategori, String judul, String alamat, String deskripsi, String fotoBencana) {
        this.kategori = kategori;
        this.judul = judul;
        this.alamat = alamat;
        this.deskripsi = deskripsi;
        this.fotoBencana = fotoBencana;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getFotoBencana() {
        return fotoBencana;
    }

    public void setFotoBencana(String fotoBencana) {
        this.fotoBencana = fotoBencana;
    }
}
