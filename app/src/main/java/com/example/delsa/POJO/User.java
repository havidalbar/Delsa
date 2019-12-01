package com.example.delsa.POJO;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String uid, nama, noTelephone, kota, email, fotoIdentitas, fotoProfil;
    private boolean status;

    public User() {
    }

    public User(String uid, String nama, String noTelephone, String kota, String email, String fotoIdentitas, String fotoProfil, boolean status) {
        this.uid = uid;
        this.nama = nama;
        this.noTelephone = noTelephone;
        this.kota = kota;
        this.email = email;
        this.fotoIdentitas = fotoIdentitas;
        this.fotoProfil = fotoProfil;
        this.status = status;
    }

    protected User(Parcel in) {
        uid = in.readString();
        nama = in.readString();
        noTelephone = in.readString();
        kota = in.readString();
        email = in.readString();
        fotoIdentitas = in.readString();
        fotoProfil = in.readString();
        status = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uid);
        dest.writeString(nama);
        dest.writeString(noTelephone);
        dest.writeString(kota);
        dest.writeString(email);
        dest.writeString(fotoIdentitas);
        dest.writeString(fotoProfil);
        dest.writeByte((byte) (status ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getUid() {
        return uid;
    }

    public String getNama() {
        return nama;
    }

    public String getNoTelephone() {
        return noTelephone;
    }

    public String getKota() {
        return kota;
    }

    public String getEmail() {
        return email;
    }

    public String getFotoIdentitas() {
        return fotoIdentitas;
    }

    public String getFotoProfil() {
        return fotoProfil;
    }

    public boolean isStatus() {
        return status;
    }


}
