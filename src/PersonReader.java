import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.CREATE;

public class PersonReader
{
    public static void main(String[] args) {

        JFileChooser chooser = new JFileChooser();
        File selectedFile;
        String rec = "";

        try {
            // uses a fixed known path:
            //  Path file = Paths.get("c:\\My Documents\\data.txt");

            // use the toolkit to get the current working directory of the IDE
            // Not sure if the toolkit is thread safe...
            File workingDirectory = new File(System.getProperty("user.dir"));

            // Typiacally, we want the user to pick the file so we use a file chooser
            // kind of ugly code to make the chooser work with NIO.
            // Because the chooser is part of Swing it should be thread safe.
            chooser.setCurrentDirectory(workingDirectory);
            // Using the chooser adds some complexity to the code.
            // we have to code the complete program within the conditional return of
            // the filechooser because the user can close it without picking a file

            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                selectedFile = chooser.getSelectedFile();
                Path file = selectedFile.toPath();
                // Typical java pattern of inherited classes
                // we wrap a BufferedWriter around a lower level BufferedOutputStream
                InputStream in =
                        new BufferedInputStream(Files.newInputStream(file, CREATE));
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(in));

                // Finally we can read the file LOL!
                int line = 0;

                String tst = "";
                int firstNHighest = "FirstName".length();
                int secondNHighest = "SecondName".length();
                int titleHighest = "Title".length();
                ArrayList<String[]> row  = new ArrayList<String[]>();

                while (reader.ready()) {
                    rec = reader.readLine();
                    line++;
                    // echo to screen

                    String[] format = rec.split(",");

                    for (int i = 0; i < format.length; i++) {
                        format[i] = format[i].trim();

                    }
                    if (format[1].length() > firstNHighest) {
                        firstNHighest = format[1].length();
                    }
                    if (format[2].length() > secondNHighest) {
                        secondNHighest = format[2].length();
                    }
                    if (format[3].length() > titleHighest) {
                        titleHighest = format[3].length();
                    }
                    row.add(format);
                    //tst = String.format("%-8s %-" + firstNHighest + "s %-" + secondNHighest + "s %-" + titleHighest + "s %4s", format[0], format[1], format[2], format[3], format[4]);
                    //n.add(tst);
                    //System.out.printf("\nLine %4d %-60s ", line, rec);

                }
                reader.close(); // must close the file to seal it and flush buffer
                String formatString = String.format("%%-%ds %%-%ds %%-%ds %%-%ds %%-%ds",  8 ,firstNHighest + 2, secondNHighest + 2, titleHighest + 2, 4);
                String headline = String.format(formatString, "ID#", "FirstName", "LastName", "Title", "YOB");
                System.out.println(headline);
                int total = (firstNHighest + 2) + (secondNHighest + 2) + (titleHighest + 2) + 8 + 4;
                for (int i = 0; i < total; i++) {
                    System.out.print("=");

                }
                System.out.println();

                for (String[] format : row) {

                    System.out.println(String.format(formatString, format[0], format[1], format[2], format[3], format[4]));
                }


                System.out.println("\n\nData file read!");


            } else  // User closed the chooser without selecting a file
            {
                System.out.println("No file selected!!! ... exiting.\nRun the program again and select a file.");
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found!!!");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
