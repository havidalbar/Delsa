package com.example.delsa.POJO;

import android.os.Parcel;
import android.os.Parcelable;

public class DonasiBarang implements Parcelable {

    private String idDonasi, idUser, idBencana, kategori, jumlah, deskripsi, foto, tgldonasi, ketlokasi;
    private boolean anonim;

    public DonasiBarang() {
    }

    public DonasiBarang(String idDonasi, String idUser, String idBencana, String kategori, String jumlah, String deskripsi, String foto, String tgldonasi, String ketlokasi, boolean anonim) {
        this.idDonasi = idDonasi;
        this.idUser = idUser;
        this.idBencana = idBencana;
        this.kategori = kategori;
        this.jumlah = jumlah;
        this.deskripsi = deskripsi;
        this.foto = foto;
        this.tgldonasi = tgldonasi;
        this.ketlokasi = ketlokasi;
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

    public String getKetlokasi() {
        return ketlokasi;
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
        jumlah = in.readString();
        deskripsi = in.readString();
        foto = in.readString();
        tgldonasi = in.readString();
        ketlokasi = in.readString();
        anonim = in.readByte() != 0;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idDonasi);
        dest.writeString(idUser);
        dest.writeString(idBencana);
        dest.writeString(kategori);
        dest.writeString(jumlah);
        dest.writeString(deskripsi);
        dest.writeString(foto);
        dest.writeString(tgldonasi);
        dest.writeString(ketlokasi);
        dest.writeByte((byte) (anonim ? 1 : 0));
    }
}
