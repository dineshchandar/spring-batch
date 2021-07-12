package com.spring.batch.util;

import com.spring.batch.model.Birthday;
import com.spring.batch.model.EmailRequest;
import com.spring.batch.repository.BirthdayRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.web.reactive.function.client.WebClient;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class CustomItemProcessor implements ItemProcessor<Birthday, Birthday> {

    String baseUrl;
    String uri;
    BirthdayRepository birthdayRepository;

    public CustomItemProcessor(
            String baseUrl, String uri, BirthdayRepository birthdayRepository) {

        this.baseUrl = baseUrl;
        this.uri = uri;
        this.birthdayRepository = birthdayRepository;

    }

    @Override
    public Birthday process(Birthday birthday) {

        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setToEmailId(birthday.getEmailId());
        emailRequest.setEmailSubject("Happy Birthday " + birthday.getName());
        emailRequest.setEmailBody("Dear, " + birthday.getName()+",<br/><br/>"+"Many Many Happy Returns of the Day! Hope you have a wonderful birthday!");

//        HttpClient httpClient=
//                HttpClient.create(ConnectionProvider.builder("custom connection")
//                                                    .maxConnections(600)
//                                                    .pendingAcquireTimeout(Duration.ofMinutes(5))
//                                                    .build());
//
//        ClientHttpConnector clientHttpConnector =
//                new ReactorClientHttpConnector(httpClient);

        WebClient webClient = WebClient.builder().baseUrl(baseUrl).build();

        try {
            webClient.post().uri(uri)
                     .header("User-Agent", "Apache-HttpClient/4.5.5 (Java/13.0.2)")
                     .bodyValue(emailRequest)
                     .exchange()
                     .timeout(Duration.ofMinutes(5))
                     .flatMap(clientResponse -> clientResponse.releaseBody())
                     .doOnSuccess(body -> {
                         birthday.setWishesStatus("WISHES SENT");
                         birthdayRepository.save(birthday);
                     })
                     .doOnError(ex -> errorFallback(birthday, ex))
                     .retry(5)
                     .subscribe();

        }
        catch (Exception e) {
            System.out.println("Consent ID in error " + birthday.getEmailId());
            System.out.println("Exception encountered in Webclient : " + e.getMessage());
        }
        return birthday;
    }

    private void errorFallback(Birthday birthday, Throwable ex) {

        birthday.setWishesStatus("ERROR");
        birthdayRepository.save(birthday);

        System.out.println("Failed for Email ID " + birthday.getEmailId() + " due to " + ex.getCause() + " " + ex.getMessage());

    }

}