package com.example.gateway;

        import io.jsonwebtoken.Claims;
        import io.jsonwebtoken.Jwts;
        import org.springframework.http.HttpStatus;
        import org.springframework.http.server.reactive.ServerHttpRequest;
        import org.springframework.stereotype.Component;
        import org.springframework.web.server.ServerWebExchange;
        import org.springframework.web.server.WebFilter;
        import org.springframework.web.server.WebFilterChain;
        import reactor.core.publisher.Mono;

        import java.nio.charset.StandardCharsets;
        import java.util.List;

        @Component
        public class AuthenticationFilter implements WebFilter {

            private static final String SECRET_KEY = "yoursecretkeyhere12345678901234567890yoursecretkeyhere12345678901234567890yoursecretkeyhere12345678901234567890";

            @Override
            public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
                ServerHttpRequest request = exchange.getRequest();

                // Skip authentication for auth-service routes
                if (request.getURI().getPath().startsWith("/api/auth/")) {
                    return chain.filter(exchange);
                }

                // Get x-authorization header
                List<String> authHeaders = request.getHeaders().get("x-authorization");
                if (authHeaders == null || authHeaders.isEmpty()) {
                    return unauthorized(exchange);
                }

                String token = authHeaders.get(0);
                if (!validateToken(token)) {
                    return unauthorized(exchange);
                }

                return chain.filter(exchange);
            }

            private boolean validateToken(String token) {
                try {
                    Claims claims = Jwts.parser()
                            .setSigningKey(SECRET_KEY.getBytes(StandardCharsets.UTF_8))
                            .parseClaimsJws(token)
                            .getBody();
                    return claims.getSubject() != null;
                } catch (Exception e) {
                    return false;
                }
            }

            private Mono<Void> unauthorized(ServerWebExchange exchange) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
        }