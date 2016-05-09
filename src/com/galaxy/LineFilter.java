package com.galaxy;

/**
 * Created by Lars on 5/2/2016.
 *
 * A filter to provide conversion type and pattern identification
 */
public class LineFilter {

    private ConversionLine.Type type;
    private String pattern;

    public LineFilter(ConversionLine.Type type, String pattern) {
        this.type = type;
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }

    public ConversionLine.Type getType() {
        return type;
    }
}
