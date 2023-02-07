package com.microservices.training.resource.web;

import com.microservices.training.resource.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/resources")
public class ResourceController {

    private final ResourceService service;

    @PostMapping
    public ResourceResp createResource(@RequestParam("file") MultipartFile file) throws IOException {
        Integer id = service.create(file);
        return new ResourceResp(id);

    }

    @GetMapping("{id}")
    public byte [] getResource(@PathVariable Integer id) {
        return service.get(id);
    }

    @DeleteMapping
    public List<Integer> deleteResources(@RequestParam("resources") String idsStr) {
        List<Integer> idList = Arrays.stream(idsStr.split(","))
                .map(Integer::valueOf)
                .collect(Collectors.toList());
        return service.delete(idList);
    }

}
