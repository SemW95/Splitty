package server.service;

import commons.Tag;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.database.TagRepository;

/**
 * Service for Tag. [CONT -> SERV -> REPO]
 */
@Service
public class TagService {

    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<Tag> getAllTag() {
        return tagRepository.findAll();
    }

    /**
     * Searches Tag on specified name,
     * throws exception if name doesn't exist.
     *
     * @param name that is searched
     * @return Tag with specified name
     */
    public Tag getTagByName(String name) {
        Optional<Tag> optionalTag = tagRepository
            .findTagByName(name);

        if (optionalTag.isEmpty()) {
            throw new IllegalStateException(
                "There is no tag with this name"
            );
        }

        return optionalTag.get();
    }
}
