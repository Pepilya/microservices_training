package com.microservices.training.resource.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ResourceService {

    Integer create(MultipartFile file) throws IOException;

    byte[] get(Integer id);

    List<Integer> delete(List<Integer> ids);

}
