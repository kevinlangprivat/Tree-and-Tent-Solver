package exceptions;

public class TentException extends Exception{
    public TentException(){
        System.out.println("There is already a tent for the selected tree!");
    }
}
