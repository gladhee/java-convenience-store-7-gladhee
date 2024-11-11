package store.exception;

public class FileException {

    public static class FileNotFoundException extends IllegalStateException {
        public FileNotFoundException(String fileName) {
            super(ErrorMessages.FILE_NOT_FOUND.getMessage() + fileName);
        }
    }

    public static class FileCannotReadException extends IllegalStateException {
        public FileCannotReadException(String fileName) {
            super(ErrorMessages.FILE_CANNOT_READ.getMessage() + fileName);
        }
    }

}
