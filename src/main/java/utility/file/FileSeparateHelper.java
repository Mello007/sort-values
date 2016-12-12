package utility.file;
import java.io.*;
import java.util.*;

/**
 * This class puts out-data into the file
 */
public class FileSeparateHelper {

    private static final String STRING_IS_NOT_FLOAT = "Number is not float: %s";

    private final String pathToInputFile;
    private final Integer sizeOfFile;

    /**
     * @param pathToInputFile - path to Input File
     * @param sizeOfFile - size Of file
     */
    public FileSeparateHelper(String pathToInputFile,  Integer sizeOfFile){
        this.pathToInputFile = pathToInputFile;
        this.sizeOfFile = sizeOfFile;
    }

    /** This main method of MergeSortHelper. It creates temp files and writes sorted data.
     * @return List with tempFiles
     * @throws Exception
     */
    public List<File> processFile() throws Exception {

        final File inputFile = new File(pathToInputFile);
        FileReader fileReader = new FileReader(inputFile.getAbsoluteFile());
        BufferedReader linesInTxtFile = new BufferedReader(fileReader);
        List<Float> partOfFile;
        List<File> tempFiles = new ArrayList<>();

        while (linesInTxtFile.readLine() != null){
            partOfFile = this.readPartOfInputFile(linesInTxtFile);
            tempFiles.add(this.writeTempFile(partOfFile));
        }

        fileReader.close();
        linesInTxtFile.close();
        return tempFiles;
    }

    private List<Float> readPartOfInputFile(BufferedReader bufferedReader) throws IOException {
        List<Float> records = new ArrayList<>();
        String lineInFile = null;
        boolean isNeedToAddNumberInList = records.size() != sizeOfFile && (lineInFile = bufferedReader.readLine()) != null;
        while (isNeedToAddNumberInList) {
            try {
                records.add(Float.valueOf(lineInFile));
            } catch (NumberFormatException e){
                throw new RuntimeException(String.format(STRING_IS_NOT_FLOAT, lineInFile));
            }
            isNeedToAddNumberInList = records.size() != sizeOfFile && (lineInFile = bufferedReader.readLine()) != null;
        }
        //A method for sorting from top to bottom
        Collections.sort(records, Collections.reverseOrder());
        return records;
    }

    private File writeTempFile(List<Float> records) throws IOException {
        File file = File.createTempFile("tempfile", ".tmp");
        try (PrintWriter out = new PrintWriter(file.getAbsoluteFile()))  {
            records.forEach(out::println);
        } catch (IOException e) {
            throw new RuntimeException("Error in writing temp File!");
        }
        records.clear();
        return file;
    }
}
