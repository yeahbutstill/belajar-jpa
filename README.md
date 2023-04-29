# belajar-jpa

- JPA akan melakukan mapping data type secara otomatis dari table ke Class Entity
secara default, tipe data dasar yang bisa digunakan di Java sudah didukung oleh JPA
yang diperlukan hanya memastikan tipe data di Class Entity sama dengan tipe data di kolom table di Database.
- Pada EnumType, sangat disarankan menggunakan type string. karena strategy integer(ORDINAL) bisa berubah ketika terjadi penambahan Enum Value pada posisi yang acak
- Khusus untuk tipe datanya Element Collection ketika melakukan UPDATE hibernate akan DELETE dulu semua data yang ada di Table Collectionnya. lalu setelah itu di INSERT ulang, jadi perlu diperhatikan ketika bikin sebuah field Collection Element pastikan tidak ada di tablenya yang berelasi ke table lain.

## Collection Update
- Hati-hati menggunakan Collection Element
- Setiap mengubah data di Collection Element, JPA akan menghapus dulu seluruh data di table Collection nya, setelah itu akan melakukan insert data baru
- Oleh karena itu, ID pada table Collection Element akan selalu berubah
- Jadi pastikan ID pada table Collection Element tidak digunakan sebagai FOREIGN KEY di table lain
- Bagaimana jika ingin menggunakan table Collection Element sebagai FK di table lain? gunakan JPA Entity Relationship.

## Entity Listener dan Event Annotation
JPA memiliki fitur Entity Listener dimana bisa membuat sebuah Class Listener, yang nantinya akan dipanggil oleh JPA ketika sebuah operasi terjadi terhadap Entity nya.

- @PrePersist Dieksekusi sebelum melakukan persist entity
- @PostPersist Dieksekusi setelah melakukan persist entity
- @PreRemove Dieksekusi sebelum melakukan remove entity
- @PostRemove Dieksekusi setelah melakukan remove entity
- @PreUpdate Dieksekusi sebelum melakukan update entity
- @PostUpdate Dieksekusi setelah melakukan update entity
- @PostLoad Dieksekusi setelah melakukan load entity

## Listener di Entity Class
- Selain menggunakan annotation @EntityListeners, bisa juga langsung menambahkan Event Annotation di Class Entity nya
- Dengan begitu, tidak perlu membuat class listener lagi
- Tapi ada kekurangan, tidak bisa membuat listener yang bisa digunakan oleh Entity lain

## One to One Relationship
- One to One Relationship adalah relasi yang paling mudah, dimana satu table berelasi dengan satu data di table lain.
- Ada beberapa cara melakukan relasi pada One to One, dengan FOREIGN KEY atau dengan PRIMARY KEY yang sama

## @OneToOne - Annotation
- Untuk menambah atribut di Entity yang berelasi dengan Entity lain, perlu di tambahkan annotation @OneToOne
- Dan untuk memberi tahu JPA tentang kolom yang digunakan untuk melakukan JOIN FOREIGN KEY, perlu di tambahkan annotation @JoinColumn
- Namun jika JOIN nya menggunakan PRIMARY KEY yang sama, bisa gunakan annotation @PrimaryKeyJoinColumn


## Run postgre with docker
```shell
docker run --rm --name belajar-java-persistance-api-db \ 
-e POSTGRES_DB=belajar_java_persistance_api \
-e POSTGRES_USER=dani \
-e POSTGRES_PASSWORD=dani \
-e PGDATA=/var/lib/postgresql/data/pgdata \
-v "$PWD/belajar-java-persistance-api-db-data:/var/lib/postgresql/data" \
-p 5432:5432 \
postgres:15
```

## Test connect to database
```shell
psql -h 127.0.0.1 -U dani belajar_java_persistance_api
```

## enable extension UUID v4 postgre
```sql
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE TABLE categories(                  
    id uuid DEFAULT uuid_generate_v4() PRIMARY KEY NOT NULL,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(500)
);

```