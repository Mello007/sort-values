package utility.file;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  This class sorts the values of temporary files and writes them in the output file
 */
public class MergeSortHelper {

    private final String NAME_OF_OUT_FILE = "data";

    private final String pathToOutPutFile;

    /**
     * @param pathToOutPutFile path to output file entered by user
     */
    public MergeSortHelper(String pathToOutPutFile){
        this.pathToOutPutFile = pathToOutPutFile;
    }


    private void createOutputFile(String pathToOutPutFile){
        File file = new File(pathToOutPutFile + NAME_OF_OUT_FILE);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void writeInOutputFile(List<String> numbers){
            try {
                FileWriter writer = new FileWriter(pathToOutPutFile+NAME_OF_OUT_FILE, true);
                BufferedWriter bufferWriter = new BufferedWriter(writer);
                try {
                    numbers.forEach(number->{
                        try {
                            bufferWriter.write(number + System.lineSeparator());
                        } catch (IOException e) {
                            throw new RuntimeException("Error in writing!!!");
                        }
                    });
//                    bufferWriter.write(number + System.lineSeparator());
                } finally {
                    bufferWriter.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // print message if records exist
    }

    /**
     *  This method
     * @param tempFiles - List with temp files.
     * @throws Exception
     */
    public void merge(List<File> tempFiles) throws Exception {
        List<String> outputNumbers = new ArrayList<>();
        List<MergeStructure> mergeStructures = tempFiles.stream().map(MergeStructure::new).collect(Collectors.toList());
        createOutputFile(pathToOutPutFile);

        while (!mergeStructures.isEmpty()){
             //List with mergeStructures which contain sorted numbers
            MergeStructure maxMergeStructure = mergeStructures.stream().max(MergeStructure::compareTo).get();
            outputNumbers.add(maxMergeStructure.toString());
            if (maxMergeStructure.next() == null) {
                mergeStructures.remove(maxMergeStructure);
            };
        }
        writeInOutputFile(outputNumbers);
    }

    private static class MergeStructure implements Comparable {
        private Float currentNumber;
        private final BufferedReader bufferedReader;
        private final FileReader fileReader;


        public MergeStructure(File file)  {
            try {
                fileReader = new FileReader(file.getAbsoluteFile());
                bufferedReader = new BufferedReader(fileReader);
                next();
            } catch (Exception e) {
                throw new RuntimeException("Error with reading temp file. File path: " + file.getAbsolutePath());
            }
        }

        //A method which reads the next line and return it
        private Float next() throws Exception {
            String currentString = bufferedReader.readLine();
            if (currentString == null) {
                fileReader.close();
                bufferedReader.close();
                return null;
            } else if (currentString.isEmpty()) {
                throw new RuntimeException("Empty string in your file");
            } else {
                try {
                    currentNumber = Float.valueOf(currentString);
                } catch (NumberFormatException e) {
                    throw new RuntimeException("Is not valid float number: " + currentString);
                }
            }
            return this.currentNumber;
        }

        //A method for comparison currentNumbers
        @Override
        public int compareTo(Object o) {
            if (o != null && o instanceof MergeStructure){
                return this.currentNumber.compareTo(((MergeStructure) o).currentNumber);
            }
            throw new RuntimeException("Is not compared object");
        }

        @Override
        public String toString(){
            return currentNumber.toString();
        }
    }
}
