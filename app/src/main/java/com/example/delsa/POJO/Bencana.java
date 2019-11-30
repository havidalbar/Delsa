package com.example.delsa.POJO;

import android.os.Parcel;
import android.os.Parcelable;

public class Bencana implements Parcelable {

    private String idbencana, idUser, kategori, judul, alamat, deskripsi, fotoBencana, tanggalLapor,statusPengiriman,kota;
    private boolean status;

    public Bencana() {
    }

    public Bencana(String idbencana, String idUser, String kategori, String judul, String alamat, String deskripsi, String fotoBencana, String tanggalLapor, String statusPengiriman,boolean status,String kota) {
        this.idbencana = idbencana;
        this.idUser = idUser;
        this.kategori = kategori;
        this.judul = judul;
        this.alamat = alamat;
        this.kota = kota;
        this.deskripsi = deskripsi;
        this.fotoBencana = fotoBencana;
        this.tanggalLapor = tanggalLapor;
        this.statusPengiriman = statusPengiriman;
        this.status = status;
        this.kota = kota;
    }

    public String getIdbencana() {
        return idbencana;
    }

    public String getIdUser() {
        return idUser;
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

    public String getKota() { return kota; }


    public String getStatusPengiriman() {
        return statusPengiriman;
    }

    public boolean isStatus() {
        return status;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.idbencana);
        dest.writeString(this.idUser);
        dest.writeString(this.kategori);
        dest.writeString(this.judul);
        dest.writeString(this.alamat);
        dest.writeString(this.kota);
        dest.writeString(this.deskripsi);
        dest.writeString(this.fotoBencana);
        dest.writeString(this.tanggalLapor);
        dest.writeString(this.statusPengiriman);
        dest.writeString(this.kota);
        dest.writeByte(this.status ? (byte) 1 : (byte) 0);
    }

    protected Bencana(Parcel in) {
        this.idbencana = in.readString();
        this.idUser = in.readString();
        this.kategori = in.readString();
        this.judul = in.readString();
        this.alamat = in.readString();
        this.kota = in.readString();
        this.deskripsi = in.readString();
        this.fotoBencana = in.readString();
        this.tanggalLapor = in.readString();
        this.statusPengiriman = in.readString();
        this.kota = in.readString();
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
