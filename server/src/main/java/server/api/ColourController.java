package server.api;

import commons.Colour;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import server.service.ColourService;

/**
 * Controller for Colour. [CONT -> SERV -> REPO]
 */
@RestController
public class ColourController {

    private final ColourService colourService;

    public ColourController(ColourService colourService) {
        this.colourService = colourService;
    }

    @GetMapping(path = "/colour")
    public List<Colour> getAllColour() {
        return colourService.getAllColour();
    }

    @GetMapping(path = "/colour/{id}")
    @ResponseBody
    public Colour getColourById(@PathVariable String id) throws IllegalStateException {
        return colourService.getColourById(id);
    }

    @PostMapping(path = "/colour")
    @ResponseBody
    public String createColour(@RequestBody Colour colour) throws IllegalStateException {
        return colourService.createColour(colour);
    }

    @PostMapping(path = "/colour/rgb/{red}/{green}/{blue}")
    @ResponseBody
    public String createColour(
        @PathVariable int red, @PathVariable int green, @PathVariable int blue)
        throws IllegalStateException {
        return colourService.createColour(red, green, blue);
    }

    @PostMapping(path = "/colour/hex/{hexString}")
    @ResponseBody
    public String createColour(
        @PathVariable String hexString) {
        return colourService.createColour(hexString);
    }

    @PutMapping(path = "/colour")
    public void updateColour(@RequestBody Colour colour) throws IllegalStateException {
        colourService.updateColour(colour);
    }

    @GetMapping(path = "/colour/{id}/red")
    @ResponseBody
    public int getRed(@PathVariable String id) throws IllegalStateException {
        return colourService.getRed(id);
    }

    @GetMapping(path = "/colour/{id}/green")
    @ResponseBody
    public int getGreen(@PathVariable String id) throws IllegalStateException {
        return colourService.getGreen(id);
    }

    @GetMapping(path = "/colour/{id}/blue")
    @ResponseBody
    public int getBlue(@PathVariable String id) throws IllegalStateException {
        return colourService.getBlue(id);
    }

    @PutMapping(path = "/colour/{id}/red/{red}")
    public void setRed(@PathVariable String id, @PathVariable int red)
        throws IllegalStateException {
        colourService.setRed(id, red);
    }

    @PutMapping(path = "/colour/{id}/green/{green}")
    public void setGreen(@PathVariable String id, @PathVariable int green) {
        colourService.setGreen(id, green);
    }

    @PutMapping(path = "/colour/{id}/blue/{blue}")
    public void setBlue(@PathVariable String id, @PathVariable int blue) {
        colourService.setBlue(id, blue);
    }

    @GetMapping(path = "/colour/{id}/hex")
    @ResponseBody
    public String toHexString(@PathVariable String id) {
        return colourService.toHexString(id);
    }
}
