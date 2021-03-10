/**
 * This class implements the GaleShapley algorithm to 
 * provide a solution to the matching problem.
 * @author Daria Przhebelska, 300106264
 */

import java.util.Stack;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

class GaleShapley {

    //------------------------------ Instance Variables -------------------------------//

    private int n; //Keep track of n

    private Stack<Integer> Sue; //Stack of unmatched employers

    private Integer[] students; //Student matches
    private Integer[] employers; //Employer matches

    private Integer[][] A; //2D array to hold students' ranking of each company
    private PriorityQueue[] PQ; //list of priority queues to hold each employer's ranking of students


    private String filename; //Keep track of the name of the input file for easy output
    private String[] studentNames; //Keep track of the student names for easy output
    private String[] employerNames; //Keep track of the employer names for easy output

    //--------------------------------- Constructor -------------------------------------//

    public GaleShapley() {}

    //------------------------------ Public Class Method --------------------------------//

    /**
     * Initializes the algorithm and calls execute() and save()
     * 
     * Takes as input a file name for a file of the following format:
     *      value of n
     *      employer names
     *      student names
     *      ranking matrix
     * 
     * An example of the input file:
     * 
     * 3
     * Thales
     * Canada Post
     * Cisco
     * Olivia
     * Jackson
     * Sophia
     * 1,1 2,1 3,2
     * 3,2 2,2 1,3
     * 1,3 3,3 2,1
     * 
     * @param filename
     * @throws IOException
     */
    public void initialize(String filename) throws IOException {
        //create a new file and a reader for the file
        Scanner reader;
        
        try {

            this.filename = filename;
            File file = new File(filename);
            reader = new Scanner(file);

        } catch (FileNotFoundException e) {

            System.out.println("File was not found. ");
            return;

        }

        try {

            n = Integer.parseInt(reader.nextLine()); //store the value of n
        
        } catch (NumberFormatException e){

            System.out.println("File not in the right format. ");
            reader.close();
            return;

        }
          
        //initialize all of the storage
        Sue = new Stack<Integer>();
        
        students = new Integer[n];
        employers = new Integer[n];

        A = new Integer[n][n];
        PQ = new PriorityQueue[n];
    
        studentNames = new String[n];
        employerNames = new String[n];

        String[] fileLines;

        try {

            fileLines = new String[n*3];
            //stores the file in an array of strings to minimalize number of for loops
            for (int i = 0; i < fileLines.length; i++) {
                fileLines[i] = reader.nextLine();
            }

        } catch (NoSuchElementException e) {

            System.out.println("File not in the right format. ");
            reader.close();
            return;

        }

        for (int i = 0; i < n; i++) {
            Sue.push(i); //add all of the unmatched employers to stack
            
            students[i] = -1; //-1 is used to designate an umatched employer/student
            employers[i] = -1;
            
            PQ[i] = new PriorityQueue(n);
            
            String tmp = fileLines[i+n*2]; //navigate to matrix
            //splits the line of the matrix into an array of integers
            String[] r = tmp.split("[, ]", 2*n);


            for (int j = 0; j < n; j++) {
                A[j][i] = Integer.parseInt(r[j*2+1]); //adds student ranking
                PQ[i].insert(Integer.parseInt(r[2*j]), j); //adds employer ranking
            }

            studentNames[i] = fileLines[i+n]; //navigate to student names
            employerNames[i] = fileLines[i]; //navigate to employer names
        }

        reader.close();

        //executes the algorithm and saves the results in a text file
        execute();
        save();
    } /* initialize */

    //----------------------------- Private Class Methods -------------------------------//

    /**
     * Executes the Gale-Shapley algorithm given in the instructions.
     * Returns nothing since results are stored in instance variables.
     */
    private void execute() {
        while (!Sue.empty()) {

            int e = Sue.pop(); //e is looking for a student
            int s = PQ[e].removeMin(); //most preferred student of e
            int e2 = students[s]; 

            if (students[s] == -1) { //student is unmatched

                students[s] = e;
                employers[e] = s; //match (e, s)

            } else if (A[s][e] < A[s][e2]) { // s prefers e to employer e2

                students[s] = e;
                employers[e] = s; //replace the match
                employers[e2] = -1; //now unmatched
                Sue.push(e2);

            } else { //s rejects the offer from e

                Sue.push(e);

            }
        }
    } /* execute */

    /**
     * Creates a file and saves the results in it
     * 
     * The name of the file follows the format of "matches_ABC.txt" for an input file
     * named "ABC.txt".
     * 
     * The output is in the format of Match [number]: [Employer] - [Student]
     * 
     * An example of an output file:
     * 
     * Match 0: Thales - Olivia
     * Match 1: Canada Post - Jackson
     * Match 2: Cisco - Sophia
     * 
     * @throws IOException
     */
    private void save() throws IOException {
        //creates a file and a writer
        File out = new File("matches_" + filename);
        FileWriter writer = new FileWriter(out);

        //adds a line to the file for each match
        for (int i = 0; i < n; i++) {
            writer.write("Match " + i + ": " + employerNames[i] + " - " + studentNames[employers[i]] + "\n");
        }

        System.out.println("Output file, " + "matches_" + filename + " created.");
        System.out.println();
        writer.close();
    } /* save */

    //----------------------------------- Main Method -----------------------------------//

    /**
     * Main method
     * 
     * Input can be taken from the command line or through the main method.
     * 
     * Takes as input the name of the file and executes the algorithm on it. 
     * 
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        Scanner input = new Scanner(System.in);
        String c = "y";

        if (args.length > 0) { //Checks the commandline arguments as a way to input

            if (!args[0].equals(null)) {
                GaleShapley g = new GaleShapley();
                g.initialize(args[0]);
            }

        } else {

            while (c.equals("y")) { //Loops the program so that it can take multiple inputs

                System.out.println("Enter the name of the file (excluding the .txt): ");
    
                String name = input.nextLine() + ".txt";
                GaleShapley g = new GaleShapley();
    
                g.initialize(name);
    
                System.out.println("Would you like to enter another file name? (y/n) ");
                c = input.nextLine();

            } 

        }

        input.close();

    } /* main */

 }