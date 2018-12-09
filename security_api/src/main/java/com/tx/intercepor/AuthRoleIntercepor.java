package com.tx.intercepor;

import com.tx.App;
import com.tx.annotation.AuthRole;
import com.tx.annotation.RoleType;
import com.tx.dao.AdminMapper;
import com.tx.pojo.entity.Admin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author TanXin
 * 鉴权
 */
@Configuration
public class AuthRoleIntercepor extends WebMvcConfigurationSupport {

    static Logger log = LoggerFactory.getLogger(AuthRoleIntercepor.class);

    @Autowired
    private AdminMapper adminMapper;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptorAdapter() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
                    throws Exception {
                // TODO 不加Auth注解不验证 open等效不验证
                // TODO 单工程项目，后台接口 app接口 前端网站接口 在一起时比较好用
                // TODO 用户信息带入根据实际情况来则可，本demo直接带的id
                // TODO 仅供参考
                if (!(handler instanceof HandlerMethod)) {
                    log.debug("Expected handler class " + HandlerMethod.class.getSimpleName() + " but was " + handler.getClass());
                    return true;
                }
                HandlerMethod hm = (HandlerMethod) handler;
                AuthRole auth = hm.getMethodAnnotation(AuthRole.class);
                if(auth.role().equals(RoleType.OPEN)){
                    return true;
                }

                String strId = request.getParameter("adminId");
                if (StringUtils.isEmpty(strId)) {
                    // 需要验证权限的接口没有带上角色关键信息
                    // 一般可能在session，或者是个token
                    // 此次应该抛出异常，做好全局异常处理，懒没做
                    return false;
                }

                int roleIdx = auth.role().ordinal();
                Admin admin = adminMapper.selectById(Integer.valueOf(strId));
                String role = admin.getRole();
                RoleType adminRole = RoleType.strGet(role);
                int admIdx = adminRole.ordinal();

                String uri = request.getRequestURI();
                log.debug(uri);
                // 角色权限
                List<String> list = App.roleUrlMap.get(role);
                // 验证角色权限
                boolean b = list.contains("*");
                b = (b ? b : list.contains(uri));
                if (!b) {
                    return false;
                }
                // 用户单独权限本次不加入

                // 角色等级验证
                log.debug("admIdx :" + admIdx + "\t roleIdx : " + roleIdx );
                switch (auth.role()) {
                    case ADMIN:
                        // 是不是角色为AMIND才可以访问
                        if (admIdx <= roleIdx) {
                            return true;
                        }
                        return false;
                    case FINANCE:
                        // 取枚举的index
                        if (admIdx <= roleIdx) {
                            return true;
                        }
                        return false;
                    case ALL:
                        // 所有角色都可以，只要有访问权限
                        return true;
                    default:
                        return false;
                }
            }
        }).addPathPatterns("/**");
    }


}
