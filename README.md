# Wajib Pajak REST API

Layanan RESTful berbasis Spring Boot untuk mengelola data *Wajib Pajak*. Aplikasi ini menyediakan endpoint CRUD, validasi data, serta database H2 in-memory guna memudahkan pengembangan lokal.

## Clone Repository

Untuk mendapatkan kode sumber aplikasi ini, silakan clone repository dari GitHub:

```bash
git clone https://github.com/RiskyAdit06/wajibpajak-spring.git
cd wajibpajak-spring
```

Pastikan Anda berada di direktori hasil clone sebelum melakukan build atau menjalankan aplikasi.

## Fitur
- Endpoint `create`, `read`, `update`, dan `delete` pada jalur `/api/wajib-pajak`.
- Lapisan validasi untuk memastikan field wajib terisi dan NPWPD tidak duplikat.
- Body respons konsisten menggunakan `ResultResponse<T>` untuk operasi mutasi.
- Global exception handler dengan HTTP status yang sesuai.
- Database H2 in-memory (konsol di `/h2-console`) untuk uji cepat.

## Daftar Endpoint

Berikut daftar endpoint (API endpoints) yang tersedia pada aplikasi ini:

| Method | Endpoint                       | Keterangan                      |
|--------|------------------------------- |----------------------------------|
| GET    | `/api/wajib-pajak`             | Mendapatkan daftar seluruh Wajib Pajak |
| GET    | `/api/wajib-pajak/{id}`        | Mendapatkan detail Wajib Pajak berdasarkan ID |
| POST   | `/api/wajib-pajak`             | Membuat data Wajib Pajak baru    |
| PUT    | `/api/wajib-pajak/{id}`        | Memperbarui data Wajib Pajak berdasarkan ID |
| DELETE | `/api/wajib-pajak/{id}`        | Menghapus data Wajib Pajak berdasarkan ID |

## Teknologi
- Java 17
- Spring Boot 3.4.11 (Web, Data JPA, Validation)
- H2 Database
- Maven wrapper (`mvnw` / `mvnw.cmd`)
- Lombok

## Langkah-Langkah Menjalankan di Lokal

1. **Prasyarat**
   - Pastikan sudah menginstall **JDK 17**.
   - *Opsional:* **Maven 3.9+** jika ingin menggunakan Maven secara manual, namun direkomendasikan langsung pakai wrapper `./mvnw` (Linux/Mac) atau `mvnw.cmd` (Windows) yang sudah disediakan di repo.

2. **Clone project**  
   Jalankan perintah berikut di terminal:
   ```bash
   git clone https://github.com/RiskyAdit06/wajibpajak-spring.git
   cd wajibpajak-spring
   ```

3. **Build project dan install dependensi**  
   Gunakan Maven wrapper:
   ```bash
   ./mvnw clean package
   ```
   atau
   ```bash
   ./mvn clean install
   ```
   Keduanya bisa dipakai, namun untuk keperluan lokal `clean package` saja sudah cukup.

4. **Menjalankan aplikasi**
   ```bash
   ./mvn spring-boot:run
   ```
   Setelah berhasil jalan, API akan tersedia di `http://localhost:8080`.

5. **Akses Konsol Database (opsional)**
   - Buka browser ke: `http://localhost:8080/h2-console`
   - JDBC URL: `jdbc:h2:mem:wajibpajakdb`
   - User: `sa`, password: *(kosong)*

6. **Cek endpoint API**  
   Gunakan Postman, Insomnia, curl, dsb. Contoh: lihat di bagian "Contoh Request Create" di bawah.

   Atau bisa juga import di Postman menggunakan file koleksi berikut:  
   **Wajib Pajak.postman_collection.json**

## Konfigurasi
File `src/main/resources/application.properties` mengatur koneksi database H2, perilaku JPA (`ddl-auto=update`), penayangan query (`show-sql`), dan port server. Sesuaikan sesuai kebutuhan environment.

### Contoh Request Create
```bash
curl -X POST http://localhost:8080/api/wajib-pajak \
  -H "Content-Type: application/json" \
  -d '{
    "nama": "Warung Sederhana",
    "npwpd": "1234567890",
    "alamat": "Jl. Mawar No. 10",
    "jenisUsaha": "Kuliner"
  }'
```
Response:
```json
{
  "message": "Data Wajib Pajak berhasil ditambahkan",
  "data": {
    "id": 1,
    "nama": "Warung Sederhana",
    "npwpd": "1234567890",
    "alamat": "Jl. Mawar No. 10",
    "jenisUsaha": "Kuliner",
    "createdAt": "...",
    "updatedAt": "..."
  }
}
```

