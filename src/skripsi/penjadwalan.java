/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package skripsi;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javax.swing.SwingUtilities;
import jxl.read.biff.BiffException;
//import userinterface.formProses;

/**
 *
 * @author Yamlikho Karma
 */
public class penjadwalan {

    static int id[] = null;
    static int ukrnKromosom;
    static int popsize;
    static int iterasiMax;
    static int offspringCrossover;
    static int offspringMutasi;
    static double cr;
    static double mr;
    static int bestkro[];
    static int id_kelas[] = null; //berisi data id kelas, mulai dari 0, id ini digunakan sebagai index dalam array
    static int penugasan[][] = null;
    static double fitness[] = null;
    static int pelanggaran[][] = null;
    static int tmpPelanggaran[] = null;
    static int pelanggaranMax[] = null;
    static String nama_kelas[] = null;
    static int lokasi[] = null; //index dari lokasi sama dengan index dengan ruangan
    static Integer idGuruTugas[] = null;
    static double fitnesMax = 0.0;
    static double ratarataFitness = 0.0;
    static int[][] requestGuru = null;
    static double bobotHard;
    static double bobotSoft;
    static String[] namaMapel;
    // static String inputFile;

    public penjadwalan(String inputFile, int pz, double cr, double mr) throws SQLException, IOException, BiffException {
        //sumber dari excel sudah siap, lalu mengganti class nya ke class preprosesing
        preprosesing proses = new preprosesing();
        proses.setInputFile(inputFile);
        id = proses.getId();
        ukrnKromosom = id.length;
        popsize = pz;
        id_kelas = proses.getIdKelas();
        penugasan = proses.getPenugasan();
        namaMapel = proses.getNamaMapel();
        this.cr = cr;
        this.mr = mr;
        bobotHard = 0.0035;
        bobotSoft = 0.00075;
        offspringCrossover = (int) Math.ceil(cr * popsize);
        offspringMutasi = (int) Math.ceil(mr * popsize);
        nama_kelas = proses.getNamaKelas();
        lokasi = proses.getLokasi();
        idGuruTugas = proses.getIdGuru();
        requestGuru = proses.getRequestGuru();
    }

    public static int[] getBestkro() {
        return bestkro;
    }

    public static void setBestkro(int[] bestkro) {
        penjadwalan.bestkro = bestkro;
    }

    
    public static String[] getNamaMapel() {
        return namaMapel;
    }

    public static int[][] getPenugasan() {
        return penugasan;
    }

    public static double getBobotHard() {
        return bobotHard;
    }

    public static double getBobotSoft() {
        return bobotSoft;
    }

    public static void setBobotHard(double bobotHard) {
        penjadwalan.bobotHard = bobotHard;
    }

    public static void setBobotSoft(double bobotSoft) {
        penjadwalan.bobotSoft = bobotSoft;
    }

    public static void setPjgKromosom(int ukrnKromosom) {
        penjadwalan.ukrnKromosom = ukrnKromosom;
    }

    public static int[][] getPelanggaran() {
        return pelanggaran;
    }

    public static int[] getTmpPelanggaran() {
        return tmpPelanggaran;
    }

    public static int[] getPelanggaranMax() {
        return pelanggaranMax;
    }

    public static void setPelanggaran(int[][] pelanggaran) {
        penjadwalan.pelanggaran = pelanggaran;
    }

    public static void setTmpPelanggaran(int[] tmpPelanggaran) {
        penjadwalan.tmpPelanggaran = tmpPelanggaran;
    }

    public static void setPelanggaranMax(int[] pelanggaranMax) {
        penjadwalan.pelanggaranMax = pelanggaranMax;
    }

    public void setPopsize(int popsize) {
        penjadwalan.popsize = popsize;
    }

    public static String[] getNama_kelas() {
        return nama_kelas;
    }

    public void setCr(double cr) {
        penjadwalan.cr = cr;
        offspringCrossover = (int) Math.ceil(cr * popsize);
    }

