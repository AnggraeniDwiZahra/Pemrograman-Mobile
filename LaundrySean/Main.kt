package com.example.laundysean

data class Pelanggan(
    val nama: String,
    var beratCucian: Int = 0,
    var alamat: String = "",
    var pilihLayanan: String = ""
)

class NotaLaundry {
    var namaPelanggan: String = ""
        set(value) {
            field = value.uppercase()
        }

    var jenisLayanan: String = ""
        set(value) {
            field = value.lowercase()
        }

    var hargaPerKilo: Int = 0
        get() = if (jenisLayanan == "cuci_setrika") 7000 else 5000
}

fun main() {
    val daftarAntrean = ArrayList<Pelanggan>()
    var running = true

    while (running) {
        println("\n=== WELCOME TO LAUNDRY SEAN ===")
        println("1. Tambah Antrean")
        println("2. Lihat Semua Antrean")
        println("3. Edit Data")
        println("4. Hapus Data")
        println("5. Cari Nama")
        println("0. Keluar")
        print("Pilih Menu: ")

        val inputMenu = readLine()

        if (inputMenu == null) {
            running = false
        } else {
            when (inputMenu) {
                "1" -> {
                    println("\n TAMBAH DATA ")
                    print("Nama Pelanggan: ")
                    val isiNama = readLine()
                    var nama = ""
                    if (isiNama != null) { nama = isiNama }

                    print("Berat Cucian (Kg): ")
                    val isiBerat = readLine()
                    var beratStr = ""
                    if (isiBerat != null) { beratStr = isiBerat }

                    print("Alamat: ")
                    val isiAlamat = readLine()
                    var alamat = ""
                    if (isiAlamat != null) { alamat = isiAlamat }

                    println("Pilih Layanan:")
                    println("1. Cuci Kering (Rp5.000/kg)")
                    println("2. Cuci Setrika (Rp7.000/kg)")
                    print("Pilih (1/2): ")
                    val inputPilihan = readLine()
                    var pilih = "1"
                    if (inputPilihan != null) { pilih = inputPilihan }

                    var beratInt = 0
                    if (beratStr != "") {
                        try {
                            beratInt = beratStr.toInt()
                        } catch (e: Exception) {
                            println("Peringatan! Berat harus dalam bentuk angka!")
                        }
                    }

                    if (beratInt > 0) {
                        val jenisCuci = if (pilih == "2") "cuci_setrika" else "cuci_kering"
                        val dataBaru = Pelanggan(nama, beratInt, alamat, jenisCuci)
                        daftarAntrean.add(dataBaru)
                        println("Berhasil! $nama telah terdaftar")
                    } else {
                        println("Gagal! Berat harus lebih dari 0")
                    }
                }

                "2" -> {
                    println("\n DAFTAR SEMUA ANTREAN ")
                    if (daftarAntrean.isEmpty()) {
                        println("Belum ada antrean saat ini")
                    } else {
                        for (data in daftarAntrean) {
                            val nota = NotaLaundry()
                            nota.namaPelanggan = data.nama
                            nota.jenisLayanan = data.pilihLayanan
                            val hargaTotal = nota.hargaPerKilo * data.beratCucian

                            println("---------------------------")
                            println("Nama    : ${nota.namaPelanggan}")
                            println("Layanan : ${nota.jenisLayanan}")
                            println("Berat   : ${data.beratCucian} Kg")
                            println("Alamat  : ${data.alamat}")
                            println("Total   : Rp$hargaTotal")
                        }
                    }
                }

                "3" -> {
                    println("\n EDIT DATA ")
                    print("Masukkan Nama pelanggan yang ingin diedit: ")
                    val cariNama = readLine()
                    var cari = ""
                    if (cariNama != null) { cari = cariNama }

                    var ditemukan = false
                    for (data in daftarAntrean) {
                        if (data.nama.equals(cari, ignoreCase = true)) {
                            print("Masukkan Berat Baru: ")
                            val editBerat = readLine()
                            var beratBaru = ""
                            if (editBerat != null) { beratBaru = editBerat }

                            try {
                                val hasilEdit = beratBaru.toInt()
                                if (hasilEdit > 0) {
                                    data.beratCucian = hasilEdit
                                    println("Data berhasil diupdate!")
                                } else {
                                    println("Berat tidak valid!")
                                }
                            } catch (e: Exception) {
                                println("Input harus berupa angka!")
                            }
                            ditemukan = true
                        }
                    }
                    if (!ditemukan) println("Nama tidak ditemukan.")
                }

                "4" -> {
                    println("\n HAPUS DATA ")
                    print("Masukkan Nama yang akan dihapus: ")
                    val hapusNama = readLine()
                    var namaDihapus = ""
                    if (hapusNama != null) { namaDihapus = hapusNama }

                    var posisi = -1

                    for (i in 0 until daftarAntrean.size) {
                        if (daftarAntrean[i].nama.equals(namaDihapus, ignoreCase = true)) {
                            posisi = i
                            break
                        }
                    }

                    if (posisi != -1) {
                        daftarAntrean.removeAt(posisi)
                        println("Data $namaDihapus telah dihapus.")
                    } else {
                        println("Data $namaDihapus tidak ditemukan.")
                    }
                }

                "5" -> {
                    println("\n CARI DATA ")
                    print("Masukkan Nama: ")
                    val cariData = readLine()
                    var cari = ""
                    if (cariData != null) { cari = cariData }

                    var ditemukan = false
                    for (data in daftarAntrean) {
                        if (data.nama.contains(cari, ignoreCase = true)) {
                            println("Ditemukan: ${data.nama} (${data.pilihLayanan}) - ${data.beratCucian}kg")
                            ditemukan = true
                        }
                    }
                    if (!ditemukan) println("Data tidak ditemukan.")
                }

                "0" -> {
                    println("Program selesai. Sampai jumpa lagi!")
                    running = false
                }
                else -> println("Pilihan tidak tersedia!")
            }
        }
    }
}