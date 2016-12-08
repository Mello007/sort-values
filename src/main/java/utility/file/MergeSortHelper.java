package utility.file;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
public class MergeSortHelper {

    private final String NAME_OF_OUT_FILE = "sorted_logs";

    private final String pathToOutPutFile;

    /**
     *
     * @param pathToOutPutFile
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

    private void writeInOutputFile(String number){
            try {
                FileWriter writer = new FileWriter(pathToOutPutFile+NAME_OF_OUT_FILE, true);
                BufferedWriter bufferWriter = new BufferedWriter(writer);
                try {
                    bufferWriter.write(number +"\n");
                } finally {
                    bufferWriter.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // print message if records exist
    }

    /**
     *
     * @param tempFiles
     * @throws Exception
     */
    public void merge(List<File> tempFiles) throws Exception {
        List<MergeStructure> mergeStructors = tempFiles.stream().map(MergeStructure::new).collect(Collectors.toList());
        createOutputFile(pathToOutPutFile);
        while (!mergeStructors.isEmpty()){
            MergeStructure maxMergeStructor = mergeStructors.stream().max(MergeStructure::compareTo).get();
            writeInOutputFile(maxMergeStructor.toString());
            if (maxMergeStructor.next() == null) {
                mergeStructors.remove(maxMergeStructor);
            };
        }
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