    public void setMr(double mr) {
        penjadwalan.mr = mr;
        offspringMutasi = (int) Math.ceil(mr * popsize);
    }

    public void setIterasiMax(int iterasiMax) {
        penjadwalan.iterasiMax = iterasiMax;
    }

    public static int getPjgKromosom() {
        return ukrnKromosom;
    }

    public static int getPopsize() {
        return popsize;
    }

    public static int getOffspringCrossover() {
        return offspringCrossover;
    }

    public static int getOffspringMutasi() {
        return offspringMutasi;
    }

    public int getIterasiMax() {
        return iterasiMax;
    }

    public static double getCr() {
        return cr;
    }

    public static double getMr() {
        return mr;
    }

    public static void setFitnesMax(double fitnesMax) {
        penjadwalan.fitnesMax = fitnesMax;
    }

    public static void setRatarataFitness(double ratarataFitness) {
        penjadwalan.ratarataFitness = ratarataFitness;
    }

    public static double getFitnesMax() {
        return fitnesMax;
    }

    public static double getRatarataFitness() {
        return ratarataFitness;
    }

    public static void setFitness(double[] fitness) {
        penjadwalan.fitness = fitness;
    }

    public static double[] getFitness() {
        return fitness;
    }

    public static int[][] inisialisasi() throws SQLException {
        int kromosom[][] = new int[popsize][ukrnKromosom];
        for (int i = 0; i < popsize; i++) { //looping sebanyak popsize (jumlah individu yang akan dibangkitkan)
            Random rand = new Random();
            for (int j = 0; j < ukrnKromosom; j++) { //loop sejumlah panjang kromosom untuk mengisi seluruh gen pada suatu kromosom
                int x;
                do { //do while digunakan agar tidak ada gen yang kembar
                    x = rand.nextInt(ukrnKromosom) + 1; //random antara nilai 1 sampai 9
                } while (in_array(kromosom[i], x));
                kromosom[i][j] = x;
            }
        }
        return kromosom;
    }

    public static boolean in_array(int[] arr, int targetValue) { //method untuk cek keberadaan suatu value dalam suatu array
        for (int s : arr) {
            if (s == targetValue) {
                return true;
            }
        }
        return false;
    }

    public static boolean in_arrayList(ArrayList<Integer> arr, int targetValue) {
        for (int s : arr) {
            if (s == targetValue) {
                return true;
            }
        }
        return false;
    }

    public static int[][] bentuk_jadwal(int kromosom[]) {
        int jadwal[][] = new int[id_kelas.length][11]; //nilai 11 adalah jumlah jam dalam seminggu
        int cekindex[] = new int[id_kelas.length + 1]; //untuk menyimpan informasi index terakhir dalam slot jadwal yang sudah terisi
        for (int i = 0; i < kromosom.length; i++) {
          
            for (int j = 0; j < penugasan[kromosom[i] - 1][3]; j++) { //mengambil index penugasan ke 3 yang berisi jumlah jam mengajar
               
                int kodekelas = penugasan[kromosom[i] - 1][1] - 1; //mengambil kode kelas
                
                jadwal[kodekelas][cekindex[kodekelas]] = kromosom[i];
                
                cekindex[kodekelas]++; //untuk menyambung ke index selanjutnya. jika sebelumnya terisi sampai index ke 5, proses selanjutnya biar terisi ke index 6 dst               
            } 
        }
        return jadwal;
    } //bentuk jadwal untuk satu kromosom

