package validation;

import java.io.File;

public class ValidationUtils {

    private static final int MIN_VALUE = 1;
    private static final String PATH_IS_NOT_VALID = "Path to input file is not valid: %s";
    private static final String FILE_IS_NOT_READABLE = "Input file is not readable: %s";
    private static final String FILE_IS_NOT_DIRECTORY = "File is not directory: %s";
    private static final String FILE_IS_NOT_WRITABLE = "File is not writable: %s";
    private static final String SIZE_IS_NOT_VALID = "Size is not valid: %s . Restrictions - min size: %s, max size: %s";

    public boolean checkArgumentsRestrictions(String pathToInputFile, String pathToOutputFile, String size){
        boolean pathToInputFileIsValid = this.validateInputFileInFileSystem(pathToInputFile);
        boolean pathToOutputFileIsValid = this.validateOutputDirectory(pathToOutputFile);
        boolean sizeIsValid = this.validateSize(size);
        return pathToInputFileIsValid && pathToOutputFileIsValid && sizeIsValid;
    }

    private boolean validateInputFileInFileSystem(String pathToInputFile) {
        final File file = new File(pathToInputFile);
        final boolean pathToInputFileIsValid = file.exists() && !file.isDirectory();
        final boolean fileIsReadable = file.canRead();

        if (!pathToInputFileIsValid) {
            throw new RuntimeException(String.format(PATH_IS_NOT_VALID, pathToInputFile));
        }

        if (!fileIsReadable){
            throw new RuntimeException(String.format(FILE_IS_NOT_READABLE, pathToInputFile));
        }
        return true;
    }

    private boolean validateOutputDirectory(String pathToOutputFile){
        final File file = new File(pathToOutputFile);
        final boolean isDirectory = file.isDirectory();
        final boolean isWritable = file.canWrite();
        if (!isDirectory){
            throw new RuntimeException(String.format(FILE_IS_NOT_DIRECTORY, pathToOutputFile));
        }

        if (!isWritable){
            throw new RuntimeException(String.format(FILE_IS_NOT_WRITABLE, pathToOutputFile));
        }
        return true;
    }

    private boolean validateSize(String size){
        final Integer sizeOfFile = Integer.parseInt(size);
        boolean isValidSize = sizeOfFile < Integer.MAX_VALUE && sizeOfFile > ValidationUtils.MIN_VALUE;

        if (!isValidSize){
            throw new RuntimeException(String.format(SIZE_IS_NOT_VALID, size, ValidationUtils.MIN_VALUE, Integer.MAX_VALUE));
        }
        return true;
    }
}
