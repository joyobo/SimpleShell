import java.io.*;

public class SimpleShell{
    public static void main(String[] args) throws IOException{
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

        while(true){
            System.out.print("SimpleShell>>");
            String cmdLine = console.readLine();

            if(cmdLine.equals("exit")) break;
            else if(cmdLine.equals("")) continue;
            else if(cmdLine.equals("help")){
                System.out.println();
                System.out.println("____________________________");
                System.out.println("|  Welcome to Simple Shell  |");
                System.out.println("|___________________________|");
                System.out.println();
                System.out.println("Commands:");
                System.out.println("1) exit ");
                System.out.println("2) cal \033[3mmath_expression_here\033[0m");
                System.out.println();
            }
            else System.out.println("Invalid command. Type 'help' to see a list of commands.");

        }
    }
}
