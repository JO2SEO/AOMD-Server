package jo2seo.aomd.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionType {
    
    // JWT
    USER_INFORMATION_NOT_FOUND(HttpStatus.NOT_FOUND, "유저 정보가 없습니다."),
    TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "토큰이 없습니다."),
    CANNOT_SET_TOKEN(HttpStatus.BAD_REQUEST, "토큰 상태를 변경할 수 없습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효한 토큰이 아닙니다."),

    // USER
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 없습니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "잘못된 비밀번호입니다."),
    DUPLICATED_EMAIL(HttpStatus.BAD_REQUEST, "이미 가입된 이메일입니다."),

    // Portfolio
    PORTFOLIO_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 포트폴리오를 찾을 수 없습니다."),
    PORTFOLIO_NOT_SHARING(HttpStatus.FORBIDDEN, "해당 포트폴리오에 접근할 수 없습니다."),
    PORTFOLIO_NOT_MINE(HttpStatus.FORBIDDEN, "해당 포트폴리오에 대한 접근 권한이 없습니다"),
    PORTFOLIO_BLOCK_NOT_MATCHING(HttpStatus.INTERNAL_SERVER_ERROR, "포트폴리오 블록 순서에 문제가 있습니다."),
    PORTFOLIO_BLOCK_NOT_EXIST(HttpStatus.CONFLICT, "해당 포트폴리오에 동일한 블럭이 이미 존재합니다.");

    private final HttpStatus httpStatus;
    private final String detail;

    ExceptionType(final HttpStatus httpStatus, final String detail) {
        this.httpStatus = httpStatus;
        this.detail = detail;
    }
}