    public static int[][] bentuk_jadwal_modif(int kromosom[]) {
        int jadwal[][] = new int[id_kelas.length][10]; //nilai 10 adalh jumlah jam dalam seminggu
        int cekindex[] = new int[id_kelas.length + 1]; //untuk menyimpan informasi index terakhir dalam slot jadwal yang sudah terisi
        for (int i = 0; i < kromosom.length; i++) {
            for (int j = 0; j < penugasan[kromosom[i]][3]; j++) { //mengambil index penugasan ke 3 yang berisi jumlah jam mengajar
                int kodekelas = penugasan[kromosom[i]][1];
                jadwal[kodekelas][cekindex[kodekelas]] = penugasan[kromosom[i]][4];
                cekindex[kodekelas]++; //untuk menyambung ke index selanjutnya. jika sebelumnya terisi sampai index ke 5, proses selanjutnya biar terisi ke index 6 dst
            }
        }
        return jadwal;
    } //bentuk jadwal untuk satu kromosom

    //kontrain1 untuk mendeteksi guru yang kress
    public static int cek_konstrain1(int jadwal[][]) { //jumlah konstrain ke 1 untuk satu kromosom
        int jmlkresguru = 0;
        for (int i = 0; i < jadwal[0].length - 1; i++) { //loop sepanjang jumlah jam pelajaran dalam seminggu           
            int[] cekdupguru = new int[id_kelas.length];           
            for (int j = 0; j < jadwal.length; j++) { //mengisikan id guru mengajar tiap kelas tiap jam               
                cekdupguru[j] = penugasan[jadwal[j][i] - 1][4]; //mengambil kode guru pada tiap jam dan tiap kelas                
            }
            Arrays.sort(cekdupguru);
            int previous = -1;
            int dupCount = 0;
            for (int j = 0; j < cekdupguru.length; ++j) {
                if (cekdupguru[j] == previous) {
                    ++dupCount;
                } else {
                    previous = cekdupguru[j];
                }
            }
            jmlkresguru = jmlkresguru + dupCount;
        }        
        return jmlkresguru;
    }
    //konstrain 2 untuk mendeteksi lab yang kres

    //request guru
    public static int cek_konstrain2(int jadwal[][]) {
        int jmlpelanggaran = 0;
        for (int i = 0; i < jadwal.length; i++) {
            for (int j = 0; j < requestGuru.length; j++) {
                int kodeGuru = requestGuru[j][0];
                int jam = requestGuru[j][1];
                if (penugasan[jadwal[i][jam]-1][4] == kodeGuru) {
                    jmlpelanggaran++;
                }
            }
        }
        return jmlpelanggaran;
    }

    //untuk mendeteksi guru yang berpindah lokasi dengan jeda waktu hanya 1 jam
    public static int cek_konstrain3(int jadwal[][]) {
        int jmlpelanggaran = 0;
        List<Integer> idGguru = Arrays.asList(idGuruTugas);
        int[][] jadwalGuru = new int[idGuruTugas.length][jadwal[0].length]; //menyimpan jadwal berdasarkan guru
        for (int i = 0; i < jadwal[0].length - 1; i++) { //loop jam ke
            for (int j = 0; j < jadwal.length; j++) {  //loop kelas
                int tmpKodeGuru = penugasan[jadwal[j][i] - 1][4]; //mengambil kode guru dari kode penjadwalan
                jadwalGuru[idGguru.indexOf(tmpKodeGuru)][i] = jadwal[j][i]; //dari kode guru yang didapatkan di tmpKodeguru kemudian disimpan informasi jadwal pada masing-masing guru
            }
        }
        //dari jadwal mengajar guru kemudian dicek pada jadwal yang berturut-turut.
        for (int i = 0; i < jadwalGuru.length; i++) {
            for (int j = 1; j < jadwalGuru[i].length; j++) {
                if ((jadwalGuru[i][j] != jadwalGuru[i][j - 1]) && jadwalGuru[i][j - 1] != 0 && jadwalGuru[i][j] != 0) {
                    if (lokasi[penugasan[jadwalGuru[i][j - 1]-1][2]] != lokasi[penugasan[jadwalGuru[i][j]-1][2]]) { //lokasi[penugasan[jadwalGuru[i][j]][2]] artinya adalah lokasi untuk ruangan dengan kode ruangan diambil dari prnugasan[jadwalguru[i][j]][2] 
                        jmlpelanggaran++;
                    }
                }
            }
        }
        return jmlpelanggaran;
    }

