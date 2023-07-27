package group4.organicapplication.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class SimpleAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        authorities.forEach(authority -> {
            // nếu quyền có vai trò user, chuyển đến trang "/" nếu login thành công
            if (authority.getAuthority().equals("ROLE_MEMBER")) {
                try {
                    redirectStrategy.sendRedirect(request, response, "/");
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else if (authority.getAuthority().contains("ROLE_ADMIN")) {
                try {
                    redirectStrategy.sendRedirect(request, response, "/admin");
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
//            } else if (authority.getAuthority().equals("ROLE_SHIPPER")) {
//                try {
//                    redirectStrategy.sendRedirect(request, response, "/shipper");
//                } catch (Exception e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }
              } else {
                throw new IllegalStateException();
            }
        });

    }

}
