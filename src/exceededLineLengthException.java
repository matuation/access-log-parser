public class exceededLineLengthException extends RuntimeException {
    public exceededLineLengthException() {
        super("Найдена стока длиннее 1024 символов!");
    }
}
