package bridge.domain;

public enum ErrorStatus {

    NOT_ERROR(""),
    INVALID_INPUT("[ERROR] 올바르지 않은 입력입니다."),
    OUT_OF_RANGE("[ERROR] 다리 길이는 3부터 20 사이의 숫자여야 합니다.");

    private final String message;

    ErrorStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public boolean isNotError() {
        return this == NOT_ERROR;
    }

    public boolean isInvalidInput() {
        return this == INVALID_INPUT;
    }

    public boolean isOutOfRange() {
        return this == OUT_OF_RANGE;
    }
}
