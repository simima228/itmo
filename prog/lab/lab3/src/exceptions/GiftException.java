package exceptions;

public class GiftException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Нельзя подарить того, чего нет.";
    }
}
