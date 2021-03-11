package pl.md.produkty;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ProductController {

    private ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @ResponseBody
    @GetMapping("/lista")
    public String list(@RequestParam(value = "kategoria", required = false) ProductCategory category) {

        List<Product> products;

        if(category != null) {
            products = productRepository.findByCategory(category);
        } else {
            products = productRepository.findAll();
        }

        String result = "";

        for (Product product : products) {
            result += product.getName() + " " + product.getCategory() + " " + product.getPrice() + "<br/>";
        }

        return result;
    }

    @PostMapping("/dodaj")
    public String add(@RequestParam String name,
                      @RequestParam double price,
                      @RequestParam ProductCategory category) {

        Product product = new Product(name, price, category);
        productRepository.add(product);

        return "redirect:/lista";
    }

}
