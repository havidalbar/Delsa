package com.example.delsa.POJO;

import android.os.Parcel;
import android.os.Parcelable;

public class DonasiBarang implements Parcelable {

    private String idDonasi, idUser, idBencana, kategori, koordinat, alamat, ketalamat, jumlah, deskripsi, foto, tgldonasi, pesan,nama;
    private boolean anonim;

    public DonasiBarang(){

    }

    public DonasiBarang(String idDonasi, String idUser, String idBencana, String kategori, String koordinat, String alamat, String ketalamat, String jumlah, String deskripsi, String foto, String tgldonasi, String pesan, String nama, boolean anonim) {
        this.idDonasi = idDonasi;
        this.idUser = idUser;
        this.idBencana = idBencana;
        this.kategori = kategori;
        this.koordinat = koordinat;
        this.alamat = alamat;
        this.ketalamat = ketalamat;
        this.jumlah = jumlah;
        this.deskripsi = deskripsi;
        this.foto = foto;
        this.tgldonasi = tgldonasi;
        this.pesan = pesan;
        this.nama = nama;
        this.anonim = anonim;
    }

    public String getIdDonasi() {
        return idDonasi;
    }

    public void setIdDonasi(String idDonasi) {
        this.idDonasi = idDonasi;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdBencana() {
        return idBencana;
    }

    public void setIdBencana(String idBencana) {
        this.idBencana = idBencana;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getKoordinat() {
        return koordinat;
    }

    public void setKoordinat(String koordinat) {
        this.koordinat = koordinat;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getKetalamat() {
        return ketalamat;
    }

    public void setKetalamat(String ketalamat) {
        this.ketalamat = ketalamat;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getTgldonasi() {
        return tgldonasi;
    }

    public void setTgldonasi(String tgldonasi) {
        this.tgldonasi = tgldonasi;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public boolean isAnonim() {
        return anonim;
    }

    public void setAnonim(boolean anonim) {
        this.anonim = anonim;
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
        ketalamat = in.readString();
        jumlah = in.readString();
        deskripsi = in.readString();
        foto = in.readString();
        tgldonasi = in.readString();
        pesan = in.readString();
        nama = in.readString();
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
        dest.writeString(ketalamat);
        dest.writeString(jumlah);
        dest.writeString(deskripsi);
        dest.writeString(foto);
        dest.writeString(tgldonasi);
        dest.writeString(pesan);
        dest.writeString(nama);
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
