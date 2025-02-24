#EShop - Pemrograman Lanjut

## Daftar Isi
 1. [Reflection 1](#reflection-1)
 1. [Reflection 2](#reflection-2)

## Reflection 1
 Dari Tutorial modul satu ini, kode pada modul dan yang saya buat telah mengimplementasikan prinsip clean code untuk membuat source code yang mudah dibaca dan dipahami. Selain itu, mengim 

### Clean code
- 1. Penamaan yang deskriptif seperti createProductPost untuk mappigg request post untuk membuat product baru dann juga findProductById yang menjelaskan tujuan methodnya.
- 2. Fungsi kecil dengan satu tugas, seperti findProductById yang digunakan method edit pada productRepository.

### Secure Coding
- 1. Menggunakan HTTP method dengan benar. Menggunakan method Post untuk membuat/mengubah data di dalam server dan Get untuk mengambil data.
- 2. Input validation pada form di html.
'''html
     <input th:field="*{productName}" type="text" class="form-control mb-4 col-4" id="nameInput" required>
'''

## Reflection 2
- 1. Setelah menulis unit test, saya merasa lebih yakin apakah kode saya bekerja atau tidak. Jumlah unit test yang perlu dibuat untuk setiap class sebaiknya adalah lebih dari jumlah method yang ada pada class tersebut untuk melingkup semua kasus-kasus yang mungkin terjadi pada tiap method, dapat berupa kasus positif maupun negatif. Jika code coverage dari unit test adalah 100%, maka artinya semua method atau semua baris akan dites. Hal ini tidak menjamin kode terbebas dari bug dan error, karena fungsi memiliki code coverage yang tinggi adalah untuk mengetahui apakah kode sudah berjalan sesuai ekspektasi dan juga untuk mendeteksi jika ada kode yang tidak berjalan sesuai ekpektasi kita.

- 2. Menurut saya, membuat functional test suite baru untuk memverifikasi jumlah produk pada halaman product list melanggar prinsip "Dont Repeat Yourself", karena verifikasi tadi dapat dilakukan pada functional test suite createProduct yang memiliki prosedur setup dan instance variable yang sama.