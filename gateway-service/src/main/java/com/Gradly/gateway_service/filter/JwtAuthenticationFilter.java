package com.Gradly.gateway_service.filter;

import com.Gradly.gateway_service.security.RoleValidator;
import com.Gradly.gateway_service.security.RouteValidator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Component
public class JwtAuthenticationFilter implements GlobalFilter {

    private final String SECRET = "gradlySecretK243tehgvduc36guwdeyfbiuefbkiuef7udfhoey";

    @Autowired
    private RouteValidator routeValidator;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();

        if (routeValidator.isSecured.test(path)) {

            String authHeader = exchange.getRequest()
                    .getHeaders()
                    .getFirst("Authorization");

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {

                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            String token = authHeader.substring(7);

            try {

                Claims claims = Jwts.parser()
                        .setSigningKey(SECRET)
                        .parseClaimsJws(token)
                        .getBody();

                String userId = claims.getSubject();
                String role = claims.get("role", String.class);

                // ROLE AUTHORIZATION
                for (Map.Entry<String, List<String>> entry :
                        RoleValidator.rolePermissions.entrySet()) {

                    if (path.startsWith(entry.getKey())) {

                        if (!entry.getValue().contains(role)) {

                            exchange.getResponse()
                                    .setStatusCode(HttpStatus.FORBIDDEN);

                            return exchange.getResponse().setComplete();
                        }
                    }
                }

                ServerWebExchange modifiedExchange = exchange.mutate()
                        .request(builder -> builder
                                .header("X-User-Id", userId)
                                .header("X-User-Role", role))
                        .build();

                return chain.filter(modifiedExchange);

            } catch (Exception e) {

                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
        }

        return chain.filter(exchange);
    }
}