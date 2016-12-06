import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args){

        //path to input file
        String pathToInputFile;
        //path to output file
        String pathToOutputFile;
        //object of Scanner
        Scanner scanner = new Scanner(System.in);


        System.out.println("Enter path to input file");
        pathToInputFile = scanner.next();
        System.out.println("Enter path to output file");
        pathToOutputFile = scanner.next();

        //Take records from file
        List<Float> inputFile = takeRecordsFromFile(pathToInputFile);

        //Sort records
        Collections.sort(inputFile, Collections.reverseOrder());

        //Write records in file
        write(pathToOutputFile, inputFile);
    }

    private static List<Float> takeRecordsFromFile(String pathToFile){

        File file = new File(pathToFile);
        List<Float> records = new ArrayList<>();
        try {
            BufferedReader linesInTxtFile = new BufferedReader(new FileReader(file.getAbsoluteFile()));
            try {
                String lineIfTxtFile;
                while ((lineIfTxtFile = linesInTxtFile.readLine()) != null) {
                      records.add(Float.parseFloat(lineIfTxtFile));
                }
            } finally {
                //necessarily close file
                linesInTxtFile.close();
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        return records;
    }

    private static void write(String pathToFolder, List<Float> records) {
        //Check empty file or not
        if (!records.isEmpty()) {
            File file = new File(pathToFolder);
            try {
                //If the file does not exist then create it
                if (!file.exists()) {
                    file.createNewFile();
                }

                PrintWriter out = new PrintWriter(file.getAbsoluteFile());
                try {
                    //Writing to a file using the ForEach
                    records.forEach(record -> {
                        out.print(record + "\n");
                    });
                } finally {
                    //Close the file
                    out.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // print message if records exist
            System.out.println("File successfully written!\n Number of records: " + records.size() + "\n");
        } else {
            //print message if records doesn't exist
            System.out.println("Records to these parameters is not found\n");
        }
    }
}
