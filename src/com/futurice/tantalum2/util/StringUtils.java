package com.futurice.tantalum2.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Vector;
import javax.microedition.lcdui.Font;

/**
 * Utility methods for String handling
 *
 * @author ssaa, paul houghton
 */
public final class StringUtils {

    private static StringUtils singleton;
    private final static String ELIPSIS = "...";

    private static synchronized StringUtils getStringUtils() {
        if (singleton == null) {
            singleton = new StringUtils();
        }

        return singleton;
    }

    private StringUtils() {
    }

    ;
    
    /**
     * Truncates the string to fit the maxWidth. If truncated, an elipsis "..."
     * is displayed to indicate this.
     *
     * @param str
     * @param font
     * @param maxWidth
     * @return String - truncated string with ellipsis added to end of the
     * string
     */
    public static String truncate(final String str, final Font font, final int maxWidth) {
        if (font.stringWidth(str) <= maxWidth) {
            return str;
        }

        StringBuffer truncated = new StringBuffer(str);
        while (font.stringWidth(truncated.toString()) > maxWidth) {
            truncated.deleteCharAt(truncated.length() - 1);
        }
        truncated.delete(truncated.length() - ELIPSIS.length(), truncated.length() - 1);
        truncated.append(ELIPSIS);
        return truncated.toString();
    }
    private static Font charWidthFont = null;
    private static final HashLookup charKey = new HashLookup();
    private static final Hashtable fastFonts = new Hashtable();

    private static final class HashLookup {

        public int hash = 0;

        public int hashCode() {
            return hash;
        }
    }

    /**
     * A Font support class for fast Font.stringWidth() calculation
     *
     */
    private static final class FastFont {

        final Font font;
        final Hashtable charWidthHash = new Hashtable();

        public FastFont(final Font font) {
            this.font = font;
        }
    }

    /**
     * An accelerated version of Font.stringWidth()
     *
     * @param font
     * @param s
     * @return
     */
    public static synchronized int fastStringWidth(final Font font, final String s) {
        final int l = s.length();
        int w = 0;
        
        if (!fastFonts.contains(font)) {
            fastFonts.put(font, new FastFont(font));
        }        
        final FastFont fastFont = (FastFont) fastFonts.get(font);

        for (int i = 0; i < l; i++) {
            charKey.hash = s.charAt(i);
            final Integer val = (Integer) fastFont.charWidthHash.get(charKey);
            if (val != null) {
                w += val.intValue();
            } else {
                final int cw = font.charWidth(s.charAt(i));
                w += cw;
                fastFont.charWidthHash.put(charKey, new Integer(cw));
            }
        }

        return w;
    }

    /**
     * Split a string in to several lines of text which will display within a
     * maximum width.
     *
     * @param str
     * @param font
     * @param maxWidth
     * @return
     */
    public static Vector splitToLines(final String str, final Font font, final int maxWidth) {
        final Vector lines = new Vector();

        if (font.stringWidth(str) <= maxWidth) {
            lines.addElement(str);
            return lines;
        }

        StringBuffer currentLine = new StringBuffer();
        String word = null;
        int currentIndex = 0;
        int wordBoundaryIndex = str.indexOf(' ', currentIndex);
        if (wordBoundaryIndex == -1) {
            for (int i = 0; i < str.length(); i++) {
                if (font.stringWidth(str.substring(0, i)) > maxWidth) {
                    wordBoundaryIndex = i;
                    break;
                }
            }
        }

        while (currentIndex != -1 && currentIndex < str.length()) {

            word = str.substring(currentIndex, wordBoundaryIndex + 1);

            if (currentIndex == 0) {
                currentLine.append(word);
            } else {
                if (font.stringWidth((currentLine.toString() + " " + word)) < maxWidth) {
                    currentLine.append(" " + word);
                } else {
                    lines.addElement(currentLine.toString());
                    currentLine.setLength(0);
                    currentLine.append(word);
                }
            }

            currentIndex = wordBoundaryIndex + 1;
            wordBoundaryIndex = str.indexOf(' ', currentIndex);
            if (wordBoundaryIndex == -1) {
                wordBoundaryIndex = str.length() - 1;
                for (int i = currentIndex; i < str.length(); i++) {
                    if (font.stringWidth(str.substring(currentIndex, i)) > maxWidth) {
                        wordBoundaryIndex = i;
                        break;
                    }
                }
            }
        }
        lines.addElement(currentLine.toString());

        return lines;
    }

    /**
     * This method can not be static in order to access the current instance's
     * path
     *
     * @param name
     * @return
     * @throws IOException
     */
    private byte[] doReadBytesFromJAR(final String name) throws IOException {
        final InputStream in = getClass().getResourceAsStream(name);
        final byte[] bytes = new byte[in.available()];
        in.read(bytes);

        return bytes;
    }

    /**
     * Return a byte[] stored as a file in the JAR package
     *
     * @param name
     * @return
     * @throws IOException
     */
    public static byte[] readBytesFromJAR(final String name) throws IOException {
        return getStringUtils().doReadBytesFromJAR(name);
    }

    /**
     * Return a String object stored as a file in the JAR package
     *
     * @param name
     * @return
     * @throws IOException
     */
    public static String readStringFromJAR(final String name) throws IOException {
        return new String(readBytesFromJAR(name));
    }
}
