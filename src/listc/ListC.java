/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listc;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Miguel Nieto A <miguelnietoa at github.com>
 */
public class ListC {

    /**
     * Number of codewords.
     */
    int m;

    /**
     * Number of bits per codeword
     */
    int n;

    /**
     * Base of the codewords
     */
    int q;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ListC main = new ListC();
        Scanner sc = new Scanner(System.in);

        System.out.println("Digite m (número de códigos)");
        main.m = sc.nextInt();
        System.out.println("Digite n (número de bits por código):");
        main.n = sc.nextInt();
        System.out.println("Digite q (base):");
        main.q = sc.nextInt();

        System.out.println("Digite codewords:");
        String[] codewords = new String[main.m];
        for (int i = 0; i < main.m; i++) {
            codewords[i] = sc.next();
        }

        System.out.println("*************************");
        ArrayList<String> listC = main.listElements(codewords);

        System.out.println(listC);
        System.out.println("Numero de códigos generados: " + listC.size());

        System.out.println("Min distancia: " + main.calcMinDistance(listC));

        /*
        int[][] matrix = main.generator(main.n, main.q);
        for (int[] is : matrix) {
            for (int i : is) {
                System.out.print(i);
            }
            System.out.println("");
        }
        */
    }
    
    /**
     * Calculate list of the generates of codewords
     * @param codewords
     * @return list of the generates of codewords
     */
    public ArrayList<String> listElements(String[] codewords) {
        ArrayList<String> newCodewords = new ArrayList<>();
        int row = (int) Math.pow(q, n);
        int[] alphas = new int[n];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < n; j++) {
                alphas[j] = (int) (i / Math.pow(q, n - j - 1)) % q;
            }
            calcLinearCombination(codewords, newCodewords, alphas);
        }
        return newCodewords;
    }
    
    /**
     * Calculate the linear combination of codewords with all 
     * possible numbers of length n and base q
     * @param codewords
     * @param newCodewords
     * @param alphas 
     */
    public void calcLinearCombination(String[] codewords,
            ArrayList<String> newCodewords, int[] alphas) {
        String[] listToSum = new String[m];
        String aux;
        for (int k = 0; k < m; k++) {
            String codeword = codewords[k];
            aux = "";
            for (int i = 0; i < n; i++) {
                int bitCodeword = Integer.parseInt(codeword.substring(i, i + 1));
                aux += (bitCodeword * alphas[i]) % q;
            }
            listToSum[k] = aux;
        }
        String codewordRes = "";
        for (int i = 0; i < n; i++) {
            int sum = 0;
            for (int j = 0; j < m; j++) {
                sum += Integer.parseInt(listToSum[j].substring(i, i + 1));
            }
            sum = sum % q;
            codewordRes += sum;
        }
        if (!newCodewords.contains(codewordRes)) {
            newCodewords.add(codewordRes);
        }

    }
    
    /**
     * Calculate the minimum distance of listC
     * @param listC List of generates
     * @return the minumun distance
     */
    public int calcMinDistance(ArrayList<String> listC) {
        int minDist = n;
        for (int i = 0; i < listC.size(); i++) {
            for (int j = i + 1; j < listC.size(); j++) {
                char[] code1 = listC.get(i).toCharArray();
                char[] code2 = listC.get(j).toCharArray();
                int dist = 0;
                for (int k = 0; k < code1.length; k++) {
                    if (code1[k] != code2[k]) {
                        dist++;
                    }
                }
                if (dist < minDist) {
                    minDist = dist;
                }
            }
        }
        return minDist;
    }
    
    /**
     * This funcion generates all the numbers of length n in base q
     * (not used)
     * @param n 
     * @param q
     * @return matrix
     */
    public int[][] generator(int n, int q) {
        int row = (int) Math.pow(q, n);
        int[][] matrix = new int[row][n];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = (int) (i / Math.pow(q, n - j - 1)) % q;
            }
        }
        return matrix;
    }
}
