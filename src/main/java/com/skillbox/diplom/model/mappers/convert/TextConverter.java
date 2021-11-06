package com.skillbox.diplom.model.mappers.convert;

import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class TextConverter {

    private static final String SPACE = " ";
    private static final int MAX_LEN_ANNOUNCE = 150;
    private static final  String CHOOSE_TAGS = "<.+?>";
    private static final  String ELLIPSIS = "...";

    @Named("textToAnnounce")
    public String textToAnnounce(String text) {
        if (Objects.isNull(text)) {
            return null;
        }
        String announce = text.replaceAll(CHOOSE_TAGS, "");
        announce = announce.substring(0, announce.length() >= MAX_LEN_ANNOUNCE ? MAX_LEN_ANNOUNCE : announce.length() - 1);
        int lastSpaceIndex = !announce.contains(SPACE) ? announce.length() : announce.lastIndexOf(SPACE);
        return announce.substring(0, lastSpaceIndex).concat(ELLIPSIS);
    }
}
