public class tmp {
    
    static int n = 5;
    static int[] employers = new int[]{0,1,2,3,4};
    static int[] students = new int[]{0,1,2,3,4};
    static String[] employerNames = new String[]{"A","B","C","D","E"};
    static String[] studentNames = new String[]{"1","2","3","4","5"};
    static int[][] A = new int[][]{{4,1,3,2,5},{3,1,5,2,4},{2,1,5,3,4},{4,1,2,5,3},{4,2,5,1,3}};
    static int[][] B = new int[][]{{4,5,3,2,1},{3,5,1,2,4},{3,5,4,2,1},{4,2,3,5,1},{2,4,3,5,1}};

    public static boolean stableMatch() {
        for (int i = 0; i < n; i++) { //iterates through all of the students
            int e = students[i]; //gets the matched employer
            //iterates through all other employers
            for (int j = 0; j < n; j++){ 
                int s = students[i];
                int e2 = A[i][j]-1;
                //checks if the student perfers the employer
                if (A[i][j] < A[i][e] && 
                //checks if the employer perfers the student
                    B[e2][s] < B[e2][employers[e2]]) { 
                        //return false; //not a stable match
                    }
            }
        }
        return true; //stable match
    }

    public static void main(String[] args) {
        System.out.println(stableMatch());
    }
}