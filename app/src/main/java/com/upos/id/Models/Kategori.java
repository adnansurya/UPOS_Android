package com.upos.id.Models;

public class Kategori {

    public String nama, kode, keterangan;

    public Kategori(){}

    public Kategori(String nama, String kode, String keterangan){
        this.nama = nama;
        this.kode = kode;
        this.keterangan = keterangan;
    }

    public String getNama() {
        return nama;
    }

    public String getKode() {
        return kode;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}
