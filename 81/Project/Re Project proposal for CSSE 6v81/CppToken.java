package codestudy.cpp;

import codestudy.coverage.lexers.Token;

/**
 * @author Mark Gabel
 */
public class CppToken implements Token {
    private String text;
    private int type;
    private int line, column;

    public CppToken(String text, int type) {
        this(text, type, 0, 0);
    }

    public CppToken(String text, int type, int line, int column) {
        this.text = text;
        this.type = type;
        this.line = line;
        this.column = column;
    }

    public boolean isHidden() {
        return type == CppLexer.COMMENT || type == CppLexer.WHITE;
    }

    public boolean isEOF() {
        return type == CppLexer.YYEOF;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getType() {
        return type;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public void setType(int type) {
        this.type = type;
    }
}
