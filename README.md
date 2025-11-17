# Petunjuk Penggunaan Program Hill Climbing

## Persyaratan Sistem

  1. Sebelum menjalankan program, pastikan perangkat sudah memenuhi syarat berikut:
  2. Sudah terpasang Java Development Kit (JDK) minimal versi 8.
  3. Tersedia aplikasi terminal/command prompt.
  4. Berkas input peta (format .txt) tersedia dan mengikuti struktur yang telah ditentukan oleh program.

Jika Java belum terpasang, Anda dapat mengunduhnya dari situs resmi Oracle atau OpenJDK.

## Cara Melakukan Kompilasi

Untuk mengubah kode sumber menjadi program yang siap dijalankan, lakukan langkah berikut:

  1. Buka terminal atau command prompt.
  2. Arahkan ke folder tempat file–file .java program Hill Climbing berada.
  3. Jalankan perintah kompilasi: `javac MyHC.java`

Jika tidak muncul pesan kesalahan, proses kompilasi berhasil dan program dapat dijalankan.

## Cara Menjalankan Program

Setelah berhasil dikompilasi, program dapat dijalankan melalui terminal dengan format perintah berikut:

`java MyHC [nama_file_input] [banyak_iterasi] [seed_opsional]`

| Parameter             | Wajib | Keterangan                                                                                                                                          |
| --------------------- | ----- | ----------------------------------------------------------------------------------------------------------------------------------------------------|
| `nama_file_input`     | Ya    | Nama berkas peta yang ingin digunakan, misalnya `input.txt`.                                                                                        |
| `banyak_iterasi`      | Ya    | Jumlah iterasi Hill Climbing per eksekusi (berapa kali algoritma akan mencoba menukar posisi stasiun ke tetangga yang lebih baik sebelum berhenti). |
| `seed_opsional`       | Tidak | Angka yang digunakan untuk mengatur konsistensi proses acak. Jika tidak diberikan, program menggunakan seed acak.                                   |

## Contoh penggunaan
1. Menjalankan tanpa seed : `java MyHC input.txt 1000`

2. Menjalankan dengan seed tertentu (untuk reproduksi hasil): `java MyHC input.txt 1000 38278`
