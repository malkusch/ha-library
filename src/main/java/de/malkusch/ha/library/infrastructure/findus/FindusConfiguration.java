package de.malkusch.ha.library.infrastructure.findus;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.damnhandy.uri.template.UriTemplate;

import de.malkusch.ha.library.infrastructure.http.HttpClient;
import de.malkusch.ha.library.infrastructure.http.HttpClient.Header;
import de.malkusch.ha.library.model.Findus;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
class FindusConfiguration {

    private final FindusProperties properties;

    @Component
    @ConfigurationProperties("findus")
    @Data
    public static final class FindusProperties {
        private String url;
        private String customer;
        private String cookie;
    }

    @Bean
    Findus findus(HttpClient http) {
        var uriTemplate = UriTemplate.fromTemplate(properties.url);
        var url = uriTemplate.set("customer", properties.customer).expand();
        var cookie = new Header("Cookie", String.format("findus-%s=%s", properties.customer, properties.cookie));
        return new HttpFindus(url, http, cookie);
    }
}
