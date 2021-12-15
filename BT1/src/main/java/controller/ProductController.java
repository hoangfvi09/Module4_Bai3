package controller;

import model.Category;
import model.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
import service.CategoryService;
import service.ProductService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController {
    ProductService productService = new ProductService();
    CategoryService categoryService = new CategoryService();
    RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

    @GetMapping("/list")
    public String showList(Model model) {
        List<Product> productList = productService.findAll();
        List<Category> categoryList = categoryService.findByProductList(productList);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("productList", productList);

        return "product/list";
    }

    @GetMapping("/create")
    public String showCreateForm() {
        return "product/create";
    }
    @GetMapping("")
    public String index() {
        return "index";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(Model model, @PathVariable int id) {
        model.addAttribute("product", productService.findById(id));
        return "product/edit";
    }

    @GetMapping("/find")
    public String find(Model model, @RequestParam String name) {
        List<Product> productList = new ArrayList<>();
        if (name.equals("")) {
            productList = productService.findAll();
        } else {
            productList = productService.findByName(name);
        }
        List<Category> categoryList = categoryService.findByProductList(productList);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("productList", productList);
        return "product/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        productService.delete(id);
        return "redirect:/list";
    }

    @PostMapping("/create")
    public String create(Product product) {
//        Product product = new Product(name, price, quantity, color, description, categoryId);
        productService.save(product);
        return "redirect:/list";
    }

    @PostMapping("/edit")
    public String edit(Product product, @RequestParam int id) {
//        Product product = new Product(id,name, price, quantity, color, description, categoryId);
        productService.update(id,product);
        return "redirect:/list";
        // response.sendRedirect("/list");
//        redirectAttributes.addFlashAttribute();
    }


}
