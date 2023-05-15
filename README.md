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

## Table Per Class Inheritance
- Strategi terakhir unutk implementasi IS-A adalah dengan Tabel per Class
- Yang artinya tiap Entity akan dibuatkan table masing-masing, artinya Parent Entity dab Child Entity akan memiliki table masing-masing
- Strategi ini mirip seperti dengan JOIN, namun tiap table menyimpan full kolom
- Harap bijak ketika menggunakan strategi ini, walaupun akan jadi lebih cepat kalau ketika langsung melakukan find Child Entity (karena tidak butuh join), tapi saat melakukan find menggunakan Parent Entity, maka akan sangat lambat karena harus melakukan SELECT FROM SELECT

## Mapped Superclass
- Saat membuat Class Entity, kadang ada beberapa Class Entity yang memiliki attribute yang sama, namun bukan bagian dari IS-A Relationship
- Pada kasus OOP biasanya kita membuat Parent Class sebagai class yang berisikan attribute-attribute yang sama
- Pada kasus Entity, kita bisa membuat Parent Class juga, namun kita perlu memberi annotation MapperSuperClass untuk memberi tahu ini hanya sebuah Parent Class tanpa IS-A Relationship
- Biasanya orang-orang yang membuat super class ini menggunakan Abstract Class sebagai parent class nya untuk menandai bahwa ini bukanlah entity ini sebagai parent aja yang nanti akan bisa digunakan oleh entity-entity yang ada

## Locking
- Dalam relational database, locking adalah aksi untuk mencegah data berubah dalam jeda waktu kita membaca data dan menggunakan data
- Terdapat dua jenis locking, Optimistic dan Pessimistic

## Jenis Locking 
- Optimistic Locking adalah proses multiple transaksi, dimana tiap transaksi tidak melakukan lock terhadap data. Namun sebelum melakukan commit transaksi, tiap transaksi akan mengecek terlebih dahulu apakah data sudah berubah atau belum, jika sudah berubah dikarenakan transaksi lain, maka transaksi tersebut akan melakukan rollback
- Pessimistic Locking adalah proses multiple transaksi, dimana tiap transaksi akan melakukan lock terhadap data yang digunakan. Hal ini menyebabkan tiap transaksi harus menunggu data yang akan digunakan jika data tersebut sedang di lock oleh transaksi lain
- JPA mendukung implementasi Optimistic Locking dan Pessimistic Locking

## Optimistic Locking
- Optimistic Locking diimplementasikan dengan cara menambah versi data pada Class Entity untuk memberitahu jika terjadi perubahan data pada Entity
- Pada saat transaksi melakukan commit, transaksi akan melakukan pengecekan versi terlebih dahulu, jika ternyata versi telah berubah di database, secara otomatis transaksi akan melakukan rollback
- Optimistic Locking sangat cepat karena tidak butuh melakukan lock data, sehingga tidak perlu menunggu transaksi yang melakukan lock
- Konsekuensinya, pada Optimistic Locking, transaksi akan sering melakukan rollback jika ternyata data yang di commit sudah berubah

## Version Column
- Saat menggunakan Optimistic Locking, wajib membuat version column yang digunakan sebagai tanda perubahan yang sudah terjadi di data
- JPA mendukung dua jenis tipe data version, Number(Integer, Short, Long) dan Timestamp (java.sql.Timestamp, java.time.Instant)
- Kalau menggunakan Number, bagusnya pake yang Long, karena Long itu nanti jaraknya jauh nilainya, jadi berapa miliar kali di update bisa dihandle sama Long ini
- Untuk menandai bahwa attribute tersebut adalah version column, perlu menambahkan annotation Version
- JPA akan secara otomatis mengupdate attribute version setiap kali melakukan update data tersebut

## Kenapa Optimistic Locking?
- Kenapa bernama Optimistic Locking, hal ini dikarenakan transaksi yang pertama selesai akan optimis bahwa datanya tidak akan diubah oleh transaksi lain

## Pessimistic Locking
- Pada Optimistic Locking, pengecekan versi data dilakukan ketika melakukan commit
- Pada Pessimistic Locking, data akan di lock ketika di select, dan ini menjadikan transaksi lain tidak bisa mengubah data sampai transaksi yang pertama melakukan lock selesai melakukan commit transaksi
- JPA mendukung banyak jenis tipe Lock, namum tetap saja, itu tergantung ke database yang digunakan, bisa saja database yang digunakan tidak mendukung semua jenis Lock yang terdapat di JPA
- Semua jenis Lock terdapat di enum LockModeType

## Jenis Lock Mode Type
- NONE - Tidak ada lock, semua lock terjadi di akhir transaksi ketika data di update
- READ / OPTIMISTIC - Versi entity akan di cek di akhir transaksi (ini adalah Optimistic Locking)
- WRITE / OPTIMISTIC_FORCE_INCREMENT - Versi entity akan dinaikkan secara otomatis walaupun data tidak di update
- PESSIMISTIC_FORCE_INCREMENT - Entity akan di lock secara pessimistic, dan versi akan dinaikkan walaupun data tidak di update
- PESSIMISTIC_READ - Entity akan di lock secara pessimistic menggunakan shared lock(jika database mendukung), SELECT FOR SHARE
- PESSIMISTIC_WRITE - Entity akan di lock secara explicit, SELECT FOR UPDATE

