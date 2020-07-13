import java.io.*;

public class SimpleShell{
    public static void main(String[] args) throws IOException{
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

        while(true){
            System.out.print("SimpleShell>>");
            String cmdLine = console.readLine();

            if(cmdLine.equals("exit")){ 
                System.out.println("Thank you for using SimpleShell!");
                break;
            }
            else if(cmdLine.length()<5) System.out.println("Invalid command. Type 'help' to see a list of commands.");
            else if(cmdLine.equals("")) continue;
            else if(cmdLine.substring(0,4).equals("cal ")){
                //String s = "99*( ( 33.5+45*(-2))+35+(-9.0))-7/(2^(-6))*(5)-4";
                Calculator c = new Calculator(cmdLine.substring(4).strip());
                try{        
                    System.out.println(c.evaluate());
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
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
                System.out.println("For more help with the respective functions type \"help\" behind the command (eg, cal help)");
            }
            else System.out.println("Invalid command. Type 'help' to see a list of commands.");

        }
    }
}
