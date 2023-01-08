package io.academy.backend.academy.controller;

import io.academy.backend.academy.entity.Community;
import io.academy.backend.academy.service.CommunityService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/community")
public class CommunityController {
    private final CommunityService service;

    public CommunityController(CommunityService service) {
        this.service = service;
    }


    @GetMapping
    public List<Community> getAll(
            @RequestParam(value = "includeDeleted", defaultValue = "false") boolean includeDeleted,
            @RequestParam(value = "sortType", defaultValue = "NONE") SortType sortType) {

        return this.service.getAll(includeDeleted, sortType);
    }

    @GetMapping(path = "/{id}")
    public Community get(@PathVariable("id") String id) {
        return this.service.getById(id);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteById(@PathVariable("id") String id) {
        this.service.deleteById(id);
    }

    @PutMapping(path = "/{id}")
    public void update(
            @PathVariable("id") String id,
            @RequestBody Community community) {

        this.service.update(id, community);
    }

    @PostMapping
    public void add(@RequestBody Community community) {
        this.service.save(community);
    }

}
