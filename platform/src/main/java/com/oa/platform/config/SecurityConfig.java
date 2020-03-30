package com.oa.platform.config;


import com.oa.platform.web.handler.PlatformWebAuthenticationFailHandler;
import com.oa.platform.web.handler.PlatformWebAuthenticationSuccessHandler;
import com.oa.platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 安全配置
 * @author Feng
 * @date 2018/10/15
 */
@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PlatformWebAuthenticationSuccessHandler successHandler;

    @Autowired
    private PlatformWebAuthenticationFailHandler failHandler;

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 设置不拦截规则
    //    web.ignoring().antMatchers("/static/**", "/**/*.jsp");
        web.ignoring().antMatchers("/static/**","/css/**","/img/**","/images/**","/js/**","/fonts/**",
                "/test/**", "/tmp/**", "/api/file/**", "/api/socket/**", "/api/verify/**", "/api/news/**",
                "/ueditor/**");
    }

    // @formatter:off
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/css/**","/images/**","/js/**","/fonts/**", "/index").permitAll()
                .antMatchers("/user/**").hasRole("USER")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .and()
                //登录成功后跳转到 /admin/main ,也可以使用defaultSuccessUrl 或 .successForwardUrl("/admin/main")；
                // 如果不配置登录成功后跳转路径，
                // 那么登录成功后默认就跳转到 orgiin url ， 也就是被跳转至loginPage 前我们尝试访问的那个 url。
                //.formLogin().loginPage("/login").successHandler(successHandler).failureHandler(failHandler).failureUrl("/login-error")
                .formLogin().loginPage("/login").successHandler(successHandler).successForwardUrl("/admin/main").failureHandler(failHandler).failureUrl("/login-error")
                .and()
                .cors()
                .and()
                .exceptionHandling().accessDeniedPage("/401");
        // 自定义注销
//        http.logout().logoutSuccessUrl("/").clearAuthentication(true).invalidateHttpSession(true);
        http.logout().logoutSuccessUrl("/login").clearAuthentication(true).invalidateHttpSession(true);

        //session管理 只允许一个用户登录,如果同一个账户两次登录,那么第一个账户将被踢下线,跳转到登录页面
        http.sessionManagement().sessionFixation().changeSessionId()
                .maximumSessions(1).expiredUrl("/login");

        // RemeberMe
        http.rememberMe().key("webmvc#FD637E6D9C0F1A5A67082AF56CE32485");
        //http.csrf().disable();

        //允许 iframe
        http.headers().frameOptions().sameOrigin();

        //登陆页面、'/api/*'不做csrf校验
        http.csrf().ignoringAntMatchers("/login","/api/**");

    }

    @Autowired
    private UserService userService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public SessionRegistry sessionRegistry(){
        return new SessionRegistryImpl();
    }
}
