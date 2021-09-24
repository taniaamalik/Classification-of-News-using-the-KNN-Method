/*
Nama Kelompok :
Erlina Rohmawati (175150201111045)
Tania Malik Iryana (175150201111053)
Alvina Eka ((175150201111056)
Jeowandha Ria Wiyani (175150207111029)
 */
package ProjectAkhir;

import IndonesianStemmer.IndonesianStemmer;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class preprocessing {

    static List<String> namaFileLatih = new ArrayList<String>();
    static List<String> namaFileUji = new ArrayList<String>();
    static List<String> kelasLatih = new ArrayList<String>();
    static List<String> kelasUji = new ArrayList<String>();

    public static List<String> getFile(String input) throws IOException {
        File folder = new File(input);
        System.out.println("*************************************" + folder.getName() + "*************************************");
        File[] listOfFiles = folder.listFiles();
        String[] kategori = new String[5];

        HashMap<String, String> file = new HashMap<>();
        List<String> direktori = new ArrayList<String>();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isDirectory()) {
                kategori[i] = listOfFiles[i].getName();
                File folder2 = new File(listOfFiles[i].toString());
                File[] listOfFiles2 = folder2.listFiles();
                for (int j = 0; j < listOfFiles2.length; j++) {
                    if (listOfFiles2[j].isFile()) {
                        if (folder.getName().equalsIgnoreCase("Data latih")) {
                            kelasLatih.add(kategori[i]);
                            namaFileLatih.add(listOfFiles2[j].getName());
                        } else if(folder.getName().equalsIgnoreCase("Data uji")){
                            kelasUji.add(kategori[i]);
                            namaFileUji.add(listOfFiles2[j].getName());
                        }
                        file.put(listOfFiles2[j].getName(), kategori[i]);

                        direktori.add(listOfFiles2[j].toString());
                    }
                }
            }

        }
            System.out.println("=====================KELAS LATIH=====================");
        for (int k = 0; k < kelasLatih.size(); k++) {;
            System.out.print(namaFileLatih.get(k));
            System.out.println(" kategori "+kelasLatih.get(k));
        }
            System.out.println("=====================KELAS UJI=====================");
        for (int i = 0; i < kelasUji.size(); i++) {
            System.out.print(namaFileUji.get(i));
            System.out.println(" kategori "+kelasUji.get(i));

        }

        return direktori;
    }

    
    public static String BacaFile(String direktori) throws IOException {
        String input;
        try (BufferedReader b = new BufferedReader(new FileReader(direktori))) {
            input = b.readLine();
        }
        return input;
    }

    public static List<String> getNamaFileLatih() {
        return namaFileLatih;
    }

    public static List<String> getNamaFileUji() {
        return namaFileUji;
    }

    public static List<String> getKelasLatih() {
        return kelasLatih;
    }

    public static List<String> getKelasUji() {
        return kelasUji;
    }

   

    public String cleaning(String input) {
        StringBuffer alpha = new StringBuffer();
        boolean cekkk = false;
        for (int i = 0; i < input.length(); i++) {
            String cek = "";
            boolean cekk = false;
            if (i <= input.length() - 2) {
                cek = String.valueOf(input.charAt(i)) + String.valueOf(input.charAt(i + 1));
                if (String.valueOf(cek.charAt(0)).equals(".")) {
                    cekk = true;
                }
            }
            if (Character.isAlphabetic(input.charAt(i)) || input.charAt(i) == ' ' || input.charAt(i) == '.') {
                if (cekk) {
                    alpha.append(" ");
                    cekkk = true;
                } else {
                    if (input.charAt(i) != ' ' || !cekkk) {
                        alpha.append(input.charAt(i));
                    }
                    cekkk = false;
                }
            }
        }
        input = alpha.toString();
        return input;
    }

    public String case_folding(String input) {
        input = input.toLowerCase();
        return input;
    }

    public static String[] tokenisasi(String input) {
        String[] output = input.split(" ");
        List<String> outputlist = new ArrayList<>();
        for (int i = 0; i < output.length; i++) {
            if (!"".equals(output[i])) {
                outputlist.add(output[i]);
            }
        }
        output = preprocessing.GetStringArray(outputlist);
        return output;
    }

    public String[] filtering(String[] dokumen, String direktoriStoplist) throws IOException {

        String inputStoplist = preprocessing.BacaFile(direktoriStoplist);
        String[] inputStoplistArray = preprocessing.tokenisasi(inputStoplist);

        List<String> hasilFiltering = new ArrayList<>();
        for (int i = 0; i < dokumen.length; i++) {
            hasilFiltering.add(dokumen[i]);
            for (int j = 0; j < inputStoplistArray.length; j++) {
                if (dokumen[i].equalsIgnoreCase(inputStoplistArray[j])) {
                    hasilFiltering.remove(dokumen[i]);
                } else if (dokumen[i].equalsIgnoreCase("")) {
                    hasilFiltering.remove(dokumen[i]);
                }
            }
        }
        String[] hasilFilteringArray = preprocessing.GetStringArray(hasilFiltering);
        return hasilFilteringArray;
    }

    public static String[] Stemming(String[] sebelumStemming) {
        String kataStem;
        ArrayList<String> aList = new ArrayList<String>();
        IndonesianStemmer indonesianStemmer = new IndonesianStemmer();

        for (int i = 0; i < sebelumStemming.length; i++) {
            kataStem = indonesianStemmer.findRootWord(sebelumStemming[i]);
            if (kataStem != null) {
                aList.add(kataStem);
            } else {
                aList.add(sebelumStemming[i]);
            }
        }
        String[] setelahStemming = aList.toArray(new String[aList.size()]);
        return setelahStemming;
    }

    public static String[] GetStringArray(List<String> arr) {
        String str[] = new String[arr.size()];
        for (int j = 0; j < arr.size(); j++) {
            str[j] = arr.get(j);
        }
        return str;
    }

}