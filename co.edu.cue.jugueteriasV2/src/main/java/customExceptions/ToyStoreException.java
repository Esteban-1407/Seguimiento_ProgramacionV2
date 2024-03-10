package customExceptions;

public class ToyStoreException extends Exception {
    public ToyStoreException(String message) {
        super(message);
    }


public static class ToyNotFoundException extends ToyStoreException {
    public ToyNotFoundException(String message) {
        super(message);
    }
}

public static class InvalidQuantityException extends ToyStoreException {
    public InvalidQuantityException(String message) {
        super(message);
    }
}

public static class InvalidPriceException extends ToyStoreException {
    public InvalidPriceException(String message) {
        super(message);
    }
}
}
