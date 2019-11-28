package com.example.delsa.POJO;

import android.os.Parcel;
import android.os.Parcelable;

public class Bencana implements Parcelable {

    private String kategori, judul, alamat, deskripsi, fotoBencana, tanggalLapor;
    private boolean status;

    public Bencana() {
    }

    public Bencana(String kategori, String judul, String alamat, String deskripsi, String fotoBencana, String tanggalLapor, boolean status) {
        this.kategori = kategori;
        this.judul = judul;
        this.alamat = alamat;
        this.deskripsi = deskripsi;
        this.fotoBencana = fotoBencana;
        this.tanggalLapor = tanggalLapor;
        this.status = status;
    }

    public String getKategori() {
        return kategori;
    }

    public String getJudul() {
        return judul;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getFotoBencana() {
        return fotoBencana;
    }

    public String getTanggalLapor() {
        return tanggalLapor;
    }

    public boolean isStatus() {
        return status;
    }

    public static Creator<Bencana> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.kategori);
        dest.writeString(this.judul);
        dest.writeString(this.alamat);
        dest.writeString(this.deskripsi);
        dest.writeString(this.fotoBencana);
        dest.writeString(this.tanggalLapor);
        dest.writeByte(this.status ? (byte) 1 : (byte) 0);
    }

    protected Bencana(Parcel in) {
        this.kategori = in.readString();
        this.judul = in.readString();
        this.alamat = in.readString();
        this.deskripsi = in.readString();
        this.fotoBencana = in.readString();
        this.tanggalLapor = in.readString();
        this.status = in.readByte() != 0;
    }

    public static final Creator<Bencana> CREATOR = new Creator<Bencana>() {
        @Override
        public Bencana createFromParcel(Parcel source) {
            return new Bencana(source);
        }

        @Override
        public Bencana[] newArray(int size) {
            return new Bencana[size];
        }
    };
}
