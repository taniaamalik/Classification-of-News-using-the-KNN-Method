/*
Nama Kelompok :
Erlina Rohmawati (175150201111045)
Tania Malik Iryana (175150201111053)
Alvina Eka ((175150201111056)
Jeowandha Ria Wiyani (175150207111029)
 */
package ProjectAkhir;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class weighting {

    private String[][] stem;
    private String[][] stemUji;

    public void setText(String[][] stem, String[][] stemUji) {
        this.stem = stem;
        this.stemUji = stemUji;
    }

    public List<HashMap<String, Integer>> getTFDokumen() {
        List<HashMap<String, Integer>> TFDok = new ArrayList<HashMap<String, Integer>>();

        String[] dok;

        for (int i = 0; i < stem.length; i++) {
            dok = stem[i];
            HashMap<String, Integer> freq = new HashMap<>();

            for (int j = 0; j < dok.length; j++) {
                String kata = dok[j];
                if (kata != null) {

                    if (freq.containsKey(kata)) {
                        int count = freq.get(kata);
                        count++;
                        freq.put(kata, count);
                    } else {
                        freq.put(kata, 1);
                    }
                }

            }
            TFDok.add(freq);
        }
        return TFDok;
    }

    public List<HashMap<String, Integer>> getTFDokumenUji() {
        List<HashMap<String, Integer>> TFDokUji = new ArrayList<HashMap<String, Integer>>();
        List<String> feature = new ArrayList<>(getFeatures());

        String[] dokUji;

        for (int i = 0; i < stemUji.length; i++) {
            dokUji = stemUji[i];

            HashMap<String, Integer> freq = new HashMap<>();

            for (int j = 0; j < dokUji.length; j++) {
                String kata = dokUji[j];
                for (int k = 0; k < feature.size(); k++) {
                    if (kata != null) {
                        if (kata.equals(feature.get(k))) {
                            if (freq.containsKey(kata)) {
                                int count = freq.get(kata);
                                count++;
                                freq.put(kata, count);
                            } else {
                                freq.put(kata, 1);
                            }
                        }
                    }

                }
            }
            TFDokUji.add(freq);
        }
        return TFDokUji;
    }

    public int[][] getTF(List<HashMap<String, Integer>> TFDok) {
        List<HashMap<String, Integer>> freq = TFDok;
        List<String> feature = new ArrayList<>(getFeatures());
        int n_doc = freq.size();
        int n_terms = feature.size();

        int[][] TFMatrix = new int[n_terms][n_doc];
        for (int i = 0; i < n_doc; i++) {
            for (int j = 0; j < n_terms; j++) {
                if (freq.get(i).containsKey(feature.get(j))) {
                    TFMatrix[j][i] = freq.get(i).get(feature.get(j));
                }
            }
        }

        return TFMatrix;
    }

    public Set<String> getFeatures() {
        String[] data;
        Set<String> feature = new HashSet<String>();
        for (int i = 0; i < getTFDokumen().size(); i++) {
            feature.addAll(getTFDokumen().get(i).keySet());
        }
        return feature;

    }

    public double[][] getTFIDF(List<HashMap<String, Integer>> TFDok, int[][] TF) {
        List<HashMap<String, Integer>> freq = TFDok;
        List<String> feature = new ArrayList<>(getFeatures());
        int n_doc = freq.size();
        int n_terms = feature.size();

        double[][] DocFreq = new double[n_terms][n_doc + 2];
        double[][] tfIdf = new double[n_terms][n_doc];
        for (int i = 0; i < n_doc + 2; i++) {
            for (int j = 0; j < n_terms; j++) {
                if (i < n_doc) {
                    if (TF[j][i] != 0) {
                        DocFreq[j][i] = 1 + Math.log10(TF[j][i]); //Log Frequency Weighting
                        DocFreq[j][n_doc] += 1; //df
                        DocFreq[j][n_doc + 1] = Math.log10(n_doc / DocFreq[j][n_doc]); //idf
                        tfIdf[j][i] = DocFreq[j][i] * DocFreq[j][n_doc + 1];
                    }
                }
            }

        }
        return tfIdf;
    }

    public double[][] TfIdfNormalisasi(List<HashMap<String, Integer>> TFDok, double[][] TFIDF) {
        List<HashMap<String, Integer>> freq = TFDok;
        List<String> feature = new ArrayList<>(getFeatures());
        int n_doc = freq.size();
        int n_terms = feature.size();
        double temp2 = 0, temp3 = 0;

        double[][] tfIdf = TFIDF;
        double[][] tfIdfNor = new double[n_terms][n_doc];
        for (int i = 0; i < n_doc; i++) {
            for (int j = 0; j < n_terms; j++) {
                if (tfIdf[j][i] != 0) {
                    temp2 += Math.pow(tfIdf[j][i], 2);
                    temp3 = Math.sqrt(temp2);
                    tfIdfNor[j][i] = tfIdf[j][i] / temp3;
                } else {
                    tfIdfNor[j][i] = 0;
                }

            }

        }
        return tfIdfNor;
    }

    public List<List<Double>> Cosine(double[][] tfIdfNorLatih, double[][] tfIdfNorUji) {
        List<HashMap<String, Integer>> freq = getTFDokumen();
        List<String> feature = new ArrayList<>(getFeatures());
        int n_doc = freq.size();
        int n_terms = feature.size();
        double temp2 = 0;
        List<List<Double>> hasilCosineSemua = new ArrayList<>();
        for (int i = 0; i < tfIdfNorUji[0].length; i++) {
        List<Double> hasilCosine = new ArrayList<>();
            for (int j = 0; j < n_doc; j++) {
                for (int k = 0; k < n_terms; k++) {
                    temp2 += tfIdfNorLatih[k][j] * tfIdfNorUji[k][i];
                }
            hasilCosine.add(1 - temp2);
            temp2 = 0;
            }
            hasilCosineSemua.add(hasilCosine);
        }
            
        return hasilCosineSemua;
    }

    public void cetak(double[][] input, Set<String> inputFeature) {
        Set<String> feature = inputFeature;
        List<String> feature2 = new ArrayList<>(feature);

        double[][] TFMatrix = input;

        for (int i = 0; i < TFMatrix.length; i++) {
            System.out.print(feature2.get(i) + " = ");
            for (int j = 0; j < TFMatrix[i].length; j++) {
                System.out.print(TFMatrix[i][j]);
                System.out.print(" ");
            }
            System.out.println("");
        }
    }

    public void cetakInt(int[][] input, Set<String> inputFeature) {
        Set<String> feature = inputFeature;
        List<String> feature2 = new ArrayList<>(feature);

        int[][] TFMatrix = input;

        for (int i = 0; i < TFMatrix.length; i++) {
            System.out.print(feature2.get(i) + " = ");
            for (int j = 0; j < TFMatrix[i].length; j++) {
                System.out.print(TFMatrix[i][j]);
                System.out.print(" ");
            }
            System.out.println("");
        }
    }
}