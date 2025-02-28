#EShop - Pemrograman Lanjut

## Daftar Isi
- [Tutorial 1](#tutorial-1)
  - [Reflection 1](#reflection-1)
  - [Reflection 2](#reflection-2)
- [Tutorial 3](#tutorial-3)
  - [Reflection 1](#reflection-1-1)


## Tutorial 1
### Reflection 1
 Dari Tutorial modul satu ini, kode pada modul dan yang saya buat telah mengimplementasikan prinsip clean code untuk membuat source code yang mudah dibaca dan dipahami. Selain itu, mengim 

#### Clean code
- 1. Penamaan yang deskriptif seperti createProductPost untuk mappigg request post untuk membuat product baru dann juga findProductById yang menjelaskan tujuan methodnya.
- 2. Fungsi kecil dengan satu tugas, seperti findProductById yang digunakan method edit pada productRepository.

#### Secure Coding
- 1. Menggunakan HTTP method dengan benar. Menggunakan method Post untuk membuat/mengubah data di dalam server dan Get untuk mengambil data.
- 2. Input validation pada form di html.
'''html
     <input th:field="*{productName}" type="text" class="form-control mb-4 col-4" id="nameInput" required\>
'''

### Reflection 2
- 1. Setelah menulis unit test, saya merasa lebih yakin apakah kode saya bekerja atau tidak. Jumlah unit test yang perlu dibuat untuk setiap class sebaiknya adalah lebih dari jumlah method yang ada pada class tersebut untuk melingkup semua kasus-kasus yang mungkin terjadi pada tiap method, dapat berupa kasus positif maupun negatif. Jika code coverage dari unit test adalah 100%, maka artinya semua method atau semua baris akan dites. Hal ini tidak menjamin kode terbebas dari bug dan error, karena fungsi memiliki code coverage yang tinggi adalah untuk mengetahui apakah kode sudah berjalan sesuai ekspektasi dan juga untuk mendeteksi jika ada kode yang tidak berjalan sesuai ekpektasi kita.

- 2. Menurut saya, membuat functional test suite baru untuk memverifikasi jumlah produk pada halaman product list melanggar prinsip "Dont Repeat Yourself", karena verifikasi tadi dapat dilakukan pada functional test suite createProduct yang memiliki prosedur setup dan instance variable yang sama.

## Tutorial 3
### Reflection 1
1. Prinsip SOLID yang telah saya terapkan.
    a. SRP : Memisahkan  CarController dari ProductController karena bukan Class yang memiliki hubungan. Selain itu, memisahkan class MVC Car dan Product lainnya

    b. OCP : menggunakan inteerface untuk Service product dan car sehingga  jika menambahkan fitur baru, bisa membuat implementasi baru tanpa mengubah kode yang ada.

    c. LSP : Mengubah class CarController menjadi classnya sendiri, bukan subclass dari ProductController.

    d. ISP : Memisahkan interface CarService dan ProductService karena memenuhi kebutuhan yang berbeda.

    e. DIP : Menggunakan interface untuk dependensii, sehingga kode flleksibel dan mudah ditest.

2. Kelebihan dari menerapkan prinsip SOLID pada proyek saya:

   a. SRP : Membuat tiap kelas fokus pada satu tujuan dan mengurangi kompleksitas, serta mempermudah proses debugging dan maintenance.
    Contoh: 

        1. Penerapan MVC spring boot
        2. Pemisahan tugas MVC untuk Car dan Product

   b. OCP : Memperbolehkan ekstensi fungsionalitas kelas tanpa mengubah kode yang ada.
    Contoh:

        1. Memisahkan implementasi ProductService dengan interface, sehingga dapat membuat implementasi ProductService lain bila diperlukan tanpa mengubah kode yang ada.
        
   c. LSP : Menghindari inheritance yang mungkin dapat membuat program tidak bekerja.
    Contoh:

        1. Menjadikan CarController class-nya sendiri, bukan subclass dari ProductController.

   d. ISP : Menghindari class mengimplementasikan method yang tidak dipakai, serta membuat intercface  menjadi lebib kecil dan khusus satu tujuan.
    Contoh:

        1. Memisahkan interface CarService dan ProductService.

   e. DIP : Mengurangi dependensi langsung pada concreete class, sehingga menciptakan program yang lebih fleksibel.
    Contoh:

        1. class ServiceImpl dan Controller memiliki dependensi pada interface bukan implementation pada concrete class.

3. Kekurangan akibat tidak menerapkan prinsip SOLID:

    a. SRP: Jika sebuah kelas memiliki terlalu banyak tugas, perubahan kecil bisa memengaruhi banyak bagian lain, memperbanyak bug dan mempersulit debugging.
    Contoh:

        - kelas yang mengurus logika bisnis dan akses ke database sekaligus, sehingga perubahan pada satu aspek bisa menyebabkan perubahan besar dalam kode. 

    b. OCP: Jika kode tidak terbuka untuk ekstensi, tiap fitur baru ditambahkan, perlu mengubah kode yang ada, sehingga meningkatkan risiko error terjadi dan membuat kode sulit dimaintain.
    Contoh:

        - Misal suatu sistem dengan fitur pembayaran hanya mendukung satu metode dan tidak dibuat dengan abstraksi, menambahkan metode pembayaran baru butuh melakukan perubahan yang banyak pada kode lama.

    c. LSP: Jika subclass mengganti perilaku superclass dengan cara yang tidak cocok, kode dapt menjadi tidak bisa diprediksi perilakunya atau atau bahkan menyebabkan error saat berejalan.
    Contoh:

        - Sebelum diperbaiki, CarController exteends ProductController, yang menyebabkan metode seperti editProductPage() ada dalam CarController. Padahal, mobil memiliki atribut dan logika yang berbeda dari produk, sehingga fitur edit produk bisa tidak bekerja dengan benar pada Car.

    d. ISP: Jika sebuah interface terlalu besar dan memiliki metode yang tidak dibutuhkan oleh implementasinya, kelas yang menggunakannya akan dipaksa mengimplementasikan metode yang tidak ada hubungan.
    Contoh:

        - Jika CarService dan ProductService dijadikan satu interface, metode yang spesifik hanya untuk Car, seperti updateCarColor(), juga harus diimplementasikan oleh ProductServiceImpl, padahal produk tidak memiliki atribut warna. 

    e. DIP: Jika kelas tingkat tinggi langsung bergantung pada implementasi konkret, maka iap perubahan pada implementasi akan berdampak pada kelas yang menggunakannya, menyebabkan kode sulit diubah atau ditest.
    Contoh:

        - Jika CarController langsung membuat instance dari CarRepository alih-alih bergantung pada CarService, maka jika  kita ingin mengganti cara meyimopan data seperti List<Car>, kita harus mengubah CarController secara langsung, yang bisa menyebabkan banyak perubahan di bagian-bagian lain.