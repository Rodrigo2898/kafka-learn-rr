package com.kafka.rr.advancedstreamskafka.service.impl;

import com.kafka.rr.advancedstreamskafka.dto.Photo;
import com.kafka.rr.advancedstreamskafka.dto.Photos;
import com.kafka.rr.advancedstreamskafka.service.ILargestPictureService;
import com.rr.avromodels.Message;
import com.rr.avromodels.Picture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.codec.CodecProperties;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Optional;

@Service
public class LargestPictureServiceImpl implements ILargestPictureService {

    private final Logger logger = LoggerFactory.getLogger(LargestPictureServiceImpl.class);

    public static final String SOL = "sol";
    public static final String API_KEY = "api_key";
    public static final String IMG_SRC = "img_src";
    private final CodecProperties codecProperties;

    @Value("${nasa.client.key}")
    private String nasaKey;

    @Value("${nasa.client.url}")
    private String nasaUrl;

    public LargestPictureServiceImpl(CodecProperties codecProperties) {
        this.codecProperties = codecProperties;
    }

    @Override
    public Optional<Picture> getLargestPicture(Message message) {
        return WebClient.builder()
                .codecs(codecConfigurer -> codecConfigurer.defaultCodecs().maxInMemorySize(5 * 1024 * 1024))
                .baseUrl(buildUrl(message.getSol()))
                .build()
                .get()
                .exchangeToMono(resp -> resp.bodyToMono(Photos.class))
                .map(Photos::photos)
                .flatMapMany(Flux::fromIterable)
                .map(Photo::imgSrc)
                .flatMap(pictureUrl -> getPicture(message, pictureUrl))
                .reduce((p1, p2) -> p1.getSize() > p2.getSize() ? p1 : p2)
                .doOnNext(picture -> logger.info("The picture is retrieved from NASA: {}", picture))
                .blockOptional(Duration.ofSeconds(30));
    }

    private Mono<Picture> getPicture(Message message, String url) {
        return WebClient.create(url)
                .head()
                .exchangeToMono(ClientResponse::toBodilessEntity)
                .map(HttpEntity::getHeaders)
                .map(httpHeaders -> httpHeaders.getLocation().toString())
                .flatMap(redirectUrl -> WebClient.create(redirectUrl)
                        .head()
                        .exchangeToMono(ClientResponse::toBodilessEntity)
                        .map(respEntity -> respEntity.getHeaders().getContentLength())
                        .map(length -> new Picture(message.getSol(), message.getFullName(),
                                url, length, message.getIpAddress())));
    }

    private String buildUrl(int sol) {
        return UriComponentsBuilder.fromUriString(nasaUrl)
                .queryParam(SOL, sol)
                .queryParam(API_KEY, nasaKey)
                .build().toString();
    }
}
