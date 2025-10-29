package com.juniorsfredo.xtreme_management_api.core.parsers;

public class CpfParser {
    public static String parse(String rawCpf) {
        return rawCpf.replaceAll("[^\\d]", "");
    }
}
