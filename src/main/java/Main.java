import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String [] args){
        List<Float> inputFile = new ArrayList<>();

        Collections.sort(inputFile, Collections.reverseOrder());
    }



    private List<Float> takeRecordsFromFile(String fileName,  String pathToFolder){
        //Create new file (address = pathToFolder + fileName
        File file = new File(pathToFolder+fileName);
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

    private void write(String path, List<Float> records, String filename) {
        //Check empty file or not
        if (!records.isEmpty()) {
            File file = new File(path + filename + ".txt");
            try {
                //If the file does not exist then create it
                if (!file.exists()) {
                    file.createNewFile();
                }

                PrintWriter out = new PrintWriter(file.getAbsoluteFile());
                try {
                    //Writing to a file using the ForEach
                    records.forEach(record -> {
                        // first - User Name + |
                        // next - date + |
                        // and last - message + \n (new line)
                        out.print("\n");
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
