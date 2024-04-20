package shared.referenceClasses;

public class Response <T>{

    private T data;
    private boolean isSuccess;

    public Response(T data, boolean isSuccess) {
        this.data = data;
        this.isSuccess = isSuccess;
    }
}
