package org.hyeon.file.exceptions;

import org.hyeon.global.exceptions.script.AlertBackException;
import org.springframework.http.HttpStatus;

public class FileNotFoundException extends AlertBackException {

    public FileNotFoundException(){
        super("NotFound.file", HttpStatus.NOT_FOUND);
        setErrorCode(true);
    }
}
