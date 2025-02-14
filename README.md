#EShop - Pemrograman Lanjut

##Reflection 1
 Dari Tutorial modul satu ini, kode pada modul dan yang saya buat telah mengimplementasikan prinsip clean code untuk membuat source code yang mudah dibaca dan dipahami. Selain itu, mengim 

###Clean code
- 1. Penamaan yang deskriptif seperti createProductPost untuk mappigg request post untuk membuat product baru dann juga findProductById yang menjelaskan tujuan methodnya.
- 2. Fungsi kecil dengan satu tugas, seperti findProductById yang digunakan method edit pada productRepository.

###Secure Coding
- 1. Menggunakan HTTP method dengan benar. Menggunakan method Post untuk membuat/mengubah data di dalam server dan Get untuk mengambil data.
- 2. Input validation pada form di html.
'''html
     <input th:field="*{productName}" type="text" class="form-control mb-4 col-4" id="nameInput" required>
'''
