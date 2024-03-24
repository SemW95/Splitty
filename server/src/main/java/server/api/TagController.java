package server.api;

import commons.Person;
import commons.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import server.service.TagService;

/**
 * Controller for Tag. [CONT -> SERV -> REPO]
 */
@RestController
public class TagController {
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    //TODO add endpoints below

    /**
     * Returns all persons in the database,
     * if no people returns empty list.
     *
     * @return list of persons
     */
    @GetMapping(path = "/tag")
    public List<Tag> getAllTags() {
        return tagService.getAllTag();
    }
}
