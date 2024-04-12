package server.api;

import commons.Colour;
import commons.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
    public Tag getTagById(@PathVariable(name = "id") String id) {
        return tagService.getTagById(id);
    }

    /** Get the name of a certain Tag.
     *
     * @param id The id of the Tag for which the name should be returned
     * @return The name of the specified Tag
     */
    @GetMapping(path = "/tag/{id}/name")
    @ResponseBody
    public String getName(@PathVariable(name = "id") String id) {
        return tagService.getName(id);
    }

    /** Set the name for a Tag.
     *
     * @param id The id of the Tag for which the name should be set
     * @param name The name that should be set for the specified Tag
     */
    @PutMapping(path = "/tag/{id}/name/{name}")
    public void setName(
        @PathVariable(name = "id") String id,
        @PathVariable(name = "name") String name
    ) {
        tagService.setName(id, name);
    }

    /** Get the colour of a certain Tag.
     *
     * @param id The id of the Tag for which the colour should be returned
     * @return The colour of the specified Tag
     */
    @GetMapping(path = "/tag/{id}/colour")
    @ResponseBody
    public Colour getColour(@PathVariable(name = "id") String id) {
        return tagService.getColour(id);
    }

    @PutMapping(path = "/tag/{id}/colour")
    public void setColour(@PathVariable(name = "id") String id, @RequestBody Colour colour) {
        tagService.setColour(id, colour);
    }

    /** Set the colour for a Tag.
     *
     * @param id The id of the Tag for which the colour should be set
     * @param red The red value for the Colour that should be set for the specified Tag
     * @param green The green value for the Colour that should be set for the specified Tag
     * @param blue The blue value for the Colour that should be set for the specified Tag
     */
    @PutMapping(path = "/tag/{id}/colour/{red}/{green}/{blue}")
    public void setColour(
        @PathVariable(name = "id") String id,
        @PathVariable(name = "red") int red,
        @PathVariable(name = "green") int green,
        @PathVariable(name = "blue") int blue
    ) {
        tagService.setColour(id, red, green, blue);
    }

    /** Set the colour for a Tag.
     *
     * @param id The id of the Tag for which the colour should be set
     * @param hexString The hexString value for the Colour that should be set for the specified Tag
     */
    @PutMapping(path = "/tag/{id}/colour/{hex-string}")
    public void setColour(
        @PathVariable(name = "id") String id,
        @PathVariable(name = "hex-string") String hexString
    ) {
        tagService.setColour(id, hexString);
    }

    @DeleteMapping(path = "/tag/{id}")
    public void deleteTag(@PathVariable(name = "id") String id) {
        tagService.deleteTag(id);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Object> handleIllegalStateException(IllegalStateException ex) {
        // Return a ResponseEntity with the NOT_FOUND status
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
