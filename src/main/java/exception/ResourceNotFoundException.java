package exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String filePath) {
        super(filePath + FileExceptionMessage.RESOURCE_NOT_FOUND);
    }
}
