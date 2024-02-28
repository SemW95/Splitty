package server;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("checkstyle:MissingJavadocType")
@Controller
@RequestMapping("/")
public class SomeController {

    private CounterService counter;
    public SomeController(CounterService counter){
        this.counter = counter;
    }

    @GetMapping("/name/{name}")
    @ResponseBody
    public String index(@PathVariable("name") String name, @RequestParam("title") String title) {
        var sb = new StringBuilder("Hello ");
        if(title != null){
            sb.append(title).append(' ');
        }
        sb.append(name);
        sb.append('!');
        sb.append(" You are visitor #").append(counter.getAndIncrease());
        return sb.toString();
    }
}