
import utility.file.FileSeparateHelper;
import utility.file.MergeSortHelper;
import validation.ValidationUtils;

import java.io.File;
import java.util.List;

public class Main {

    private static final ValidationUtils VALIDATION_UTILS = new ValidationUtils();

    public static void main(String[] args) throws Exception{
       if (args.length == 3 && VALIDATION_UTILS.checkArgumentsRestrictions(args[0], args[1], args[2])) {

            FileSeparateHelper fileWorker = new FileSeparateHelper(args[0], Integer.parseInt(args[2]));
            List<File> tempFiles = fileWorker.processFile();
            MergeSortHelper mergeSortHelper = new MergeSortHelper(args[1], Integer.parseInt(args[2]));
            mergeSortHelper.merge(tempFiles);
            tempFiles.forEach(File::deleteOnExit);

        } else return;
    }
}



