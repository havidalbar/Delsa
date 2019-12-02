package com.example.delsa.POJO;

import android.os.Parcel;
import android.os.Parcelable;

public class DonasiBarang implements Parcelable {

    private String idDonasi, idUser, idBencana, kategori, koordinat, alamat, jumlah, deskripsi, foto, tgldonasi;
    private boolean anonim;

    public DonasiBarang(String idDonasi, String idUser, String idBencana, String kategori, String koordinat, String alamat, String jumlah, String deskripsi, String foto, String tgldonasi, boolean anonim) {
        this.idDonasi = idDonasi;
        this.idUser = idUser;
        this.idBencana = idBencana;
        this.kategori = kategori;
        this.koordinat = koordinat;
        this.alamat = alamat;
        this.jumlah = jumlah;
        this.deskripsi = deskripsi;
        this.foto = foto;
        this.tgldonasi = tgldonasi;
        this.anonim = anonim;
    }

    public String getIdDonasi() {
        return idDonasi;
    }

    public String getIdUser() {
        return idUser;
    }

    public String getIdBencana() {
        return idBencana;
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

    public String getJumlah() {
        return jumlah;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getFoto() {
        return foto;
    }

    public String getTgldonasi() {
        return tgldonasi;
    }

    public boolean isAnonim() {
        return anonim;
    }

    public static Creator<DonasiBarang> getCREATOR() {
        return CREATOR;
    }

    protected DonasiBarang(Parcel in) {
        idDonasi = in.readString();
        idUser = in.readString();
        idBencana = in.readString();
        kategori = in.readString();
        koordinat = in.readString();
        alamat = in.readString();
        jumlah = in.readString();
        deskripsi = in.readString();
        foto = in.readString();
        tgldonasi = in.readString();
        anonim = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idDonasi);
        dest.writeString(idUser);
        dest.writeString(idBencana);
        dest.writeString(kategori);
        dest.writeString(koordinat);
        dest.writeString(alamat);
        dest.writeString(jumlah);
        dest.writeString(deskripsi);
        dest.writeString(foto);
        dest.writeString(tgldonasi);
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
