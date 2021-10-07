package io.github.dunwu.module.security.entity.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.dunwu.module.cas.entity.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * 用户信息实体
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-09-25
 */
@Getter
@AllArgsConstructor
public class UserVo implements UserDetails {

    private final UserDto user;

    private final Collection<Long> dataScopes;

    @JsonIgnore
    private final List<GrantedAuthority> authorities;

    @Override
    @JsonIgnore
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return user.getUsername();
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return !user.getDisabled();
    }

}
