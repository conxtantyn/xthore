package com.xthore.common.config;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvEntry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import java.io.File;

public class EnvironmentConfiguration implements EnvironmentPostProcessor {
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        String directory = findDotenvDirectory(new File("."));
        Dotenv dotenv = Dotenv.configure()
            .directory(directory)
            .ignoreIfMissing()
            .ignoreIfMalformed()
            .load();
        for (DotenvEntry entry : dotenv.entries()) {
            System.setProperty(entry.getKey(), entry.getValue());
        }
    }
    private String findDotenvDirectory(File current) {
        if (current == null) return ".";
        if (new File(current, ".env").exists()) {
            return current.getAbsolutePath();
        }
        return findDotenvDirectory(current.getParentFile());
    }
}
