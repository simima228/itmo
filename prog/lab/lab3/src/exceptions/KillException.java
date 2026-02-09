package exceptions;

public class KillException extends Exception {
    @Override
    public String getMessage() {
        return "Нельзя убить то, что уже мертво.";
    }

}
