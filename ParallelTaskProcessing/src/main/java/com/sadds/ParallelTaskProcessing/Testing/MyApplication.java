package com.sadds.ParallelTaskProcessing.Testing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyApplication {
    private static final Logger logger = LoggerFactory.getLogger(MyApplication.class);

    public static void main(String[] args) {
        logger.info("This is an info message");
        logger.error("This is an error message");
        logger.debug("This is a debug message");
    }
}
