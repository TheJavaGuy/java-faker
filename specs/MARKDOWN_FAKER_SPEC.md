# SPEC: Markdown Faker

COMPONENT: Markdown faker for realistic Markdown-formatted content (headers, paragraphs, lists, code blocks, links, images, tables, blockquotes)
DEPENDENCIES: Lorem faker for text content
EXISTING: src/main/resources/en/markdown.yml (extend with additional data)

## FILES

YAML_DATA: src/main/resources/en/markdown.yml
YAML_REGISTRATION: src/main/java/org/thejavaguy/javafaker/service/files/EnFile.java (add "markdown.yml" to FILES list)
JAVA_CLASS: src/main/java/org/thejavaguy/javafaker/Markdown.java
TEST_CLASS: src/test/java/org/thejavaguy/javafaker/MarkdownTest.java
FAKER_REGISTRATION: src/main/java/org/thejavaguy/javafaker/Faker.java

## IMPLEMENTATION_APPROACH

**CRITICAL**: Follow incremental testing to catch issues early:

1. Create YAML file with proper square bracket format
2. Register "markdown.yml" in EnFile.java FILES list
3. Verify YAML loads correctly with a simple test
4. Create Java class with one method
5. Add full test suite
6. Complete remaining methods

**Key Learnings Applied**:
- YAML must be registered in EnFile.java or fetchString() returns null
- Use square bracket notation for arrays: ["item1", "item2"]
- Test YAML loading BEFORE writing full implementation

## YAML_EXTENSION

```yaml
en:
  faker:
    markdown:
      headers: ["#", "##", "###", "####", "#####", "######"]
      emphasis: ["_", "~", "*", "**"]
      # Code block languages
      code_languages: ["java", "python", "javascript", "typescript", "bash", "sql", "json", "yaml", "xml", "html", "css", "ruby", "go", "rust", "kotlin", "swift", "c", "cpp", "csharp", "php"]
      # Table column headers for sample tables
      table_headers:
        - ["Name", "Age", "City"]
        - ["ID", "Product", "Price"]
        - ["Date", "Event", "Status"]
        - ["Title", "Author", "Year"]
        - ["Feature", "Description", "Priority"]
      # Sample image alt texts
      image_alt_texts: ["Sample image", "Screenshot", "Diagram", "Logo", "Chart", "Photo", "Illustration", "Icon"]
      # Blockquote attributions
      blockquote_attributions: ["Anonymous", "Unknown Author", "Traditional", "Ancient Proverb"]
```

## JAVA_IMPLEMENTATION