    public static double fitness(int jadwal[][]) {
        double fitnes = 0.0;
        int jmlPelanggaran1 = cek_konstrain1(jadwal);
        int jmlPelanggaran2 = cek_konstrain2(jadwal);
        int jmlPelanggaran3 = cek_konstrain3(jadwal);
        // rumus fitness
        fitnes = (double) 1 / (1 + ((jmlPelanggaran1 * bobotHard) + (jmlPelanggaran2 * bobotHard) + (jmlPelanggaran3 * bobotSoft)));
        int pel[] = {jmlPelanggaran1, jmlPelanggaran2, jmlPelanggaran3};
        BigDecimal big = new BigDecimal(fitnes);
        big = big.setScale(12, RoundingMode.HALF_UP);
        double fit = big.doubleValue();
        setTmpPelanggaran(pel);
        return fit;
    }

    public static int[][] crossover(int[][] kromosom) {
        int induk1, induk2;
        int[][] child = new int[offspringCrossover][ukrnKromosom];
        int i = 0; //i adalah child ke  
        do {
            //me-random 2 induk
            Random rand = new Random();
            induk1 = rand.nextInt(popsize - 1); //random dari 0 - (popsize-1);
            do {
                induk2 = rand.nextInt(popsize - 1);
            } while (induk1 == induk2);
            int cut_point = rand.nextInt(ukrnKromosom - 1);
//pertama
            int[] tmp_child = new int[ukrnKromosom];
            //mengambil sebagian kromosom dari induk1
            int tCut1 = cut_point;
            System.arraycopy(kromosom[induk1], 0, tmp_child, 0, tCut1);
            //mengambil kromosom dari induk2
            for (int j = 0; j < ukrnKromosom; j++) {
                if (!in_array(tmp_child, kromosom[induk2][j])) {
                    tmp_child[tCut1++] = kromosom[induk2][j];
                }
            }
            child[i] = tmp_child;
            i++;
            if (i == offspringCrossover) {
                break;
            }
//kedua     
            int tCut2 = cut_point;
            int[] tmp_child1 = new int[ukrnKromosom];
            int pTmp = induk1;
            induk1 = induk2;
            induk2 = pTmp;
            //mengambil sebagian kromosom dari induk1
            System.arraycopy(kromosom[induk1], 0, tmp_child1, 0, tCut2);
            //mengambil kromosom dari induk2
            for (int j = 0; j < ukrnKromosom; j++) {
                if (!in_array(tmp_child1, kromosom[induk2][j])) {
                    tmp_child1[tCut2++] = kromosom[induk2][j];
                }
            }
            child[i] = tmp_child1;
            i++;

        } while (i < offspringCrossover);
        return child;
    }

    public static int[][] reproduksi(int[][] kromosom) {
        int[][] offspring = new int[offspringCrossover + offspringMutasi][ukrnKromosom];//proses crossover
        int[][] childCrossover = crossover(kromosom);
        System.arraycopy(childCrossover, 0, offspring, 0, childCrossover.length);//proses mutasi
        int[][] childMutasi = mutasi(kromosom);
        System.arraycopy(childMutasi, 0, offspring, childCrossover.length, childMutasi.length);

        return offspring;
    }

