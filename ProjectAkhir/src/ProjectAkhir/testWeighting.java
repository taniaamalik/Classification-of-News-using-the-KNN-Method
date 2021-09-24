/*
Nama Kelompok :
Erlina Rohmawati (175150201111045)
Tania Malik Iryana (175150201111053)
Alvina Eka ((175150201111056)
Jeowandha Ria Wiyani (175150207111029)
 */
package ProjectAkhir;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

public class testWeighting {

    public static void main(String[] args) throws IOException {
        double[][] dataLatih, dataUji;
        String alamatLatih = "D:\\Tania\\UB\\Semester 5\\TEXT MINNING\\Tugas\\Project Akhir\\Data\\Data latih"; //masukkan alamat sesuai data latih
        String alamatUji = "D:\\Tania\\UB\\Semester 5\\TEXT MINNING\\Tugas\\Project Akhir\\Data\\Data uji"; //masukkan alamat sesuai data uji

        weighting(alamatLatih, alamatUji);

    }

    public static void weighting(String alamatLatih, String alamatUji) throws IOException {
        weighting dok1 = new weighting();
        preprocessing testing = new preprocessing();
        testPreprocessing hasilPrepocessing = new testPreprocessing();
        testPreprocessing hasilPrepocessing2 = new testPreprocessing();
        String[][] inputKataLatih = hasilPrepocessing.data(alamatLatih);
        String[][] inputKataUji = hasilPrepocessing2.data(alamatUji);
        dok1.setText(inputKataLatih, inputKataUji);
        System.out.println("\nTAHAP WEIGHTING");
        System.out.println("========================================================================================================= Hasil Tf =========================================================================================================");
        System.out.println("\"========================================================================================================= Data Latih =========================================================================================================\"");
        int[][] tfLatih = dok1.getTF(dok1.getTFDokumen());
        dok1.cetakInt(tfLatih, dok1.getFeatures());
        System.out.println("\"========================================================================================================= Data Uji =========================================================================================================\"");
        int[][] tfUji = dok1.getTF(dok1.getTFDokumenUji());
        dok1.cetakInt(tfUji, dok1.getFeatures());

        System.out.println("\n========================================================================================================= Hasil Tf-Idf =========================================================================================================");
        System.out.println("\"========================================================================================================= Data Latih =========================================================================================================\"");
        double[][] tfIdfLatih = dok1.getTFIDF(dok1.getTFDokumen(), tfLatih);
        dok1.cetak(tfIdfLatih, dok1.getFeatures());
        System.out.println("\"========================================================================================================= Data Uji =========================================================================================================\"");
        double[][] tfIdfUji = dok1.getTFIDF(dok1.getTFDokumenUji(), tfUji);
        dok1.cetak(tfIdfUji, dok1.getFeatures());

        System.out.println("\n========================================================================================================= Hasil Tf-Idf Normalisasi =========================================================================================================");
        System.out.println("========================================================================================================= Data Latih =========================================================================================================");
        double[][] tfIdfNormLatih = dok1.TfIdfNormalisasi(dok1.getTFDokumen(), tfIdfLatih);
        dok1.cetak(tfIdfNormLatih, dok1.getFeatures());
        System.out.println("========================================================================================================= Data Uji =========================================================================================================");
        double[][] tfIdfNormUji = dok1.TfIdfNormalisasi(dok1.getTFDokumenUji(), tfIdfUji);
        dok1.cetak(tfIdfNormUji, dok1.getFeatures());

        System.out.println("\n================= Hasil Cosine Distance =====================");

        List<List<Double>> hasilCosine = dok1.Cosine(tfIdfNormLatih, tfIdfNormUji);
        List<List<Double>> hasilCosineSort = dok1.Cosine(tfIdfNormLatih, tfIdfNormUji);
        List<String> namaFileLatih = testing.getNamaFileLatih();
        List<String> kelasLatih = testing.getKelasLatih();
        List<String> namaFileUji = testing.getNamaFileUji();
        List<String> kelasUji = testing.getKelasUji();
        int[] dokumen = new int[hasilCosine.get(0).size()];
        double akurasi = 0;
        double hasilAkurasi = 0;
        for (int j = 0; j < hasilCosineSort.size(); j++) {
            Collections.sort(hasilCosineSort.get(j));
            List<String> kelasTerbanyak = new ArrayList<>();
            for (int k = 0; k < hasilCosineSort.get(0).size(); k++) {
                dokumen[k] = hasilCosine.get(j).indexOf(hasilCosineSort.get(j).get(k)) + 1;
                if (k < 3) {
                    kelasTerbanyak.add(kelasLatih.get(dokumen[k] - 1));
                    System.out.print(hasilCosineSort.get(j).get(k));
                    System.out.println(", dokumen " + dokumen[k] + "(" + namaFileLatih.get(dokumen[k] - 1) + ", Kategori " + kelasLatih.get(dokumen[k] - 1) + ")");
                }
            }
            String kelasTerpilih = kelasTerbanyak.stream()
                    .collect(Collectors.groupingBy(s -> s, Collectors.counting()))
                    .entrySet()
                    .stream()
                    .max(Comparator.comparing(Entry::getValue))
                    .get().getKey().toString();
            System.out.println("Dokumen : " + namaFileUji.get(j) + "\nKategori Asli : " + kelasUji.get(j) + "\nKategori Perhitungan : " + kelasTerpilih + "\n***********************************************************************************************************");
            if (kelasUji.get(j).equals(kelasTerpilih)) {
                akurasi++;
            }
        }
            hasilAkurasi = (akurasi / kelasUji.size()) * 100;
        System.out.println("===========================================================================");
        System.out.println("Hasil Akurasi : " + hasilAkurasi + "%");
    }
}