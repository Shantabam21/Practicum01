import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardOpenOption.CREATE;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class PersonGenerator {
    public static void main(String[] args) {

        ArrayList<String> records = new ArrayList<>();
        Scanner in = new Scanner(System.in);
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        String ID = "";
        String firstName = "";
        String lastName = "";
        String title = "";
        int yearOfBirth = 0;

        boolean done = false;

        do {

            ID = SafeInput.getNonZeroLenString(in, "Enter ID [6 digits]: ");
            firstName = SafeInput.getNonZeroLenString(in, "Enter first name: ");
            lastName = SafeInput.getNonZeroLenString(in, "Enter last name: ");
            title = SafeInput.getNonZeroLenString(in, "Enter title: ");
            yearOfBirth = SafeInput.getRangedInt(in,"Enter birth year: ", 1000, 9999);

            String str = ID + ", " + firstName + ", "  + lastName + ", " + title + ", " + yearOfBirth;
            records.add(str);
            done = SafeInput.getYNConfirm(in, "Enter Yes or No: ");
        }

        while (!done);
            File workingDirectory = new File(System.getProperty("user.dir"));
            Path file = Paths.get(workingDirectory.getPath() + "\\src\\data.txt");

            try
            {
                // Typical java pattern of inherited classes
                // we wrap a BufferedWriter around a lower level BufferedOutputStream
                OutputStream out =
                        new BufferedOutputStream(Files.newOutputStream(file, CREATE));
                BufferedWriter writer =
                        new BufferedWriter(new OutputStreamWriter(out));

                // Finally can write the file LOL!

                for(String record : records)
                {
                    writer.write(record, 0, record.length());  // stupid syntax for write rec
                    // 0 is where to start (1st char) the write
                    // rec. length() is how many chars to write (all)
                    writer.newLine();  // adds the new line

                }
                writer.close(); // must close the file to seal it and flush buffer
                System.out.println("Data file written!");
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }


    }
}