    public static int[][] mutasi(int[][] kromosom) {
        int induk, xp1, xp2;
        int[][] child = new int[offspringMutasi][ukrnKromosom];
        for (int i = 0; i < offspringMutasi; i++) {
            //random induk
            Random rand = new Random();
            induk = rand.nextInt(popsize - 1);
            //random xp1 dan xp2 (posisi gen yang akan ditukar)
            xp1 = rand.nextInt(ukrnKromosom - 1);
            do {
                xp2 = rand.nextInt(ukrnKromosom - 1);
            } while (xp1 == xp2);
            //penukaran posisi gen
            int[] tmp_child = new int[kromosom[induk].length];

            System.arraycopy(kromosom[induk], 0, tmp_child, 0, kromosom[induk].length);
            int tmp_gen = tmp_child[xp1];
            tmp_child[xp1] = tmp_child[xp2];
            tmp_child[xp2] = tmp_gen;
            child[i] = tmp_child;
        }
        return child;
    }

    public static int[][] evaluasi(int[][] kro, int[][] childCross, int[][] childMut) {
        int populasiGabungan[][] = new int[popsize + offspringCrossover + offspringMutasi][ukrnKromosom];//wadah untuk setiap populasi (semua induk dan semua anak) pada setiap iterasi
        //proses menggabungkan induk dan hasil reproduksi
        for (int i = 0; i < kro.length; i++) {
            populasiGabungan[i] = kro[i];
        }
        for (int j = 0; j < childCross.length; j++) {
            populasiGabungan[j + kro.length] = childCross[j];
        }
        for (int j = 0; j < childMut.length; j++) {
            populasiGabungan[j + kro.length + childCross.length] = childMut[j];
        }
        //proses menghitung fitness setiap individu dalam populasi
        double[] fit = new double[populasiGabungan.length];
        int[][] pell = new int[populasiGabungan.length][];
        for (int j = 0; j < populasiGabungan.length; j++) {
            int[][] jdw = bentuk_jadwal(populasiGabungan[j]);           
            fit[j] = fitness(jdw);
            pell[j] = getTmpPelanggaran();
        }
        penjadwalan.setPelanggaran(pell);
        penjadwalan.setFitness(fit);
        return populasiGabungan;
    }

    public static int[][] evaluasii(int[][] kro, int[][] offspring) {
        int populasiGabungan[][] = new int[popsize + offspringCrossover + offspringMutasi][ukrnKromosom];//wadah untuk setiap populasi (semua induk dan semua anak) pada setiap iterasi
        //proses menggabungkan induk dan hasil reproduksi
        for (int i = 0; i < kro.length; i++) {
            populasiGabungan[i] = kro[i];
        }
        for (int j = 0; j < offspring.length; j++) {
            populasiGabungan[j + kro.length] = offspring[j];
        }
        //proses menghitung fitness setiap individu dalam populasi
        double[] fit = new double[populasiGabungan.length];
        int[][] pell = new int[populasiGabungan.length][];
        for (int j = 0; j < populasiGabungan.length; j++) {
            System.out.println(Arrays.toString(populasiGabungan[j]));
            int[][] jdw = bentuk_jadwal(populasiGabungan[j]);
            fit[j] = fitness(jdw);
            pell[j] = getTmpPelanggaran();
        }
        penjadwalan.setPelanggaran(pell);
        penjadwalan.setFitness(fit);
        return populasiGabungan;
    }

