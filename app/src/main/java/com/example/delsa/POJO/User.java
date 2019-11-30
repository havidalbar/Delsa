package com.example.delsa.POJO;

public class User {
    private String nama, noTelephone, kota, email, fotoIdentitas, fotoProfil;
    private boolean status;

    public User() {
    }

    public User(String nama, String noTelephone, String kota, String email, String fotoIdentitas, String fotoProfil, boolean status) {
        this.nama = nama;
        this.noTelephone = noTelephone;
        this.kota = kota;
        this.email = email;
        this.fotoIdentitas = fotoIdentitas;
        this.fotoProfil = fotoProfil;
        this.status = status;
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

    public String getFotoIdentitas() {
        return fotoIdentitas;
    }

    public void setFotoIdentitas(String fotoIdentitas) {
        this.fotoIdentitas = fotoIdentitas;
    }

    public String getFotoProfil() {
        return fotoProfil;
    }

    public void setFotoProfil(String fotoProfil) {
        this.fotoProfil = fotoProfil;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
