import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MergeSortHelper {

    private static final String NAME_OF_OUT_FILE = "data.out";

    private final String pathToOutPutFile;

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
                FileWriter writer = new FileWriter(pathToOutPutFile, true);
                BufferedWriter bufferWriter = new BufferedWriter(writer);
                try {
                    bufferWriter.write(number);
                } finally {
                    bufferWriter.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // print message if records exist
    }

    void merge(List<File> tempFiles){
        List<MergeStructor> mergeStructors = tempFiles.stream().map(MergeStructor::new).collect(Collectors.toList());

        while (!mergeStructors.isEmpty()){
            MergeStructor maxMergeStructor = mergeStructors.stream().max(MergeStructor::compareTo).get();
            writeInOutputFile(maxMergeStructor.toString());
        }
    }

    static class MergeStructor implements Comparable {
        private final File file;
        private Float currentNumber;


        public MergeStructor(File file) {
            this.file = file;
            try {
                getCurrentNumberFromTempFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void getCurrentNumberFromTempFile() throws IOException {
            BufferedReader linesInTxtFile = new BufferedReader(new FileReader(file.getAbsoluteFile()));
            try {
                currentNumber = Float.parseFloat(linesInTxtFile.readLine());
            } finally {
                linesInTxtFile.close();
            }
        }

        @Override
        public int compareTo(Object o) {
            if (o != null && o instanceof MergeStructor){
                return  ((MergeStructor) o).currentNumber.compareTo(this.currentNumber);
            }
            throw new RuntimeException("Is not compared object");
        }

        @Override
        public String toString(){
            return String.valueOf(currentNumber);
        }
    }
}
