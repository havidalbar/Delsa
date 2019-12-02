package com.example.delsa.POJO;

public class Donatur {

    private String nama, pesan, tanggaldonasi;

    public Donatur(){

    }

    public Donatur(String nama, String pesan, String tanggaldonasi) {
        this.nama = nama;
        this.pesan = pesan;
        this.tanggaldonasi = tanggaldonasi;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public String getTanggaldonasi() {
        return tanggaldonasi;
    }

    public void setTanggaldonasi(String tanggaldonasi) {
        this.tanggaldonasi = tanggaldonasi;
    }
}