    public static int[] sorting(double[] arr) {
        int[] idsort = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            double max = 0.0;
            int maxid = 0;
            for (int j = 0; j < arr.length; j++) {
                double tmp = arr[j];
                if (tmp > max) {
                    max = tmp;
                    maxid = j;
                }
            }
            arr[maxid] = 0;
            idsort[i] = maxid;
        }
        return idsort;
    }

    public static int[][] seleksi(int[][] populasi) {
        double[] fitnes = getFitness();
        int[][] pelanggaran = getPelanggaran();
        int[][] bestPopulasi = new int[popsize][];
        double[] ff = new double[fitnes.length];
        System.arraycopy(fitnes, 0, ff, 0, fitnes.length);
        int[][] pell = new int[populasi.length][];
        //mulai dari sini
        int[] idsort = sorting(fitnes);
        double sumFit = 0.0;
        for (int i = 0; i < popsize; i++) {
            bestPopulasi[i] = populasi[idsort[i]];
            sumFit = sumFit + ff[idsort[0]];
        }
        setRatarataFitness(sumFit / popsize);
        setFitnesMax(ff[idsort[0]]);
        setPelanggaranMax(pell[idsort[0]]);
        return bestPopulasi;
    }

    public static void run(int iterasi) throws SQLException, IOException, BiffException {
        int kro[][] = inisialisasi();//wadah untuk populasi awal inisialisasi
        int populasiGabungan[][] = new int[popsize + offspringCrossover + offspringMutasi][ukrnKromosom];//wadah untuk setiap populasi (semua induk dan semua anak) pada setiap iterasi
        for (int i = 0; i < iterasi; i++) {//perulangan untuk melakukan iterasi genetika
            //proses reproduksi
            int[][] childCrossover = crossover(kro);
            int[][] childMutasi = mutasi(kro);
            //proses menggabungkan induk dan hasil reproduksi
            populasiGabungan = evaluasi(kro, childCrossover, childMutasi);
            double[] fit = getFitness();
            System.out.println();
           //proses seleksi
            int[][] best = seleksi(populasiGabungan);
            for (int j = 0; j < best.length; j++) {
                System.out.println(Arrays.toString(best[j]));
            }
            System.out.println("Max = " + getFitnesMax());
            System.out.println("");
            kro = best;
        } 
        setBestkro(kro[iterasi-1]);
    }
    
    public static int[] bestKro(int populasi[][]){
        double[] fitnes = getFitness();
        int[][] bestPopulasi = new int[popsize][];
        double[] ff = new double[fitnes.length];
        int [] kro;
        int[] idsort = sorting(fitnes);
        double sumFit = 0.0;
        kro = populasi[idsort[0]];
        
        return null;
    }

    public void out() throws SQLException, IOException, BiffException {
        final formProses it = new formProses();
        int kro[][] = {{11, 6, 10, 9, 20, 17, 14, 15, 13, 12, 21, 16, 3, 18, 5, 19, 1, 4, 7, 2, 8},
            {2, 6, 4, 8, 15, 20, 5, 3, 12, 14, 19, 21, 10, 18, 17, 1, 11, 16, 13, 9, 7},
            {16, 13, 8, 2, 9, 4, 11, 3, 20, 19, 7, 6, 10, 12, 5, 17, 15, 1, 21, 14, 18},
            {3, 2, 21, 5, 10, 7, 8, 18, 16, 14, 17, 6, 4, 12, 11, 9, 1, 13, 20, 19, 15},
            {16, 9, 3, 6, 20, 10, 21, 2, 19, 4, 7, 18, 8, 14, 12, 1, 5, 15, 13, 11, 17}};
        int populasiGabungan[][] = new int[popsize + offspringCrossover + offspringMutasi][ukrnKromosom];//wadah untuk setiap populasi (semua induk dan semua anak) pada setiap iterasi

        for (int i = 0; i < 2; i++) {
            System.out.println("\033[31m" + "=============================== ITERASI " + (i + 1) + " ==================================");
            int[][] childCrossover = crossover(kro);
            int[][] childMutasi = mutasi(kro);
            populasiGabungan = evaluasi(kro, childCrossover, childMutasi);
            int[][] best = seleksi(populasiGabungan);
            System.out.println("");
            System.out.println("====Hasil Seleksi=====");
            for (int j = 0; j < best.length; j++) {
                System.out.println(Arrays.toString(best[j]));
            }
            System.out.println("Fitness maksimum = " + getFitnesMax());

            //mengganti kromosom awal dengan kromosom terbaik hasil seleksi
            for (int j = 0; j < best.length; j++) {
                kro[j] = best[j];
            }
            System.out.println("");
        }
    }

    public static void main(String[] args) throws IOException, BiffException, SQLException {
        formProses proses = new formProses();
        proses.setVisible(true);
        
    }
}
