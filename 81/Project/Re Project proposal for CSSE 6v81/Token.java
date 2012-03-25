package codestudy.coverage.lexers;

/**
 * @author Mark Gabel
 */
public interface Token {
    public String getText();

    public void setText(String text);

    public int getType();

    public boolean isHidden();

    public boolean isEOF();

    public int getLine();

    public int getColumn();

    public static final Token EOF_TOKEN = new Token() {
        public String getText() { return ""; }

        public void setText(String text) { throw new UnsupportedOperationException(); }

        public int getType() { return -1; }

        public boolean isHidden() { return true; }

        public boolean isEOF() { return true; }

        public int getLine() { return 0; }

        public int getColumn() { return 0; }
    };
}
