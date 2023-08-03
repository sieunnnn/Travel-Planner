package travelplanner.project.demo.global.security.jwt;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import travelplanner.project.demo.global.util.TokenUtil;
import travelplanner.project.demo.global.util.CookieUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Cookie;

import java.util.ArrayList;

@Tag(name = "Jwt Token", description = "토큰 재발행 API")
@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class JwtController {

    private final TokenUtil tokenUtil;
    private final CookieUtil cookieUtil;

    @Operation(summary = "accessToken 재발급")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "accessToken 재발급 성공"),
            @ApiResponse(responseCode = "403", description = "재발급에 문제가 생긴 경우")
    })
    @GetMapping("/token")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {

        Cookie refreshTokenCookie = cookieUtil.getCookie(request, "refreshToken");
        if (refreshTokenCookie == null) {
            return ResponseEntity.badRequest().body("Refresh token not found.");
        }

        String refreshToken = refreshTokenCookie.getValue();

        // 어세스 토큰 재발급
        String newAccessToken;
        try {
            newAccessToken = tokenUtil.refreshAccessToken(refreshToken);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to generate new access token.");
        }

        // 헤더에 추가
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Authorization", newAccessToken);

        String principal = tokenUtil.getEmail(newAccessToken);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(principal, newAccessToken, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

       log.info("SecurityContextHolder: " + principal + " newAccessToken: " + newAccessToken);

        return ResponseEntity.ok().headers(responseHeaders).build();
    }
}