## Kenapa Pessimistic Locking?
- Kenapa bernama Pessimistic Locking, hal ini karena ketika transaksi pertama sudah sukses, bisa saja datanya diubah oleh transaksi kedua walaupun transaksi pertama lebih dulu selesai
- Oleh karena itu disebut pessimistic

## Managed Entity
- Salah satu fitur yang wajib dimengerti ketika menggunakan JPA adalah Managed Entity
- Saat membuat Object Entity secara manual, mana bisa dibilang itu adalah Unmanaged Entity(Entity yang tidak di managed oleh JPA)
- Saat Unmanaged Entity di simpan ke database menggunakan Entity Manager, Secara otomatis objectnya berubah menjadi Managed Entity
- Setiap perubahan yang terjadi pada Managed Entity, secara otomatis akan di update ke database ketika melakukan commit, walaupun tidak melakukan update secara manual.

## Find Managed Entity
- saat melakukan find data ke database pun, secara otomatis object tersebut menjadi Managed Entity
- Artinya setiap perubahan yang kita lakukan, secara otomatis akan di update ketika commit, walaupun tidak melakukan update secara manual

## Detach Entity
- Kadang ada kasus ketika ingin membuat Managed Entity menjadi Unmanaged Entity
- Untuk kasus seperti itu, bisa menggunakan EntityManager.detach(entity)
- Jika sudah menjadi Unmanaged Entity, secara otomatis perubahan yang terjadi di Entity tidak akan disimpan secara otomatis pada saat melakukan commit
- Perubahan yang terjadi di Unmanaged Entity, harus disimpan secara manual menggunakan EntityManager.persist(entity) atau EntityManager.merge(entity)

## Schema Generator
- JPA memiliki fitur untuk membuat Table secara otomatis dari Class Entity yang ada
- Walaupun fitur ini sangat menarik, namun sangat tidak disarankan untuk membuat table secara otomatis, karena akan sulit melakukan tracking perubahan schema database nya
- Sangat disarankan menggunakan database versioning seperti flywaydb
- https://flywaydb.org
- Untuk menggunakan fitur Hibernate Schema Generator, bisa gunakan property jakarta.persistance.schema-generation.database.action di file persistance.xml dengan value:
- none, tidak melakukan apapun
- create, membuat schema
- drop, menghapus schema
- drop-and-create, menghapus schema dan membuat nya

## JPA Query Language
- Untuk melakukan Query ke Database, JPA memiliki standarisasi Query Language, jadi tidak menggunakan SQL yang spesifik ke database yang digunakan
- Hal ini dikarenakan dengan menggunakan JPA, bisa mengganti-ganti databse yang akan digunakan, sehingga untuk Query Language nya pun perlu dibuat standar
- Namun tidak perlu khawatir, karena JPA Query Language sangat mirip dengan SQL

## Query
- Saat menggunakan JPA QL, object yang dihasilkan adalah object dari class Query
- Class Query mirip seperti PreparedStatement, dimana bisa menambahkan parameter jika JPA QL yang dibuat membutuhkan parameter

## TypedQuery<T>
- Class Query akan mengembalikan Object sehingga perlu melakukan konversi tipe dara secara manual
- Jika melakukan query yang sudah jelas Entity nya, sangat disarankan menggunakan TypedQuery

## Select Query
- Untuk melakukan select query di JPA QL, tidak menyebutkan nama table, melainkan nama Entity nya
- Selain itu, dalam JPA jika ingin meng-select semua attribute, tidak menggunakan tanda * (bintang), melainkan menggunakan nama alias dari Entity nya

## Where Clause
- Saat ingin menggunakan where clause pada select query, bisa menggunakan nama attribute di Entity nya, bukan nama kolom di table
- Jika attribute nya berupa embedded class, bisa menyebut object di dalam nya menggunakan tanda . (titik)

## Parameter
- Jika membutuhkan parameter pada where clause, bisa menggunakan tanda : (titik dua) diikuti dengan nama parameter

## Join Clause
- Melakukan join di JPA QL sangat mudah, karena informasi join nya sudah terdapat di Entity Class nya
- Hanya cukup gunakan perintah join diikuti attribute di Entity

## Join Fetch
- Secara default, saat melakukan join, juga bisa memaksa data table yang di join untuk di select sehingga tidak perlu melakukan select lagi untuk mendapatkan datanya

## Order By Clause
- Sama seperti di SQL. di JPA QL juga bisa menambahkan Order By Clause

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
For Unit Test
```shell
docker run --rm --name belajar-java-persistance-api-test-db \ 
-e POSTGRES_DB=belajar_java_persistance_api_test \
-e POSTGRES_USER=dani \
-e POSTGRES_PASSWORD=dani \
-e PGDATA=/var/lib/postgresql/data/pgdata \
-v "$PWD/belajar-java-persistance-api-test-db-data:/var/lib/postgresql/data" \
-p 5433:5432 \
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