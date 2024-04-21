package shared.referenceClasses;

public class Response <T>{

    private T data;
    private boolean isSuccess;

    public Response(T data, boolean isSuccess) {
        this.data = data;
        this.isSuccess = isSuccess;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
