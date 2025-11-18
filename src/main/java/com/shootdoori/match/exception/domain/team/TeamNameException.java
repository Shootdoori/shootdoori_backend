package com.shootdoori.match.exception.domain.team;

import com.shootdoori.match.exception.common.BusinessException;
import com.shootdoori.match.exception.common.ErrorCode;

public class TeamNameException extends BusinessException {

    public TeamNameException(ErrorCode errorCode) {
        super(errorCode);
    }
}
