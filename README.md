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

## One to Many Relationship
- Untuk membuat Entity yang memiliki relasi One to Many dengan entity lain, bisa menggunakan @OneToMany
- Dan atribut di Entity, tipe datanya harus dibungkus dalam collection, seperti misalnya List<T> atau Set<T>
- Relasi OneToMany jika dilihat dari arah sebaliknya adalah relasi ManyToOne, oleh karena itu nanti di Class Entity sebelahnya, relasinya adalah ManyToOne
- Cara penggunaannya hampir mirip dengan relasi OneToOne, bisa digunakan JoinColumn pada Entity yang memiliki kolom Foreign Key nya yang menggunakan ManyToOne, dan cukup gunakan attribute mappedBy pada attribute yang menggunakan OneToMany

## Many To Many Relationship
- Pada relasi Many to Many, tidak bisa hanya menggunakan dua table, biasanya akan ditambahkan table ditengah sebagai jembatan relasi antara table pertama dan kedua
- Untuk menggunakan relasi Many to Many, bisa dengan menggunakan annotation @ManyToMany
- Yang membedakan dengan relasi lain, karena Many to Many butuh table tambahan ditengah sebagai jembatan, oleh karena itu untuk melakukan join, menggunakan annotation @JoinTable
- Untuk table yang ditengah sebagai jembatan, tidak butuh membuat Class Entity nya

## Fetch
- Saat membuat Entity Class yang kompleks dan banyak sekali relasinya, perlu diperhatikan dan berhati-hati
- Hal ini karena secara default, beberapa jenis relasi memiliki value fetch EAGER, artinya saat melakukan FIND Entity, relasinya akan secara otomatis di JOIN, walaupun tidak membutuhkan relasinya
- Kebalikan dari EAGER adalah LAZY, dimana artinya relasi akan di QUERY ketika dipanggil attribute nya, jika tidak maka itu tidak akan di QUERY

## Default Value Fetch
- @OneToOne itu default fetch EAGER
- @OneToMany itu default fetch LAZY
- @ManyToOne itu default fetch EAGER
- @ManyToMany itu default fetch LAZY

PERLU BERHATI-HATI DENGAN FETCH EAGER

## Mengubah Fetch
- Jika ingin mengubah nilai default Fetch, kita bisa ubah di attribut di annotation relasinya
- Semua relasi dari One to One, One to Many, Many to One dan Many to Many memiliki attribute fetch yang bisa kita ubah

## IS-A Relationship
- Di dalam Entity Relationship Diagram (ERD), terdapat jenis relasi bernama IS-A
- IS-A ini biasanya digunakan untuk mendukung konsep pewarisan di relational database, yang notabenenya tidak mendukung pewarisan
- Jenis relasi IS-A sering sekali ditemukan di kehidupan nyata
- Contoh: table Empolyee, dimana memiliki detail Manager, VicePresident, Supervisor dan lain-lain
- Contoh: table Payment, dimana memiliki detail PaymentBCA, PaymentMandiri, PaymentCreditCard dan lain-lain
- IS-A Relationship jika dalam OOP, maka implementasinya adalah berupa Inheritance / Pewarisan

## Implementasi IS-A Relationship
- IS-A sendiri memiliki beberapa cara implementasi di table nya
- Single Table Inheritance
- Joined Table Inheritance
- Table PerClass Inheritance

## Single Table Inheritance
- Single Table Inheritance adalah menyimpan seluruh Entity untuk relasi IS-A dalam satu table
- Artinya semua kolom di semua Entity akan digabungkan dalam satu table
- Plus nya dari strategi ini adalah mudah dan cepat, tidak butuh melakukan join
- Minus nya, harus membuat semua kolom jadi nullable, karena tiap record hanya milik satu Entity

## Parent Entity
- Saat membuat Entity untuk IS-A Relationship perlu membuat parent Entity nya terlebih dahulu
- Parent entity berisikan attribute yang tersedia di semua Child Entity nya
- Dan khusus untuk Parent Entity, harus menyebutkan strategy Inheritance yang menggunakan annotation Inheritance
- Pada kasus Single Table Inheritance, wajib menggunakan Inheritance Type SINGLE_TABLE

## Child Entity
- Untuk Child Entity, cukup extends class Parent Entity
- Pada kasus Single Table Inheritance, perlu memberi tahu JPA dari kolom dan value apa Child Entity tersebut dipilih
- Oleh karena itu perlu menambahkan annotation DiscriminatorColumn pada Parent
- Dan pada Child Entity, harus menambahkan annotation DiscriminatorValue

## Find Entity IS-A
- Pada kasus relasi IS-A, bisa find data langsung spesifik ke Child Entity, atau lewat Parent Entity
- Selain itu pada kasus jika melakukan Find menggunakan Parent Entity dan ternyata data tersebut adalah Child Entity, bisa konversi secara manual

## Joined Table Inheritance
- Implementasi IS-A adalah menggunakan Join Table
- Yang artinya, tiap Child Entity memiliki table masing-masing, namun akan melakukan Join Primary Key dengan Table Parent Entity
- Pada Joined Table Inheritance, tidak perlu menggunakan Discriminator Column lagi, karena data nya sudah terpisah table


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