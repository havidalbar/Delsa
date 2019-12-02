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

    public static Creator<Donasi> getCREATOR() {
        return CREATOR;
    }

    protected Donasi(Parcel in) {
        idDonasi = in.readString();
        idUser = in.readString();
        idBencana = in.readString();
        nominal = in.readString();
        kodeunik = in.readString();
        totalpembayaran = in.readString();
        metodepembayaran = in.readString();
        pesan = in.readString();
        bataspembayaran = in.readString();
        anonim = in.readByte() != 0;
        terbayar = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idDonasi);
        dest.writeString(idUser);
        dest.writeString(idBencana);
        dest.writeString(nominal);
        dest.writeString(kodeunik);
        dest.writeString(totalpembayaran);
        dest.writeString(metodepembayaran);
        dest.writeString(pesan);
        dest.writeString(bataspembayaran);
        dest.writeByte((byte) (anonim ? 1 : 0));
        dest.writeByte((byte) (terbayar ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Donasi> CREATOR = new Creator<Donasi>() {
        @Override
        public Donasi createFromParcel(Parcel in) {
            return new Donasi(in);
        }

        @Override
        public Donasi[] newArray(int size) {
            return new Donasi[size];
        }
    };
}
