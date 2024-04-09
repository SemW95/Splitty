package server.service;

import commons.Colour;
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
     * @param id The id of the Tag that is searched
     * @return Tag with the specified id
     */
    public Tag getTagById(String id) {
        Optional<Tag> optionalTag = tagRepository.findById(id);

        if (optionalTag.isEmpty()) {
            throw new IllegalStateException("There is no tag with this id");
        }

        return optionalTag.get();
    }

    /** Adds a Tag to the database.
     *
     * @param tag The Tag that should be added to the database
     */
    public void addTag(Tag tag) {
        Optional<Tag> optionalTag = tagRepository.findById(tag.getId());

        if (optionalTag.isPresent()) {
            throw new IllegalStateException("There already is a Tag with this id");
        }

        tagRepository.save(tag);
    }

    /** Gets the name of the Tag.
     *
     * @param id The id of the Tag that is searched
     * @return The name of the Tag with the specified id
     */
    public String getName(String id) {
        Tag tag = getTagById(id);
        return tag.getName();
    }

    /** Gets the colour of the Tag.
     *
     * @param id The id of the Tag that is searched
     * @return The colour of the Tag with the specified id
     */
    public Colour getColour(String id) {
        Tag tag = getTagById(id);
        return tag.getColour();
    }

    /** Gets the name of the Tag.
     *
     * @param id The id of the Tag that is searched
     * @param name The name for the Tag with the specified id
     */
    public void setName(String id, String name) {
        Tag tag = getTagById(id);

        tag.setName(name);
        tagRepository.save(tag);
    }

    /** Gets the name of the Tag.
     *
     * @param id The id of the Tag that is searched
     * @param colour The Colour for the Tag with the specified id
     */
    public void setColour(String id, Colour colour) {
        Tag tag = getTagById(id);

        tag.setColour(colour);
        tagRepository.save(tag);
    }

    /** Deletes a Tag from the database.
     *
     * @param id The id of the Tag that should be deleted
     */
    public void deleteTag(String id) {
        Optional<Tag> optionalTag = tagRepository.findById(id);

        if (optionalTag.isEmpty()) {
            throw new IllegalStateException("There is no Tag with this id");
        }

        tagRepository.deleteById(id);
    }
}
