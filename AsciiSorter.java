package finalproject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Program that solves problem for Task 2.
 * Adjacency Matrix with Topological Sort based Bubble Sort Solution to 
 * alphabetize a list of words with unknown characters
 * @author Anish Dalal
 *
 */
public final class AsciiSorter {
    
    /**
     * Default constructor due to AsciiSorter being a utility class. 
     */
    private AsciiSorter() {
        
    }
    
    /**
     * Main method that takes in cmd line args 
     * (dictionary, unsorted list, desired
     * sorted list output file name) and sorts the unsorted list of words. 
     * @param args
             the list of command line arguments.
     * @throws IOException
             Input Output exception for file handling.
     */
    public static void main(String[] args) throws IOException {
        /*
         * Read in dict and save to list build graph from list read in unsorted
         * and save to list/array write bubble sort algo that uses a compare
         * method (swap) method that is depth first search based use bubble sort
         * on unsorted list using this compare method output list
         */

        LinkedList<String> unmodDict = readDict(new File(args[0]));
        String[] unsorted = readUnsorted(new File(args[1]));
        
        String alphabet = returnAlphabet(unmodDict);
        String[] sorted = sort(unsorted, alphabet);

        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(
                args[2])));

        for (String i : sorted) {
            writer.write(i);
            writer.newLine();
        }

        writer.close();
    }

    /**
     * Method to read in the dictionary file and output a linkedlist of the
     * words in the file.
     * @param file
             file that contains the dictionary. 
     * @return
             a linked list of the words stored in the dictionary file.
     * @throws IOException
             Input/Output exception of BufferedReader.
     */
    public static LinkedList<String> readDict(File file) throws IOException {
        Scanner s = new Scanner(new FileReader(file));
        String line = null;
        LinkedList<String> ll = new LinkedList<>();
        int count = 0;
        while (s.hasNext()) {
            ll.add(s.next());
        }
        s.close();
        return ll;
    }

    /**
     * Read in the unsorted list of words and save them to a 
     * linked list.
     * @param file
             file that contains a list of unsorted words.
     * @return
             linked list of unsorted words.
     * @throws IOException
             Input/Output exception for file handling.
     */
    public static String[] readUnsorted(File file) throws IOException {
        Scanner reader = new Scanner(new FileReader(file));
        int count = 0;
        while (reader.hasNext()) {
            reader.next();
            count++;
        }
        reader.close();
        Scanner reader2 = new Scanner(new FileReader(file));
        String line2 = null;
        String[] unsortedWerdz = new String[count];
        int i = 0;
        while (reader2.hasNext()) {
            unsortedWerdz[i] = reader2.next();
            i++;
        }
        reader2.close();

        return unsortedWerdz;
    }

    /**
     * Build adjacency matrix of the characters in order to
     * form a proper ordering of the characters called the 
     * alphabet.
     * @param ll
             linkedlist of sorted words.
     * @return
             String that provides the distinct characters 
             in dict file in sorted order.
     */
    public static String returnAlphabet(LinkedList<String> ll) {
        String sb = "";
        for (String word : ll) {
            for (int i = 0; i < word.length(); i++) {
                if (sb.indexOf(word.charAt(i)) == -1) {
                    sb = sb + word.charAt(i);
                }
            }
        }
        int cap = sb.length();
        boolean[][] am = new boolean[cap][cap];

        for (int j = 0; j < (ll.size() - 1); j++) {
            int k = 0;
            String w1 = ll.get(j);
            String w2 = ll.get(j + 1);
            while (w1.charAt(k) == w2.charAt(k)) {
                k++;
            }
            int index1 = sb.indexOf(w1.charAt(k));
            int index2 = sb.indexOf(w2.charAt(k));
            am[index1][index2] = true;
        }

        String alphabet = produceAlphabet(sb, am);
        return alphabet;
    }

    /**
     * Actual topological sorting of the graph.
     * @param sb
             the alphabet string that needs to be built.
     * @param am
             the adjacency matrix. 
     * @return
             complete sorted alphabet.
     */
    public static String produceAlphabet(String sb, boolean[][] am) {
        boolean[] unconnected = new boolean[sb.length()];
        String alphabet = "";
        int added = 0;
        while (added < sb.length()) {
            for (int i = 0; i < sb.length(); i++) {
                if (!unconnected[i] && checkIndegreeZero(am, i)) {
                    added++;
                    alphabet = alphabet + sb.charAt(i);
                    unconnected[i] = true;
                    for (int k = 0; k < sb.length(); k++) {
                        am[i][k] = false;
                    }
                }

            }
        }

        return alphabet;

    }
    
    /**
     * Checks if a vertex has no dependencies. 
     * @param edges
             array of the edges to which the vertex connects.
     * @param index
             row of dependencies.
     * @return
             true if indegree=0
             false if indegree != 0
     */
    public static boolean checkIndegreeZero(boolean[][] edges, int index) {
        for (int i = 0; i < edges.length; i++) {
            if (edges[i][index]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Swap function for bubble sort. 
     * @param a
             The left string to be compared.
     * @param b
             The right string to be compared.
     * @param alphabet
             The sorting order to be followed.
     * @return
             0 if the right string is "better".
             1 if the left string is "better".
     */
    public static int compare(String a, String b, String alphabet) {
        int k = 0;
        while (a.charAt(k) == b.charAt(k)) {
            k++;
        }
        if (alphabet.indexOf(a.charAt(k)) > alphabet.indexOf(b.charAt(k))) {
            return 1;
        }

        return 0;
    }

    /**
     * Bubble sort algorithm method.
     * @param unsorted
             Unsorted list of words.
     * @param alphabet
             Alphabet to be used to sort list of words.
     * @return
             list of sorted words.
     */
    public static String[] sort(String[] unsorted, String alphabet) {
        if (unsorted.length == 0) {
            return null;
        }
        for (int i = 0; i < (unsorted.length - 1); i++) {
            for (int k = 0; k < (unsorted.length - i - 1); k++) {
                if (compare(unsorted[k], unsorted[k + 1], alphabet) == 1) {
                    String temp = unsorted[k + 1];
                    unsorted[k + 1] = unsorted[k];
                    unsorted[k] = temp;
                }
            }
        }

        return unsorted;
    }
}
