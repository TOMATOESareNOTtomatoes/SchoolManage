//package com.fanqie.gateway.filter;
//
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import org.springframework.web.server.WebFilter;
//import org.springframework.web.server.WebFilterChain;
//import reactor.core.publisher.Mono;
//
//import java.util.Collections;
//import java.util.Optional;
//
//@Component
//public class CorsFilter implements WebFilter {
//   @Override
//    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
//        ServerHttpRequest request = exchange.getRequest();
//        HttpHeaders headers =  exchange.getResponse().getHeaders();
//
//        Optional.ofNullable(request.getHeaders().getOrigin())
//                .ifPresent(origin -> headers.setAccessControlAllowOrigin(origin));
//        headers.add("Access-Control-Allow-Credentials", "true");
//        headers.addAll("Access-Control-Allow-Headers",
//                Collections.singletonList("Access-Control-Allow-Headers,Cookie,Content-Type,Authorization,Access-Control-Allow-Origin"));
//        headers.addAll("Access-Control-Expose-Headers", Collections.singletonList("*"));
//        Optional.ofNullable(request.getHeaders().getAccessControlRequestHeaders())
//                .ifPresent(headers::setAccessControlAllowHeaders);
//        Optional.ofNullable(request.getHeaders().getAccessControlRequestMethod())
//                .ifPresent(method -> headers.setAccessControlAllowMethods(Collections.singletonList(new HttpMethod[]{new HttpMethod(method)})));
//
//        if (HttpMethod.OPTIONS.matches(request.getMethodValue())) {
//            return Mono.empty();
//        }
//        return chain.filter(exchange);
//    }
//}