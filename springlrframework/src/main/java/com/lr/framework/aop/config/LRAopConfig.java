package com.lr.framework.aop.config;

import lombok.Data;

/**
 *
 */
@Data
public class LRAopConfig {

    private String pointCut;
    private String apsectClass;
    private String aspectBefore;
    private String aspectAfter;
    private String aspectAfterThrow;
    private String aspectAfterThrowingName;
}
