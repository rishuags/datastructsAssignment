package project20280.exercises;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

public class Wk1 {

    public static void q1() {
        int[] my_array = {25, 14, 56, 15, 36, 56, 77, 18, 29, 49};


        //double average = ...;
        int sum = 0;
        double average;

        for(int i=0; i<my_array.length; i++){
            sum = sum + my_array[i];
        }

        average = (double) sum/ my_array.length;
        System.out.println("Average of inputted array: " + average);
    }
    public static int getIndexOf(int [] arr, int x){

        for(int i=0; i<arr.length; i++){
            if(arr[i] == x){
                return i;
            }
        }
        return -1;
    }

    public static String[] getCommonElements(String[] arr2, String[] arr1){

        HashSet<String> set1 = new HashSet<>();
        HashSet<String> set2 = new HashSet<>();

        for(String s: arr1){
            set1.add(s);
        }

        for(String s: arr2){
            if(set1.contains(s)){
                set2.add(s);
            }

        }
        String[] common =  set2.toArray(new String [0]);

        return common ;
    }

    public static int[][] addMatrices(int mat1[][], int mat2[][]){

        int m = mat1.length;
        int n = mat1[0].length;
        //System.out.println("m: "  + m);
        //System.out.println("n: "  + n);

        int sum[][] = new int[m][n];
        for (int i = 0; i < mat1.length; i++) {
            for (int j = 0; j < mat1.length; j++) {
                sum[i][j] = mat1[i][j] + mat2[i][j];
            }
        }
        return sum;
    }

    public static double [][] prodMatrices(double mat1[][], double mat2[][]){
        int m = mat1.length;
        int n = mat1[0].length;

        double prod [][] = new double [m][n];
        for (int row = 0; row < prod.length; row++) {
            for (int col = 0; col < prod[row].length; col++) {
                double x = 0;
                for (int i = 0; i < mat2.length; i++) {
                    x += mat1[row][i] * mat2[i][col];
                }
                prod[row][col] = x;
            }
        }
        return prod;
    }

    public static void main(String [] args) {

        //Q1
        System.out.println("Answer 1: ");
        q1();
        System.out.println();

        //Q2
        System.out.println("Answer 2: ");
        int [] arr = {90 , 77, 67, 55, 75, 57, 98, 17, 50, 23, 30, 100 , 34, 75, 28, 43, 14, 52, 64, 13};
        int x = 64;
        int indexOf = getIndexOf (arr , x); // your function
        System.out.println("index of " + x + " : " + indexOf);
        System.out.println();

        //Q3
        System.out.println("Answer 3: ");
        String[] array1 = {"nail", "cure", "vagabond", "riddle", "act", "songs", "zipper", "excite", "magical", "eager", "blood", "coast", "guess", "selfish", "milky", "ticket", "authority"};

        String[] array2 = {"cure", "wicked", "guess", "vagabond", "riddle", "act", "excite", "songs", "troubled", "eager", "blood", "coast", "waiting", "selfish", "milky", "ticket", "authority", "nail"};

        String[] common = getCommonElements(array1, array2); // your code

        System.out.println(Arrays.asList(common));
        System.out.println();

        //Q4
        System.out.println("Answer 4: ");
        int m = 5, n = 5;

        int mat1[][] = new int[m][n];
        int mat2[][] = new int[m][n];

        // initialize the matrices randomly, something like this:
        Random rnd = new Random();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                mat1[i][j] = rnd.nextInt(100) + 1;
                mat2[i][j] = rnd.nextInt(100) + 1;
            }
        }

        int sum[][] = addMatrices(mat1, mat2);

        // print sum
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(sum[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();


        //Q5
        System.out.println("Answer 5: ");
        m = 5;
        n = 5;

        double mat3[][] = new double[m][n];
        double mat4[][] = new double[m][n];

        // initialize the matrices randomly, something like this:

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                mat3[i][j] = rnd.nextDouble() ;
                mat4[i][j] = rnd.nextDouble() ;
            }
        }

        double prod[][] = prodMatrices(mat3, mat4);

        // print sum
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(prod[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();

    }


}
