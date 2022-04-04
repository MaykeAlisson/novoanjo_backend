package br.com.novoanjo.novoanjo.infra.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public final class UtilDate {

    public static boolean isValida(final LocalDateTime value) {
        return value != null;
    }

    public static Date toDate(final LocalDateTime localDateTime) {
        return localDateTime != null ? Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()) : null;
    }
}
