package utility.file;
import java.io.*;
import java.util.*;

/**
 * This class puts out-data into the file
 */
public class FileSeparateHelper {

    private static final String STRING_IS_NOT_FLOAT = "Number is not float: %s";

    private final String pathToInputFile;
    private final String pathToOutputFile;
    private final Integer sizeOfFile;

    /**
     * @param pathToInputFile - path to Input File
     * @param pathToOutputFile - path to Output File
     * @param sizeOfFile - size Of file
     */
    public FileSeparateHelper(String pathToInputFile, String pathToOutputFile, Integer sizeOfFile){
        this.pathToInputFile = pathToInputFile;
        this.pathToOutputFile = pathToOutputFile;
        this.sizeOfFile = sizeOfFile;
    }

    /** This main method of MergeSortHelper. It creates temp files and writes sorted data.
     * @return List with tempFiles
     * @throws Exception
     */
    public List<File> processFile() throws Exception {
        //Counter for names of temp files
        int recordNumber = 0;

        final File inputFile = new File(pathToInputFile);

        FileReader fileReader = new FileReader(inputFile.getAbsoluteFile());
        BufferedReader linesInTxtFile = new BufferedReader(fileReader);
        List<Float> partOfFile = this.readPartOfInputFile(linesInTxtFile);
        List<File> tempFiles = new ArrayList<>();

        while (!partOfFile.isEmpty()){
            recordNumber++;
            String pathToTempFile = pathToOutputFile + recordNumber;
            tempFiles.add(this.writeTempFile(pathToTempFile, partOfFile));
            partOfFile = this.readPartOfInputFile(linesInTxtFile);
        }
        fileReader.close();
        linesInTxtFile.close();
        return tempFiles;
    }

    private List<Float> readPartOfInputFile(BufferedReader bufferedReader) throws IOException{
        List<Float> records = new ArrayList<>();
        String lineInFile = null;
        Float number;
        boolean isNeedToWriteInFile = records.size() != sizeOfFile && (lineInFile = bufferedReader.readLine()) != null;
        while (isNeedToWriteInFile) {
            try {
                number = Float.valueOf(lineInFile);
                records.add(number);
            } catch (NumberFormatException e){
                throw new RuntimeException(String.format(STRING_IS_NOT_FLOAT, lineInFile));
            }
            isNeedToWriteInFile = records.size() != sizeOfFile && (lineInFile = bufferedReader.readLine()) != null;
        }
        Collections.sort(records, Collections.reverseOrder());
        return records;
    }

    private File writeTempFile(String pathToTempFile, List<Float> records) {
        File file = new File(pathToTempFile);
        try (PrintWriter out = new PrintWriter(file.getAbsoluteFile()))  {
            file.createNewFile();
            records.forEach(out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return file;
    }
}
