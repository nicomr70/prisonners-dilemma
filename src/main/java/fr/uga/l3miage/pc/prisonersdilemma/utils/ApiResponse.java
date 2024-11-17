package fr.uga.l3miage.pc.prisonersdilemma.utils;

public class ApiResponse<T> {

    private int code;
    private String message;
    private T data;
    private String type;

    // Constructeur par défaut
    public ApiResponse() {
    }

    public ApiResponse(int code, String message, String type, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.type = type;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    // Getters et Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", type='" + type + '\'' +
                '}';
    }
}

