import java.util.ArrayList;
import java.util.Scanner;

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
            done = SafeInput.getYNConfirm(in, "Enter Yes or No: ");
        }

        while (!done);



    }
}