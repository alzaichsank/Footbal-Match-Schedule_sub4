# Footbal-Match-Schedule_sub4
Footbal Match Schedule
submission dicoding 4

## Preview
<img src="https://github.com/alzaichsank/Footbal-Match-Schedule_sub4/blob/master/preview/preview1.png" width=350/>&nbsp;
<img src="https://github.com/alzaichsank/Footbal-Match-Schedule_sub4/blob/master/preview/unit1.png" width=350/>&nbsp;
<img src="https://github.com/alzaichsank/Footbal-Match-Schedule_sub4/blob/master/preview/unit2.png" width=350/>&nbsp;

### syarat :
1. Pertahankan semua fitur pada aplikasi sebelumnya.
2. Menerapkan unit tests pada beberapa fungsi, misalnya fungsi untuk request data ke server.
3. Menerapkan instrumentation tests dengan skenario yang Anda buat sendiri sesuai behaviour pada aplikasi.

NB : Tuliskan skenario pengujian Anda (unit tests & instrumentation tests) pada kolom komentar ketika Anda ingin mengumpulkan tugas ini.

## skenario :
Berikut skenario testing:

<>unit testing :
- mengetes fungsi get data dengan retrofit call back pada main presenter dan detail presenter:
  () detail presenter -> get detail team data
  () matchpresenter -> get all leageu data , get next match, get prev match


<>instrumental testing :
 - mengecek botom navigation view
 - mesimulasikan flow pada next match dan prev match :
   () click spinner -> pilih item
   () scroll recyler -> pilih item
   () add favorites -> back
   () pindah ke menu favorite -> pilih item
   () remove favorites -> back


### Ketentuan :

Ketentuan dari aplikasi yang Anda serahkan adalah:

1. Proyek dari aplikasi harus menggunakan Android Studio.
2. Bahasa pemrograman yang digunakan adalah Kotlin.

## Info ##
1. Aplikasi menggunakan konsep MVPI
2. Aplikasi menangkap data dari API menggunakan Retrofit
3. Aplikasi dibuat dengan XML
4. Aplikasi menggunakan Anko SQLite
5. Improvment dari UI, string konsep, model konsep, penamaan class, file dll
6. Implement unit test dengan mockito 
7. Implement Instrumental test dengan espresso

