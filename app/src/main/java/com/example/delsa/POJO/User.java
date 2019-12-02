package com.example.delsa.POJO;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nama);
        dest.writeString(this.noTelephone);
        dest.writeString(this.kota);
        dest.writeString(this.email);
        dest.writeString(this.fotoIdentitas);
        dest.writeString(this.fotoProfil);
        dest.writeByte(this.status ? (byte) 1 : (byte) 0);
    }

    protected User(Parcel in) {
        this.nama = in.readString();
        this.noTelephone = in.readString();
        this.kota = in.readString();
        this.email = in.readString();
        this.fotoIdentitas = in.readString();
        this.fotoProfil = in.readString();
        this.status = in.readByte() != 0;
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