```java
package org.thejavaguy.javafaker;

import java.util.ArrayList;
import java.util.List;

/**
 * Generates realistic Markdown-formatted fake content.
 *
 * <p>This faker produces properly formatted Markdown that can be
 * rendered by any standard Markdown processor.</p>
 *
 * @since 1.x.x
 */
public class Markdown {

    private final Faker faker;

    protected Markdown(Faker faker) {
        this.faker = faker;
    }

    /**
     * Generates a random Markdown header (h1-h6).
     *
     * @return a header string, e.g., "## Lorem Ipsum"
     */
    public String header() {
        String prefix = faker.fakeValuesService().fetchString("markdown.headers");
        String text = capitalizeWords(faker.lorem().words(faker.random().nextInt(3) + 2));
        return prefix + " " + text;
    }

    /**
     * Generates a header of specific level (1-6).
     *
     * @param level the header level (1-6)
     * @return a header string, e.g., "### Lorem Ipsum"
     */
    public String header(int level) {
        if (level < 1 || level > 6) {
            throw new IllegalArgumentException("Header level must be between 1 and 6");
        }
        String prefix = "#".repeat(level);
        String text = capitalizeWords(faker.lorem().words(faker.random().nextInt(3) + 2));
        return prefix + " " + text;
    }

    /**
     * Generates emphasized text using random emphasis markers.
     *
     * @return emphasized text, e.g., "*important*" or "**bold**"
     */
    public String emphasis() {
        String marker = faker.fakeValuesService().fetchString("markdown.emphasis");
        String text = faker.lorem().word();
        return marker + text + marker;
    }

    /**
     * Generates bold text.
     *
     * @return bold text, e.g., "**important**"
     */
    public String bold() {
        return "**" + faker.lorem().word() + "**";
    }

    /**
     * Generates italic text.
     *
     * @return italic text, e.g., "*emphasis*"
     */
    public String italic() {
        return "*" + faker.lorem().word() + "*";
    }

    /**
     * Generates strikethrough text.
     *
     * @return strikethrough text, e.g., "~~removed~~"
     */
    public String strikethrough() {
        return "~~" + faker.lorem().word() + "~~";
    }

    /**
     * Generates inline code.
     *
     * @return inline code, e.g., "`variable`"
     */
    public String inlineCode() {
        return "`" + faker.lorem().word() + "`";
    }

    /**
     * Generates a fenced code block with random language.
     *
     * @return a code block with language hint
     */
    public String codeBlock() {
        String language = faker.fakeValuesService().fetchString("markdown.code_languages");
        return codeBlock(language);
    }

    /**
     * Generates a fenced code block with specified language.
     *
     * @param language the language for syntax highlighting
     * @return a code block with the specified language
     */
    public String codeBlock(String language) {
        StringBuilder sb = new StringBuilder();
        sb.append("```").append(language).append("\n");
        // Generate 2-5 lines of pseudo-code
        int lines = faker.random().nextInt(4) + 2;
        for (int i = 0; i < lines; i++) {
            sb.append(faker.lorem().sentence()).append("\n");
        }
        sb.append("```");
        return sb.toString();
    }

    /**
     * Generates an unordered (bullet) list.
     *
     * @return an unordered list with 3-5 items
     */
    public String unorderedList() {
        return unorderedList(faker.random().nextInt(3) + 3);
    }

    /**
     * Generates an unordered list with specified number of items.
     *
     * @param items number of list items
     * @return an unordered list
     */
    public String unorderedList(int items) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < items; i++) {
            sb.append("- ").append(capitalizeFirst(faker.lorem().sentence()));
            if (i < items - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * Generates an ordered (numbered) list.
     *
     * @return an ordered list with 3-5 items
     */
    public String orderedList() {
        return orderedList(faker.random().nextInt(3) + 3);
    }

    /**
     * Generates an ordered list with specified number of items.
     *
     * @param items number of list items
     * @return an ordered list
     */
    public String orderedList(int items) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < items; i++) {
            sb.append(i + 1).append(". ").append(capitalizeFirst(faker.lorem().sentence()));
            if (i < items - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * Generates a task list (checkbox list).
     *
     * @return a task list with 3-5 items, some checked
     */
    public String taskList() {
        return taskList(faker.random().nextInt(3) + 3);
    }

    /**
     * Generates a task list with specified number of items.
     *
     * @param items number of list items
     * @return a task list with random checked/unchecked items
     */
    public String taskList(int items) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < items; i++) {
            boolean checked = faker.random().nextBoolean();
            sb.append("- [").append(checked ? "x" : " ").append("] ");
            sb.append(capitalizeFirst(faker.lorem().sentence()));
            if (i < items - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * Generates a Markdown link.
     *
     * @return a link, e.g., "[Lorem Ipsum](https://example.com)"
     */
    public String link() {
        String text = capitalizeWords(faker.lorem().words(faker.random().nextInt(2) + 1));
        String url = faker.internet().url();
        return "[" + text + "](" + url + ")";
    }

    /**
     * Generates a Markdown link with specified text and URL.
     *
     * @param text the link text
     * @param url the URL
     * @return a link string
     */
    public String link(String text, String url) {
        return "[" + text + "](" + url + ")";
    }

    /**
     * Generates a Markdown image reference.
     *
     * @return an image, e.g., "![Sample image](https://example.com/image.jpg)"
     */
    public String image() {
        String alt = faker.fakeValuesService().fetchString("markdown.image_alt_texts");
        String url = faker.internet().image();
        return "![" + alt + "](" + url + ")";
    }

    /**
     * Generates a Markdown image with specified alt text and URL.
     *
     * @param altText the alt text
     * @param url the image URL
     * @return an image string
     */
    public String image(String altText, String url) {
        return "![" + altText + "](" + url + ")";
    }

    /**
     * Generates a blockquote.
     *
     * @return a blockquote, e.g., "> Lorem ipsum dolor sit amet."
     */
    public String blockquote() {
        return "> " + capitalizeFirst(faker.lorem().sentence());
    }

    /**
     * Generates a multi-line blockquote.
     *
     * @param lines number of lines in the blockquote
     * @return a multi-line blockquote
     */
    public String blockquote(int lines) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lines; i++) {
            sb.append("> ").append(capitalizeFirst(faker.lorem().sentence()));
            if (i < lines - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * Generates a horizontal rule.
     *
     * @return a horizontal rule string
     */
    public String horizontalRule() {
        String[] rules = {"---", "***", "___"};
        return rules[faker.random().nextInt(rules.length)];
    }

    /**
     * Generates a simple Markdown table.
     *
     * @return a table with 3 columns and 3-5 rows
     */
    public String table() {
        return table(3, faker.random().nextInt(3) + 3);
    }

    /**
     * Generates a Markdown table with specified dimensions.
     *
     * @param columns number of columns
     * @param rows number of data rows (excluding header)
     * @return a formatted Markdown table
     */
    public String table(int columns, int rows) {
        StringBuilder sb = new StringBuilder();

        // Header row
        sb.append("|");
        for (int c = 0; c < columns; c++) {
            sb.append(" ").append(capitalizeFirst(faker.lorem().word())).append(" |");
        }
        sb.append("\n");

        // Separator row
        sb.append("|");
        for (int c = 0; c < columns; c++) {
            sb.append(" --- |");
        }
        sb.append("\n");

        // Data rows
        for (int r = 0; r < rows; r++) {
            sb.append("|");
            for (int c = 0; c < columns; c++) {
                sb.append(" ").append(faker.lorem().word()).append(" |");
            }
            if (r < rows - 1) {
                sb.append("\n");
            }
        }

        return sb.toString();
    }

    /**
     * Generates a paragraph of Markdown text.
     *
     * @return a paragraph, e.g., "Lorem ipsum dolor sit amet..."
     */
    public String paragraph() {
        return faker.lorem().paragraph();
    }

    /**
     * Generates multiple paragraphs separated by blank lines.
     *
     * @param count number of paragraphs
     * @return paragraphs separated by double newlines
     */
    public String paragraphs(int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(faker.lorem().paragraph());
            if (i < count - 1) {
                sb.append("\n\n");
            }
        }
        return sb.toString();
    }

    /**
     * Generates a complete Markdown document with mixed content.
     *
     * @return a sample Markdown document
     */
    public String document() {
        StringBuilder sb = new StringBuilder();
        sb.append(header(1)).append("\n\n");
        sb.append(paragraph()).append("\n\n");
        sb.append(header(2)).append("\n\n");
        sb.append(unorderedList(3)).append("\n\n");
        sb.append(header(2)).append("\n\n");
        sb.append(paragraph()).append("\n\n");
        sb.append(codeBlock()).append("\n\n");
        sb.append(blockquote());
        return sb.toString();
    }

    private String capitalizeFirst(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        return Character.toUpperCase(text.charAt(0)) + text.substring(1);
    }

    private String capitalizeWords(List<String> words) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < words.size(); i++) {
            sb.append(capitalizeFirst(words.get(i)));
            if (i < words.size() - 1) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }
}
```

## FAKER_REGISTRATION

FIELD: private final Markdown markdown;
CONSTRUCTOR: this.markdown = new Markdown(this);
GETTER: public Markdown markdown() { return markdown; }

## TESTS

```java
package org.thejavaguy.javafaker;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

public class MarkdownTest extends AbstractFakerTest {

    @Test
    void header() {
        String header = faker.markdown().header();
        assertThat(header).matches("#{1,6} .+");
    }

    @Test
    void headerWithLevel() {
        for (int level = 1; level <= 6; level++) {
            String header = faker.markdown().header(level);
            assertThat(header).startsWith("#".repeat(level) + " ");
        }
    }

    @Test
    void headerWithInvalidLevel() {
        assertThatThrownBy(() -> faker.markdown().header(0))
            .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> faker.markdown().header(7))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void emphasis() {
        String emphasis = faker.markdown().emphasis();
        assertThat(emphasis).matches("[_~*]{1,2}\\w+[_~*]{1,2}");
    }

    @Test
    void bold() {
        String bold = faker.markdown().bold();
        assertThat(bold).matches("\\*\\*\\w+\\*\\*");
    }

    @Test
    void italic() {
        String italic = faker.markdown().italic();
        assertThat(italic).matches("\\*\\w+\\*");
    }

    @Test
    void strikethrough() {
        String strike = faker.markdown().strikethrough();
        assertThat(strike).matches("~~\\w+~~");
    }

    @Test
    void inlineCode() {
        String code = faker.markdown().inlineCode();
        assertThat(code).matches("`\\w+`");
    }

    @Test
    void codeBlock() {
        String block = faker.markdown().codeBlock();
        assertThat(block).startsWith("```");
        assertThat(block).endsWith("```");
        assertThat(block).contains("\n");
    }

    @Test
    void codeBlockWithLanguage() {
        String block = faker.markdown().codeBlock("java");
        assertThat(block).startsWith("```java\n");
        assertThat(block).endsWith("```");
    }

    @Test
    void unorderedList() {
        String list = faker.markdown().unorderedList();
        assertThat(list).contains("- ");
        String[] lines = list.split("\n");
        assertThat(lines.length).isBetween(3, 5);
    }

    @Test
    void unorderedListWithCount() {
        String list = faker.markdown().unorderedList(5);
        String[] lines = list.split("\n");
        assertThat(lines).hasSize(5);
        for (String line : lines) {
            assertThat(line).startsWith("- ");
        }
    }

    @Test
    void orderedList() {
        String list = faker.markdown().orderedList();
        assertThat(list).matches("(?s)1\\. .+");
    }

    @Test
    void orderedListWithCount() {
        String list = faker.markdown().orderedList(4);
        String[] lines = list.split("\n");
        assertThat(lines).hasSize(4);
        for (int i = 0; i < lines.length; i++) {
            assertThat(lines[i]).startsWith((i + 1) + ". ");
        }
    }

    @Test
    void taskList() {
        String list = faker.markdown().taskList();
        assertThat(list).matches("(?s).*- \\[[x ]\\] .+.*");
    }

    @Test
    void taskListWithCount() {
        String list = faker.markdown().taskList(3);
        String[] lines = list.split("\n");
        assertThat(lines).hasSize(3);
        for (String line : lines) {
            assertThat(line).matches("- \\[[x ]\\] .+");
        }
    }

    @Test
    void link() {
        String link = faker.markdown().link();
        assertThat(link).matches("\\[.+\\]\\(.+\\)");
    }

    @Test
    void linkWithParams() {
        String link = faker.markdown().link("Click here", "https://example.com");
        assertThat(link).isEqualTo("[Click here](https://example.com)");
    }

    @Test
    void image() {
        String image = faker.markdown().image();
        assertThat(image).matches("!\\[.+\\]\\(.+\\)");
    }

    @Test
    void imageWithParams() {
        String image = faker.markdown().image("Alt text", "https://example.com/img.jpg");
        assertThat(image).isEqualTo("![Alt text](https://example.com/img.jpg)");
    }

    @Test
    void blockquote() {
        String quote = faker.markdown().blockquote();
        assertThat(quote).startsWith("> ");
    }

    @Test
    void blockquoteMultiline() {
        String quote = faker.markdown().blockquote(3);
        String[] lines = quote.split("\n");
        assertThat(lines).hasSize(3);
        for (String line : lines) {
            assertThat(line).startsWith("> ");
        }
    }

    @Test
    void horizontalRule() {
        String rule = faker.markdown().horizontalRule();
        assertThat(rule).matches("---|(\\*{3})|(_{3})");
    }

    @Test
    void table() {
        String table = faker.markdown().table();
        assertThat(table).contains("|");
        assertThat(table).contains("---");
    }

    @Test
    void tableWithDimensions() {
        String table = faker.markdown().table(4, 2);
        String[] lines = table.split("\n");
        // Header + separator + 2 data rows = 4 lines
        assertThat(lines).hasSize(4);
        // Each line should have 5 pipes (for 4 columns)
        for (String line : lines) {
            assertThat(line.chars().filter(c -> c == '|').count()).isEqualTo(5);
        }
    }

    @Test
    void paragraph() {
        String para = faker.markdown().paragraph();
        assertThat(para).isNotBlank();
    }

    @Test
    void paragraphs() {
        String paras = faker.markdown().paragraphs(3);
        String[] parts = paras.split("\n\n");
        assertThat(parts).hasSize(3);
    }

    @Test
    void document() {
        String doc = faker.markdown().document();
        assertThat(doc).isNotBlank();
        assertThat(doc).contains("#"); // Has headers
        assertThat(doc).contains("```"); // Has code block
        assertThat(doc).contains(">"); // Has blockquote
    }
}
```
