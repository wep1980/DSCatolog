package com.wepdev.dscatalog.resources.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;

/**
 * Classe que vai exibir os campos de erro na representação
 */
@Getter
@Setter
public class ErroPadrao implements Serializable {

    private Instant timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

    public ErroPadrao() {
    }
}
