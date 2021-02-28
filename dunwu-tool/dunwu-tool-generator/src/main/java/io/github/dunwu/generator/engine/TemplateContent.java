package io.github.dunwu.generator.engine;

/**
 * 模板内容
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-30
 */
public class TemplateContent {

    private final String name;
    private final String content;

    public TemplateContent(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

}
