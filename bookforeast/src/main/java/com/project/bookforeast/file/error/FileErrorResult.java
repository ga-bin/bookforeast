package com.project.bookforeast.file.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FileErrorResult {
	EMPTY_FILE(HttpStatus.BAD_REQUEST, "요청 파일이 정상적으로 전송되지 않았습니다."),
	UPLOAD_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패하였습니다.")
	;

	
	private final HttpStatus status;
	private final String message;
	
}
