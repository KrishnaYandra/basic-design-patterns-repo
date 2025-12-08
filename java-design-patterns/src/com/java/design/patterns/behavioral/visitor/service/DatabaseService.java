package com.java.design.patterns.behavioral.visitor.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class DatabaseService {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseService.class);

    public int executeUpdate(String sql, Object... params) {
        // Just log what would be executed
        logger.info("Executing SQL: {}", sql);
        logger.info("With params: {}", Arrays.toString(params));

        // Return 1 as if one row was updated/inserted
        return 1;
    }
}
