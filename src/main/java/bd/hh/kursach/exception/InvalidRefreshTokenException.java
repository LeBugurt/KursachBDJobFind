package bd.hh.kursach.exception;

public class InvalidRefreshTokenException extends RuntimeException {
    public InvalidRefreshTokenException(String massage) { super(massage); }
}
