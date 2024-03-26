package server.api;

import commons.Colour;
import commons.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    /** Adds a Tag.
     *
     * @param tag The Tag that should be added
     */
    @PostMapping(path = "/tag")
    public void addTag(@RequestBody Tag tag) {
        tagService.addTag(tag);
    }

    /**
     * Searches Tag on specified id,
     * throws exception if id doesn't exist.
     *
     * @param id that is searched
     * @return Tag with specified id
     */
    @GetMapping(path = "/tag/{id}")
    @ResponseBody
    public Tag getTagById(@PathVariable(name = "id") Long id) {
        return tagService.getTagById(id);
    }

    /** Get the name of a certain Tag.
     *
     * @param id The id of the Tag for which the name should be returned
     * @return The name of the specified Tag
     */
    @GetMapping(path = "/tag/{id}/name")
    @ResponseBody
    public String getName(@PathVariable(name = "id") Long id) {
        return tagService.getName(id);
    }

    /** Get the colour of a certain Tag.
     *
     * @param id The id of the Tag for which the colour should be returned
     * @return The colour of the specified Tag
     */
    @GetMapping(path = "/tag/{id}/colour")
    @ResponseBody
    public Colour getColour(@PathVariable(name = "id") Long id) {
        return tagService.getColour(id);
    }

    /** Set the name for a Tag.
     *
     * @param id The id of the Tag for which the name should be set
     * @param name The name that should be set for the specified Tag
     */
    @PutMapping(path = "/tag/{id}/name/{name}")
    public void setName(
        @PathVariable(name = "id") Long id,
        @PathVariable(name = "name") String name
    ) {
        tagService.setName(id, name);
    }

    @PutMapping(path = "/tag/{id}")
    public void setColour(@PathVariable(name = "id") Long id, @RequestBody Colour colour) {
        tagService.setColour(id, colour);
    }

    /** Set the colour for a Tag.
     *
     * @param id The id of the Tag for which the colour should be set
     * @param red The red value for the Colour that should be set for the specified Tag
     * @param green The green value for the Colour that should be set for the specified Tag
     * @param blue The blue value for the Colour that should be set for the specified Tag
     */
    @PutMapping(path = "/tag/{id}/name/{red}/{green}/{blue}")
    public void setColour(
        @PathVariable(name = "id") Long id,
        @PathVariable(name = "red") int red,
        @PathVariable(name = "green") int green,
        @PathVariable(name = "blue") int blue
    ) {
        tagService.setColour(id, new Colour(red, green, blue));
    }

    /** Set the colour for a Tag.
     *
     * @param id The id of the Tag for which the colour should be set
     * @param hexString The hexString value for the Colour that should be set for the specified Tag
     */
    @PutMapping(path = "/tag/{id}/name/{hex-string}")
    public void setColour(
        @PathVariable(name = "id") Long id,
        @PathVariable(name = "hex-string") String hexString
    ) {
        tagService.setColour(id, new Colour(hexString));
    }

    @DeleteMapping(path = "/tag/{id}")
    public void deleteTag(@PathVariable(name = "id") Long id) {
        tagService.deleteTag(id);
    }

}
