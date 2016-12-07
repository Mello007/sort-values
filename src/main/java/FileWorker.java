import java.io.*;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class FileWorker {


    FileWorker(String pathToInputFile, String pathToOutputFile, Integer sizeOfFile){

    }

    private void sortInputFiles(){

    }

    private List<Float> takeRecordsFromFile(String pathToFile, String pathToOutputFile, Integer sizeOfFile){
        int recordNumber = 0;
        File file = new File(pathToFile);
        List<Float> records = new ArrayList<>();
        try {
            BufferedReader linesInTxtFile = new BufferedReader(new FileReader(file.getAbsoluteFile()));
            try {
                String lineIfTxtFile;
                while ((lineIfTxtFile = linesInTxtFile.readLine()) != null) {
                    //If size of List == size Of file
                    if (records.size() == sizeOfFile){
                        //sort this list
                        Collections.sort(records, Collections.reverseOrder());
                        //write it in file
                        write(pathToOutputFile, records, recordNumber);
                        //clear list
                        records.clear();
                        recordNumber++;
                    } else
                    // add Float numbers
                        records.add(Float.parseFloat(lineIfTxtFile));
                }
            } finally {
                write(pathToOutputFile, records, recordNumber);
                linesInTxtFile.close();
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        return records;
    }




    private void write(String pathToFolder, List<Float> records, int recordNumber) {
        //Check empty file or not
        if (!records.isEmpty()) {
            File file = new File(pathToFolder+recordNumber+".txt");
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


    private void write(String pathToFolder, List<Float> records) {
        //Check empty file or not
        if (!records.isEmpty()) {
            File file = new File(pathToFolder+"output.out");
            try {
                //If the file does not exist then create it
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileWriter writer = new FileWriter(pathToFolder+"output.out", true);
                BufferedWriter bufferWriter = new BufferedWriter(writer);
                try {
                    //Writing to a file using the ForEach
                    records.forEach(record -> {
                        try {
                            bufferWriter.write(String.valueOf(record));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                } finally {
                    //Close the file
                    bufferWriter.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // print message if records exist
        } else {
            //print message if records doesn't exist
            System.out.println("Records to these parameters is not found\n");
        }
    }

    private void sortOutputFiles(String pathToFolder){

        File listOfFiles = new File(pathToFolder);
        List<String> exportFiles = Arrays.asList(listOfFiles.list());
        List<Float> numbers = new ArrayList<>();


            exportFiles.forEach(file -> {
                Float number = openSortedFileAndReturnNumber(pathToFolder + file);
                numbers.add(number);
            });
            Collections.sort(numbers, Collections.reverseOrder());

    }

    private Float openSortedFileAndReturnNumber(String pathToFile){
        File file = new File(pathToFile);
        Float lineIfTxtFile;
        try {
            BufferedReader linesInTxtFile = new BufferedReader(new FileReader(file.getAbsoluteFile()));

            try {
                lineIfTxtFile = Float.parseFloat(linesInTxtFile.readLine());
            } finally {
                linesInTxtFile.close();
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        return lineIfTxtFile;
    }
}
