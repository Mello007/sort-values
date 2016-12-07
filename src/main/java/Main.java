
import java.util.Scanner;

public class Main {

    public static void main(String[] args){

        //path to input file
        String pathToInputFile;
        //path to output file
        String pathToOutputFile;

        Integer size;
        //object of Scanner
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter path to input file");
        pathToInputFile = scanner.next();
        System.out.println("Enter path to output file");
        pathToOutputFile = scanner.next();
        System.out.println("Enter the size of file");
        size = scanner.nextInt();
        new FileWorker(pathToInputFile, pathToOutputFile, size);
    }
}
