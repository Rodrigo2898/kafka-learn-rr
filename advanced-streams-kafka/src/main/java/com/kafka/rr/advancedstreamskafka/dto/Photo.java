package com.kafka.rr.advancedstreamskafka.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kafka.rr.advancedstreamskafka.service.impl.LargestPictureServiceImpl;

import java.io.Serializable;

public record Photo(@JsonProperty(LargestPictureServiceImpl.IMG_SRC) String imgSrc) {
}
