package com.ramp.learnmybatis.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class PasswordUtils {

    private static final Logger LOG = LoggerFactory.getLogger(PasswordUtils.class);

    private static final String USER_HOME = System.getProperty("user.home");
    private static final String REGEX_NL = "([\\r\\n])";

    public static String getPasswordFromFile(String username) {
        return getConfigPasswordFromFile("~/passwords/%s.b64", username);
    }

    private static String getConfigPasswordFromFile(String filePath, String username) {
        String filePattern = null;
        if (StringUtils.hasText(filePath)) {
            filePattern = filePath;
        }

        String password = null;
        if (!StringUtils.hasText(filePattern)) {
            LOG.error("failed to resolve password. filePath pattern is empty");
        } else {
            final String userHome = StringUtils.replace(USER_HOME, "\\", "/");
            filePattern = filePattern.replace("~", userHome);
            final String path = String.format(filePattern, username);
            LOG.debug("password resolve: reading {}", path);
            File file = new File(path);
            try (final InputStream is = new FileInputStream(file);
                 final InputStreamReader isReader = new InputStreamReader(is, StandardCharsets.UTF_8);
                 final BufferedReader br = new BufferedReader(isReader)) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (!line.isBlank() && !line.startsWith("#")) {
                        line = line.replaceAll(REGEX_NL, "");
                        final byte[] decoded = Base64.getDecoder().decode(line.getBytes(StandardCharsets.UTF_8));
                        password = new String(decoded, StandardCharsets.UTF_8);
                        password = password.replaceAll(REGEX_NL, "");
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (!StringUtils.hasText(password)) {
            throw new RuntimeException("Password not found from the file: " + filePath);
        }
        return password;
    }
}
