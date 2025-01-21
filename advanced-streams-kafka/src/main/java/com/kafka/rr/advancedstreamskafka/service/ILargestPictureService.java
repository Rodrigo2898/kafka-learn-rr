package com.kafka.rr.advancedstreamskafka.service;

import com.rr.avromodels.Message;
import com.rr.avromodels.Picture;

import java.util.Optional;

public interface ILargestPictureService {

    Optional<Picture> getLargestPicture(Message message);
}
