package io.github.dunwu.module.cas.entity.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-16
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class VueElementMenuVo implements Serializable {

    private String name;

    private String path;

    private Boolean hidden;

    private String redirect;

    private String component;

    private Boolean alwaysShow;

    private MenuMetaVo meta;

    private String label;

    private boolean hasChildren;

    private List<VueElementMenuVo> children;

    @Data
    @AllArgsConstructor
    public static class MenuMetaVo implements Serializable {

        private String name;
        private String icon;
        private Boolean noCache;

    }

}
