/*
Nama Kelompok :
Erlina Rohmawati (175150201111045)
Tania Malik Iryana (175150201111053)
Alvina Eka ((175150201111056)
Jeowandha Ria Wiyani (175150207111029)
 */
package ProjectAkhir;

import IndonesianStemmer.IndonesianStemmer;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class testPreprocessing {

    public static void main(String[] args) throws IOException {
       data("D:\\Tania\\UB\\Semester 5\\TEXT MINNING\\Tugas\\Project Akhir\\Data\\Data latih"); //masukkan alamat data latih
       data("D:\\Tania\\UB\\Semester 5\\TEXT MINNING\\Tugas\\Project Akhir\\Data\\Data uji"); //masukkan alamat sesuai data uji
    }

    public static String[][] data(String alamat) throws IOException {

        List<String> direktori = preprocessing.getFile(alamat);
        List<String[]> hasilTokenisasi = new ArrayList<>();
        List<String[]> hasilFiltering = new ArrayList<>();
        List<String[]> hasilStemming = new ArrayList<>();

        preprocessing dok1 = new preprocessing();
        List<String> input = new ArrayList<>();
        String[] hasilCleaning = new String[direktori.size()];
        String[] hasilCaseFolding = new String[direktori.size()];
        System.out.println("\nTAHAP PREPROCESSING\n");

        System.out.println("================== CLEANING ==========================");
        for (int i = 0; i < direktori.size(); i++) {
            input.add(dok1.BacaFile(direktori.get(i)));
            if (input.get(i) != null) {
                hasilCleaning[i] = dok1.cleaning(input.get(i));
            }
        }
        for (Object array : hasilCleaning) {
            System.out.println(array);
        }
        System.out.println("\n================ CASE FOLDING ========================");
        for (int i = 0; i < input.size(); i++) {
            if (input.get(i) != null) {
                hasilCaseFolding[i] = dok1.case_folding(hasilCleaning[i]);
                System.out.println(hasilCaseFolding[i]);
            }
        }

        System.out.println("\n================= TOKENISASI =========================");
        for (int i = 0; i < hasilCaseFolding.length; i++) {
            if (hasilCaseFolding[i] != null) {
                hasilTokenisasi.add(dok1.tokenisasi(hasilCaseFolding[i]));
            }
        }
        for (Object[] array : hasilTokenisasi) {
            System.out.println(Arrays.toString(array));
        }

        System.out.println("\n================= FILTERING =========================");
        String direktoriStopword = "D:\\Tania\\UB\\Semester 5\\TEXT MINNING\\Tugas\\Project Akhir\\tala.txt";
        for (int i = 0; i < hasilTokenisasi.size(); i++) {
            if (hasilTokenisasi.get(i) != null) {
                hasilFiltering.add(dok1.filtering(hasilTokenisasi.get(i), direktoriStopword));
            }
        }
        for (Object[] array : hasilFiltering) {
            System.out.println(Arrays.toString(array));
        }

        System.out.println("\n==================== STEMMING ====================");
        System.out.println(hasilFiltering.size());
        for (int i = 0; i < hasilFiltering.size(); i++) {
            if (hasilFiltering.get(i) != null && hasilFiltering.get(i).length > 0) {
                try {
                    hasilStemming.add(dok1.Stemming(hasilFiltering.get(i)));
                } catch (Exception e) {
                    
                    System.out.println("ERROR!!! "+ e);
                }
            }
        }
        int index = 0;
        for (Object[] array : hasilStemming) {
            index +=1;
            System.out.println(index+Arrays.toString(array));
        }
        String[][] hasilStemmingArray = new String[hasilStemming.size()][];
        for (int i = 0; i < hasilStemmingArray.length; i++) {
            hasilStemmingArray[i] = hasilStemming.get(i);
        }
        return hasilStemmingArray;
    }
}