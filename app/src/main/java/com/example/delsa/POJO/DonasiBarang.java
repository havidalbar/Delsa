package com.example.delsa.POJO;

import android.os.Parcel;
import android.os.Parcelable;

public class DonasiBarang implements Parcelable {
    private String idDonasi, idBencana, idUser, kategori, koordinat, alamat, deskripsi, jumlah, foto;
    private boolean anonim;

    public DonasiBarang() {
    }

    public DonasiBarang(String idDonasi, String idBencana, String idUser, String kategori, String koordinat, String alamat, String deskripsi, String jumlah, String foto, boolean anonim) {
        this.idDonasi = idDonasi;
        this.idBencana = idBencana;
        this.idUser = idUser;
        this.kategori = kategori;
        this.koordinat = koordinat;
        this.alamat = alamat;
        this.deskripsi = deskripsi;
        this.jumlah = jumlah;
        this.foto = foto;
        this.anonim = anonim;
    }

    public String getIdDonasi() {
        return idDonasi;
    }

    public String getIdBencana() {
        return idBencana;
    }

    public String getIdUser() {
        return idUser;
    }

    public String getKategori() {
        return kategori;
    }

    public String getKoordinat() {
        return koordinat;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getJumlah() {
        return jumlah;
    }

    public String getFoto() {
        return foto;
    }

    public boolean isAnonim() {
        return anonim;
    }

    public static Creator<DonasiBarang> getCREATOR() {
        return CREATOR;
    }

    protected DonasiBarang(Parcel in) {
        idDonasi = in.readString();
        idBencana = in.readString();
        idUser = in.readString();
        kategori = in.readString();
        koordinat = in.readString();
        alamat = in.readString();
        deskripsi = in.readString();
        jumlah = in.readString();
        foto = in.readString();
        anonim = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idDonasi);
        dest.writeString(idBencana);
        dest.writeString(idUser);
        dest.writeString(kategori);
        dest.writeString(koordinat);
        dest.writeString(alamat);
        dest.writeString(deskripsi);
        dest.writeString(jumlah);
        dest.writeString(foto);
        dest.writeByte((byte) (anonim ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DonasiBarang> CREATOR = new Creator<DonasiBarang>() {
        @Override
        public DonasiBarang createFromParcel(Parcel in) {
            return new DonasiBarang(in);
        }

        @Override
        public DonasiBarang[] newArray(int size) {
            return new DonasiBarang[size];
        }
    };
}
