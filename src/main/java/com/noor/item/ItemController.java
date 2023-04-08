package item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/rec/item/")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping(value = "add")
    public ResponseEntity<String> addItem(){
       return ResponseEntity.ok(itemService.addItem());
    }

}
