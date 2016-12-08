import java.io.*;
import java.util.*;

class FileSeparateHelper {
    private static final String STRING_IS_NOT_FLOAT = "Number is not float: %s";

    private final String pathToInputFile;
    private final String pathToOutputFile;
    private final Integer sizeOfFile;

    public FileSeparateHelper(String pathToInputFile, String pathToOutputFile, Integer sizeOfFile){
        this.pathToInputFile = pathToInputFile;
        this.pathToOutputFile = pathToOutputFile;
        this.sizeOfFile = sizeOfFile;
    }

    public List<File> processFile() throws Exception{
        int recordNumber = 0;
        final File inputFile = new File(pathToInputFile);
        BufferedReader linesInTxtFile = null;

        FileReader fileReader = new FileReader(inputFile.getAbsoluteFile());
        linesInTxtFile = new BufferedReader(fileReader);
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
        List<Float> records = new LinkedList<>();
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
        //Check empty file or not
            File file = new File(pathToTempFile);
            try (PrintWriter out = new PrintWriter(file.getAbsoluteFile()))  {
                //If the file does not exist then create it
                file.createNewFile();
                records.forEach(out::println);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        return file;
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
