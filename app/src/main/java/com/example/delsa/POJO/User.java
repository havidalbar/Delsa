package com.example.delsa.POJO;

public class User {
    private String nama, noTelephone, kota, email;

    public User(String nama, String noTelephone, String kota, String email) {
        this.nama = nama;
        this.noTelephone = noTelephone;
        this.kota = kota;
        this.email = email;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNoTelephone() {
        return noTelephone;
    }

    public void setNoTelephone(String noTelephone) {
        this.noTelephone = noTelephone;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
