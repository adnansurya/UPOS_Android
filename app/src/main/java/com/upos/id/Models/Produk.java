package com.upos.id.Models;

public class Produk {

    public String nama, kode, stok, satuan,  hargaDasar, hargaJual, kategori, jenis, keterangan, gambar;


    public Produk(){}

    public Produk(String nama, String kode, String stok, String satuan, String hargaDasar, String hargaJual, String kategori, String jenis, String keterangan, String gambar){
        this.nama = nama;
        this.kode = kode;
        this.keterangan = keterangan;
        this.stok = stok;
        this.satuan = satuan;
        this.hargaDasar = hargaDasar;
        this.hargaJual = hargaJual;
        this.kategori = kategori;
        this.jenis =jenis;
        this.gambar = gambar;

    }

    public String getNama() {
        return nama;
    }

    public String getKode() {
        return kode;
    }

    public String getStok() {
        return stok;
    }

    public String getSatuan() {
        return satuan;
    }

    public String getHargaDasar() {
        return hargaDasar;
    }

    public String getHargaJual() {
        return hargaJual;
    }

    public String getKategori() {
        return kategori;
    }

    public String getJenis() {
        return jenis;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public String getGambar() {
        return gambar;
    }


    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public void setStok(String stok) {
        this.stok = stok;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public void setHargaDasar(String hargaDasar) {
        this.hargaDasar = hargaDasar;
    }

    public void setHargaJual(String hargaJual) {
        this.hargaJual = hargaJual;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }
}
