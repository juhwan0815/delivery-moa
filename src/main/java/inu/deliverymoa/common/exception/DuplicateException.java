package inu.deliverymoa.common.exception;

public class DuplicateException extends RuntimeException{

    public static final String CATEGORY_DUPLICATE = "이미 존재하는 카테고리입니다.";
    public DuplicateException(String message) {
        super(message);
    }
}
