package de.malkusch.ha.library.infrastructure.calendar;

import static com.google.api.services.calendar.CalendarScopes.CALENDAR;
import static com.google.api.services.calendar.CalendarScopes.CALENDAR_EVENTS;
import static java.util.Arrays.asList;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;

import de.malkusch.ha.library.model.Calendar;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
class CalendarConfiguration {

    private final Properties properties;

    @Component
    @ConfigurationProperties("google")
    @Data
    public static final class Properties {
        private String clientId;
        private String clientSecret;
        private String authUri;
        private String tokenUri;
        private String calendarName;
        private String applicationName;
        private String location;
    }

    @Bean
    Calendar calendar() throws GeneralSecurityException, IOException {
        var json = GsonFactory.getDefaultInstance();
        var http = GoogleNetHttpTransport.newTrustedTransport();

        var details = new GoogleClientSecrets.Details();
        details.setClientId(properties.clientId);
        details.setClientSecret(properties.clientSecret);
        details.setAuthUri(properties.authUri);
        details.setTokenUri(properties.tokenUri);

        var secrets = new GoogleClientSecrets();
        secrets.setWeb(details);

        var scopes = asList(CALENDAR_EVENTS, CALENDAR);
        var flow = new GoogleAuthorizationCodeFlow.Builder(http, json, secrets, scopes)
                .setDataStoreFactory(new FileDataStoreFactory(new File("tokens"))).setAccessType("offline").build();

        var receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        var credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");

        var api = new com.google.api.services.calendar.Calendar.Builder(http, json, credential) //
                .setApplicationName(properties.applicationName) //
                .build();

        var list = api.calendarList().list().execute().getItems();
        var calendarId = list.stream().filter(it -> it.getSummary().equals(properties.calendarName)).findAny()
                .orElseThrow().getId();

        return new GoogleCalendar(api, calendarId, properties.location);
    }
}
