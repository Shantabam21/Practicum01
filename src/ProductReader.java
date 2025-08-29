import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import static java.nio.file.StandardOpenOption.CREATE;

public class ProductReader {
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
                int nameHighest = "Name".length();
                int descriptionHighest = "Description".length();
                ArrayList<String[]> row  = new ArrayList<String[]>();

                while (reader.ready()) {
                    rec = reader.readLine();
                    line++;
                    // echo to screen

                    String[] format = rec.split(",");

                    for (int i = 0; i < format.length; i++) {
                        format[i] = format[i].trim();

                    }
                    if (format[1].length() > nameHighest) {
                        nameHighest = format[1].length();
                    }
                    if (format[2].length() > descriptionHighest) {
                        descriptionHighest = format[2].length();
                    }

                    row.add(format);
                    //tst = String.format("%-8s %-" + nameHighest + "s %-" + descriptionHighest + "s %-" + titleHighest + "s %4s", format[0], format[1], format[2], format[3], format[4]);
                    //n.add(tst);
                    //System.out.printf("\nLine %4d %-60s ", line, rec);

                }
                reader.close(); // must close the file to seal it and flush buffer
                String formatString = String.format("%%-%ds %%-%ds %%-%ds %%-%ds",  8 , nameHighest + 2, descriptionHighest + 2, 4);
                String headline = String.format(formatString, "ID#", "Name", "Description", "Cost");
                System.out.println(headline);
                int total = (nameHighest + 2) + (descriptionHighest + 2)  + 8 + 4;
                for (int i = 0; i < total; i++) {
                    System.out.print("=");

                }
                System.out.println();

                for (String[] format : row) {

                    System.out.println(String.format(formatString, format[0], format[1], format[2], format[3]));
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
