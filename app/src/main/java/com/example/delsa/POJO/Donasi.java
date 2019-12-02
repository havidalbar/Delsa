package com.example.delsa.POJO;

import android.os.Parcel;
import android.os.Parcelable;

public class Donasi implements Parcelable {

    private String idDonasi, idUser, idBencana, nominal, kodeunik, totalpembayaran, metodepembayaran,pesan, bataspembayaran;
    private boolean anonim, terbayar;

    public Donasi() {
    }

    public Donasi(String idDonasi, String idUser, String idBencana, String nominal, String kodeunik, String totalpembayaran, String metodepembayaran, String pesan, String bataspembayaran, boolean anonim, boolean terbayar) {
        this.idDonasi = idDonasi;
        this.idUser = idUser;
        this.idBencana = idBencana;
        this.nominal = nominal;
        this.kodeunik = kodeunik;
        this.totalpembayaran = totalpembayaran;
        this.metodepembayaran = metodepembayaran;
        this.pesan = pesan;
        this.bataspembayaran = bataspembayaran;
        this.anonim = anonim;
        this.terbayar = terbayar;
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

    public String getNominal() {
        return nominal;
    }

    public String getKodeunik() {
        return kodeunik;
    }

    public String getTotalpembayaran() {
        return totalpembayaran;
    }

    public String getMetodepembayaran() {
        return metodepembayaran;
    }

    public String getPesan() {
        return pesan;
    }

    public String getBataspembayaran() {
        return bataspembayaran;
    }

    public boolean isAnonim() {
        return anonim;
    }

    public boolean isTerbayar() {
        return terbayar;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.idDonasi);
        dest.writeString(this.idUser);
        dest.writeString(this.idBencana);
        dest.writeString(this.nominal);
        dest.writeString(this.kodeunik);
        dest.writeString(this.totalpembayaran);
        dest.writeString(this.metodepembayaran);
        dest.writeString(this.pesan);
        dest.writeString(this.bataspembayaran);
        dest.writeByte(this.anonim ? (byte) 1 : (byte) 0);
        dest.writeByte(this.terbayar ? (byte) 1 : (byte) 0);
    }

    protected Donasi(Parcel in) {
        this.idDonasi = in.readString();
        this.idUser = in.readString();
        this.idBencana = in.readString();
        this.nominal = in.readString();
        this.kodeunik = in.readString();
        this.totalpembayaran = in.readString();
        this.metodepembayaran = in.readString();
        this.pesan = in.readString();
        this.bataspembayaran = in.readString();
        this.anonim = in.readByte() != 0;
        this.terbayar = in.readByte() != 0;
    }

    public static final Creator<Donasi> CREATOR = new Creator<Donasi>() {
        @Override
        public Donasi createFromParcel(Parcel source) {
            return new Donasi(source);
        }

        @Override
        public Donasi[] newArray(int size) {
            return new Donasi[size];
        }
    };
}
