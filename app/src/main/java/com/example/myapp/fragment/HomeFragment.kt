package com.example.myapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.DataHidroponik
import com.example.myapp.R
import com.example.myapp.adapter.DataHidroponikAdapter
import com.example.myapp.databinding.FragmentHomeBinding
import java.util.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private var mList = ArrayList<DataHidroponik>()
    private lateinit var adapter: DataHidroponikAdapter

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        searchView = view.findViewById(R.id.searchView)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        addDataToList()
        adapter = DataHidroponikAdapter(mList)
        recyclerView.adapter = adapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
            filterList(newText)
            return true
            }

        })

    }
    private fun filterList(query : String?){

        if(query != null){
            val filteredList = ArrayList<DataHidroponik>()
            for (i in mList){
                if(i.title.lowercase(Locale.ROOT).contains(query)){
                    filteredList.add(i)
                }
            }


            if (filteredList.isEmpty()){
                Toast.makeText(activity, "Tidak Ada Data", Toast.LENGTH_SHORT).show()

            } else {
                adapter.setFilteredList(filteredList)
            }
        }
    }

    private fun addDataToList() {
        mList.add(
            DataHidroponik(
                "Tomat Hidroponik",
                R.drawable.tomat,
            "CARA PENANAMAN TOMAT HIDROPONIK\n" +
                    "\n" +
                    "1. Siapkan Alat dan Bahan:\n" +
                    "\n" +
                    "* Bibit tomat,\n" +
                    "* Rockwool,\n" +
                    "* Netpot,\n" +
                    "* Nutrisi AB Mix,\n" +
                    "* Bak nutrisi,\n" +
                    "* Kain flanel/pipa.\n" +
                    "\n" +
                    "2. Pertama melakukan pemilihan bibit unggul dengan merendam benih/bibit tomat yang telah kering tersebut selama 15-20 menit. Kemudian, terdapat bibit yang mengapung dan ada pula yang terendam. Bibit yang baik adalah yang terendam sementara yang terapung sebaiknya tidak digunakan.\n" +
                    "\n" +
                    "3. Selanjutnya tahap penyemaian bibit, Siapkan rockwool yang sudah dipotong, Lubangi masing-masing rockwool dengan tusuk gigi untuk memasukkan bibit nantinya. Taruh rockwool tersebut pada tray semai atau nampan. Masukkan bibit pada lubang yang telah dibuat. Siram rockwool dengan air biasa (air tanah) hingga basah. Tutup tray semai dengan plastic hitam supaya cahaya tidak masuk dan simpan di tempat yang gelap selama 2×24 jam.\n" +
                    "\n" +
                    "4. Kemudian, setelah 2 hari akan terlihat proses pertumbuhan kecambah. Pada proses ini pindahkan tray semai ke tempat yang ada sinar matahari. Proses ini hingga 21-28 hari.\n" +
                    "\n" +
                    "5. Tahap pemindahan ke sistem hidroponik. Setelah tunas sejati muncul (daunnya sudah lebih dari 2) maka bibit dari proses penyemaian siap dipindahkan pada sistem hidroponik. Sistem hidroponik yang biasanya digunakan untuk tomat, seperti deep flow system (DFT) atau drip system.\n" +
                    "\n" +
                    "6. Untuk proses pemindahannya yaitu, Pisahkan rockwool dengan rockwool lainnya. Taruh  rockwool pada sistem hidroponik dengan bantalan kerikil, arang, atau bisa pula menggunakan netpot. Untuk perhitungan kadar nutrisi AB mix untuk tomat sebelum berbunga sekitar 1300 ppm.\n" +
                    "\n" +
                    "7. Setelah tanaman tomat cukup tinggi, Anda bisa memberi penopang (misalnya, tali) agar tumbuhnya tetap tegak atau patah. Disaat tomat telah berbunga tingkatkan ppm air nutrisi menjadi 2000 ppm. Hal ini supaya nutrisi untuk buahnya lebih optimal.\n" +
                    "\n" +
                    "8. Perawatan lebih ditingkatkan pada saat tanaman tomat telah berbuah dan sebaiknya potong ranting tanaman yang tidak berbuah. Hal ini supaya konsentrasi nutrisi tertuju lebih pada buah. TIngkatkan ppm air nutrisi sekitar 3600 ppm.\n" +
                    "\n" +
                    "9. Masa Panen Berkisar hingga 80-90 hari tomat hidroponik mulai berbuah warna merah. Hal ini menandakan tomat siap untuk dipanen"
            )
        )
        mList.add(
            DataHidroponik(
                "Cabai Hidroponik",
                R.drawable.cabai,
                "CARA PENANAMAN CABAI HIDROPONIK\n" +
                        "\n" +
                        "1. Siapkan Alat dan Bahan:\n" +
                        "\n" +
                        "* Benih Cabai, \n" +
                        "* Tissue Basah,\n" +
                        "* Sekam Bakar,\n" +
                        "* Nutrisi AB Mix,\n" +
                        "* Netpot & kain perca,\n" +
                        "* Serbuk Srabut Kelapa,\n" +
                        "* Kompos,\n" +
                        "* Bak Nutrisi/Ember.\n" +
                        "\n" +
                        "2. Persiapan Benih Cabe, Anda bisa menggunakan biji-biji cabai yang ada di dapur, atau biji cabai kering di ecommerce, Adapun proses penyemaian benih cabe dalam cara menanam cabe hidroponik adalah sebagai berikut:\n" +
                        "\n" +
                        "3. Rendam benih cabe yang akan disemai, proses ini bertujuan agar biji cabe lebih cepat berkecambah. Pilihlah benih yang tenggelam, jika ada yang mengambang lebih baik dibuang saja. Masukan benih cabe kedalam kain atau tisuue basah, kemudian bungkus selama seharian, hal ini bertujuan agar cepat berkecambah. Jika kecambah mulah tumbuh segera lakukan penyemaian.\n" +
                        "\n" +
                        "4. Pada proses penyemaian, gunakanlah media tanam yang terdiri dari campuran tanah, kompos dan arang sekam, gunakan perbandingan 1:1:1. Lanjutkan dengan menyemai biji cabai secara teratur dengan jarak 3 x 3 cm, lalu tutuplah dengan tanah halus dan siram secukupnya dengan menggunakan air yang dimasukkan ke dalam sprayer agar tanah tidak terlalu basah. Pastikan proses penyemaian tidak terkena sinar matahari langsung.\n" +
                        "\n" +
                        "5. Tahap pemindahan ke sistem hidroponik. Setelah tunas sejati muncul (daunnya sudah lebih dari 2) maka bibit dari proses penyemaian siap dipindahkan pada sistem hidroponik. Sistem hidroponik yang biasanya digunakan untuk tomat, seperti deep flow system (DFT) atau drip system.\n" +
                        "\n" +
                        "6. Siapkan Media Tanam Hidroponik, Untuk media tanam cabai hidroponik, Anda bisa menggunakan campuran arang sekam, dan serbuk sabut kelapa dengan perbandingan 1:1. Setelah keduanya tercampur dengan rata, maka saatnya untuk menyiapkan sumbu agar tanaman hidroponik sistem wick dapat tumbuh secara sempurna. Sistem wick merupakan sistem tanam hidroponik dengan sumbu yang membantu agar air terserap dan diterima oleh tanaman. Pada metode ini, kain yang memiliki daya serap tinggi ialah kain flannel.\n" +
                        "\n" +
                        "7. Siapkan Tandon dan Nutrisi, Wadah tandon nutrisi bisa menggunakan barang-barang bekas yang ada di rumah seperti toples / ember yang sudah tidak terpakai lagi.\n" +
                        "\n" +
                        "8. Bibit cabai bisa dipindahkan pada usia 25-30 hari setelah proses semai. Untuk memindahkannya juga diperlukan trik yang tepat. Cara terbaik ialah dengan mencungkil bersama medianya agar tidak merusak akar. Lalu bersihkan akar dari media semai dengan mencuci akar bibit cabai secara hati-hati.\n" +
                        "\n" +
                        "9. Setelah dibersihkan, tanam kembali bibit cabai di dalam pot yang telah Anda siapkan. Simpan pot di tempat yang teduh, siram dengan air jika media tanamnya kering. Biarkan cabai hidroponik di tempat tersebut selama lima hari dan sesekali dijemur untuk mendapatkan sinar matahari.\n" +
                        "\n" +
                        "10. Berikanlah nutrisi yang khusus untuk tanaman hidroponik dan gunakanlah air sumur atau air endapan agar tanaman dapat tumbuh dengan sehat. Selain itu, Anda juga harus rajin menjaga kesehatan tanaman cabai hidroponik agar terbebas dari hama."
            )
        )
        mList.add(
            DataHidroponik(
                "Bayam Hidroponik",
                R.drawable.bayam,
                "CARA PENANAMAN BAYAM HIDROPONIK\n" +
                        "\n" +
                        "1. Siapkan alat dan Bahan:\n" +
                        "\n" +
                        "* Benih Bayam,\n" +
                        "* Nutrisi AB Mix,\n" +
                        "* Besek Pelastik,\n" +
                        "* Baskom yang berdiameter sedikit lebih kecil dari Besek agar tidak tenggelam.\n" +
                        "\n" +
                        "2. Langkah pertama yaitu dengan menyemai benih bayam, Umumnya, penyemaian benih bayam ini termasuk cepat. Sehingga untuk bayam masuk ke tahap panen juga tidak membutuhkan waktu yang lama, kurang lebih 1 bulan.\n" +
                        "\n" +
                        "3. Potong kain flanel sesuai bentuk bakul besek, lalu masukan kain tesebut kedalam besek dan basahi kain flanel yang sudah berada didalam besek.\n" +
                        "\n" +
                        "4. Taburi benih bayam sekitar 20 benih tau secukupnya ke bakul besek yang telah terisi oleh kain flanel. Setelah itu, isi baskom dengan air bersih dan taruh bakul besek diatas baskom, usahakan air mengenai permukaan kain flanel.\n" +
                        "\n" +
                        "5. Setelah proses tersebut sudah dilakukan, pindahkan media tanam di tempat gelap. Dalam waktu 3 hari, nantinya benih akan pecah dan tumbuh kecambah. Selama waktu 3 hari ini, kita harus rutin menyemprotkan air ke benih bayam, supaya tanaman cepat tumbuh dan tidak rusak.\n" +
                        "\n" +
                        "6. Jika benih sudah pecah dan tumbuh kecambah, pindahkan media tanam ke tempat yang cukup terkena cahaya sinar matahari.\n" +
                        "\n" +
                        "7. Bila tanaman bayam sudah memunculkan daun sekitar 5 helai, kita dapat mengganti isi ember dengan campuran air nutrisi AB mix. Caranya, cukup tuangkan nutrisi AB mix 1 botol ke dalam air yang terdapat di ember. Kemudian aduk merata, setelah itu letakkan kembali bakul nasi di atas ember tersebut. Pastikan kain flanel menyentuh air yang terdapat di ember.\n" +
                        "\n" +
                        "8. Bila bayam sudah tumbuh akar, pastikan akar selalu terkena air nutrisi AB mix supaya bayam cepat tumbuh. Selain itu, semprot tanaman bayam 2 kali sehari setiap pagi dan sore.\n" +
                        "\n" +
                        "9. Proses panen. Tanaman bayam termasuk cepat dalam menumbuhkan sayuran yang siap dikonsumsi. Bayam hanya membutuhkan waktu kurang lebih 1 bulan untuk sampai ke tahap panen setelah proses penanaman.Cara memanen tanaman bayam hidroponik dengan langsung mencabut bayam yang sudah tumbuh dengan hati-hati."
            )
        )
        mList.add(
            DataHidroponik(
                "Kangkung Hidroponik",
                R.drawable.kangkung,
            "CARA PENANAMAN KANGKUNG HIDROPONIK\n" +
                    "\n" +
                    "1. Siapkan Alat dan Bahan:\n" +
                    "\n" +
                    "* Benih Kangkung,\n" +
                    "* Nutrisi Hidroponik,\n" +
                    "* Besek Pelastik,\n" +
                    "* Baskom yang berdiameter sedikit lebih kecil dari Besek agar tidak tenggelam.\n" +
                    "\n" +
                    "2. Langkah pertama yaitu dengan menyemai benih kangkung, Pertama Ambil baskom yang sudah diisi air kemudian taruh besek diatasnya.\n" +
                    "\n" +
                    "3. Masukkan benih ke dalam besek, taburkan hingga merata. Jangan terlalu banyak atau sedikit. Bila lubang-lubang besek lebih besar dari pada benih, bisa tambahkan tisu pada dasar besek. Sebelum ditabur benih juga dapat di rendam beberapa jam terlebih dulu.\n" +
                    "\n" +
                    "4. Taruh baskom di tempat yang teduh hingga benih berkecambah kemudian jemur dibawah matahari.\n" +
                    "\n" +
                    "5. Pada tahap ini adalah tahap perawatan dengan pemberian nutrisi hidroponik AB Mix dan jangan sampai telat, cara menggunakannya yaitu Siapkan air baku sebanyak yang dibutuhkan sesuai dengan tabel pemakaian nutrisi. Biasanya menggunakan perbandingan 1: 5:5, dan berlaku kelipatan. Tambahkan stok A dan stok B kemudian aduk hingga tercampur. Lalu nutrisi yang sudah tercampur ditambahkan pada baskom.\n" +
                    "\n" +
                    "6. Pada tahap perawatan kangkung sudah memasuki umur 2 minggu, berarti harus dilakukan penambahan nutrisi. Kamu harus menambah jumlah nutrisi yang awalnya hanya 5 ml per 1 liter, sekarang menjadi 10 ml per 1,5 liter.\n" +
                    "\n" +
                    "7. Setelah berumur 21 - 25 hari dan memiliki ciri - ciri tanaman yang sudah bisa dipanen, maka kangkung siap dipanen. Cara panennya ada 2 macam, yaitu dengan mencabut langsung sampai akarnya ikut (mati). Atau dengan memotong 2/3 bagian batang atas, dan beri nutrisi lagi maka dia akan tumbuh lagi."

            )
        )
        mList.add(
            DataHidroponik(
                "Selada Hidroponik",
                R.drawable.selada,
                "CARA PENANAMAN SELADA HIDROPONIK\n" +
                        "\n" +
                        "1. Siapkan Alat dan Bahan:\n" +
                        "\n" +
                        "* Benih Selada,\n" +
                        "* Pot,\n" +
                        "* Kain Flanel,\n" +
                        "* Nutrisi AB Mix,\n" +
                        "* Tandon Air Nutrisi,\n" +
                        "* Air Sumur,\n" +
                        "* Rockwool.\n" +
                        "\n" +
                        "2. Benih selada disemai terlebih dahulu menggunakan wadah/mangkuk, isi wadah/ mangkuk tersebut dengan air biasa kemudian basahi media hingga benar-benar basah, masukkan benih-benih selada ke dalam wadah/ mangkuk berisi air tersebut,tunggu 12-24 jam hingga benih pecah.\n" +
                        "\n" +
                        "3. Tahap penyemaian selanjutnya yaitu menempatkan benih di dalama rockwoll yang sudahdi iris dengan ukuran sekitar 2,5 x 2,5 x 2,5 cm dan dilubangi dengan keedalaman 1 cm, letakan rockwoll pada baki basahai rockwoll dengan air dan taruh di teeempat yang terkena sinar lalu tunggu 10 hari.\n" +
                        "\n" +
                        "4. Siapkan tandon, larutan nutrisi, sumbu dan media tanam. Sumbu (Kain fanel) dipasang pada pot/net dengan panjang, sumbu disesuaikan dengan kedalaman tandon. Usahakan menyentuh dasar tandon,\n" +
                        "\n" +
                        "5. Setelah mencapai usia 10 hari, saatnya pemindahan ke sistem hidorponik yang sebelumnya telah kita buat, Masukkan rockwool tersebut pada netpot.pastikan bahwak rockwool menyentuh kain flanel.\n" +
                        "\n" +
                        "6. Isi bak sistem hidroponik dengan air nutrisi 500 ppm. Jumlah ini setara dengan 2,5 liter nutrisi A dan B serta 1 liter air. (Nilai ini dipertahankan hinga 12 HST). Letakan selada hidroponik yang baru ditanam pada tempat yang teduh selama 2-3 hari supaya beradaptasi terlebih dahulu,\n" +
                        "\n" +
                        "7. Selanjutnya perkenalkan dengan sinar matahari secara bertahap, jika cuaca terlalu panas, letakan selada hidroponik di bawah naungan paranel.\n" +
                        "\n" +
                        "8. Setelah 12 HST naikkan kadar kepekatan menjadi 1000 ppm.\n" +
                        "\n" +
                        "9. Setelah memasuki 20 HST naikkan kadar kepekatan menjadi 1200 ppm. Nilai ini tetap hingga masa panen tiba.\n" +
                        "\n" +
                        "10. Masa Panen Sayur Sawi Hidroponik adalah sekitar 30-40 hari dengan cara dicabut beserta akarnya."
            )
        )
        mList.add(
            DataHidroponik(
                "Sawi Hidroponik",
                R.drawable.sawi,
            "CARA PENANAMAN SELADA HIDROPONIK\n" +
                    "\n" +
                    "1. Siapkan Alat dan Bahan:\n" +
                    "\n" +
                    "* Rockwoll,\n" +
                    "* Pinset,\n" +
                    "* Benih Sawi\n" +
                    "* Sprayer/ Penyemprot Air,\n" +
                    "\n" +
                    "2. Siapkan media tanam, potong rockwool setebal 2,5 cm. Iris memanjang sedalam kurang lebih 1 cm menjadi 3 bagian dan iris melintang menjadi 6 bagian sedalam 1 cm. Lihat gambar di bawah ini untuk detailnya.\n" +
                    "\n" +
                    "3. Masukkan benih ke dalam lubang dengan posisi kecambah di bawah (1 lubang diisi 1 benih).\n" +
                    "\n" +
                    "4. Setelah semua lubang terisi, basahi rockwool menggunakan sprayer/semprotan dengan kekuatan air yang lembut. Taruh semaian di tempat terbuka yang cukup sinar matahari.\n" +
                    "\n" +
                    "5. Pada tahap ini adalah tahap perawatan semai, jaga rockwool agar tetap lembab (tidak terlalu basah dan tidak terlalu kering)\n" +
                    "\n" +
                    "6. Setelah sawi berdaun 3 atau lebih, sudah waktunya untuk dipindah ke sistem hidroponik dan diberi nutrisi hidroponik. Umur sawi sekitar 7-10 hari setelah semai.\n" +
                    "\n" +
                    "7. Step ini adalah tahap pindah tanam. Pisahkan/ potong rockwool berdasarkan irisan yang dibuat pada step awal. Apabila susah untuk memisahkan rockwool, maka bisa menggunakan cutter untuk memotong rockwool sampe terpisah. Taruh potongan rockwool ke dalam netpot yang sudah dikasih flanel (sumbu).\n" +
                    "\n" +
                    "8. Tempatkan netpot ke dalam sistem hidroponik, pada level ini konsentrasi nutrisi adalah 600 ppm. Konsentrasi nutrisi meningkat setelah 5 hari setelah tanam menjadi 800 ppm. Setelah memasuki 10 hari setelah tanam, tingkatkan konsentrasi nutrisi menjadi 1200 ppm. Jaga agar ppm nutrisi selalu stabil pada angka tersebut hingga panen.\n" +
                    "\n" +
                    "9. Masa Panen Sayur Sawi Hidroponik adalah sekitar 30-40 hari dengan cara dicabut beserta akarnya."
            )
        )


    }

}
