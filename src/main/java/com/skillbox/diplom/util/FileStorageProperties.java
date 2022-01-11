package com.skillbox.diplom.util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@NoArgsConstructor
public class FileStorageProperties {

    @Getter
    private final String uploadDir = "upload";

    @Getter
    @Value("${file.extensions}")
    private String extensions;

    @Getter
    @Value("${file.max-file-size}")
    private String maxSize;

    private static final List<String> UNITS_INFO = List.of("KB", "MB", "GB", "TB");

    private static final String DEFAULT_SIZE = "5MB";
    private static final int DEFAULT_BYTE = 1024;

    private static final String REG_EX = "(\\d+)([TKMGBkmgb]{2})";

    public long getSizeFileToByte() {
        long sizeFileToByte = 0;
        if (Objects.isNull(maxSize) || !maxSize.matches(REG_EX)) {
            maxSize = DEFAULT_SIZE;
        }
        Pattern pattern = Pattern.compile(REG_EX);
        Matcher matcher = pattern.matcher(maxSize);
        if (matcher.find()) {
            String unit = matcher.group(2);
            int power = UNITS_INFO.contains(unit) ? UNITS_INFO.indexOf(unit) + 1 : 1;
            sizeFileToByte = Math.round(Math.pow(DEFAULT_BYTE, power)) * Long.parseLong(matcher.group(1));
        }
        return sizeFileToByte;
    }
}
