package com.vnco.fusiontech.product.web.rest.request;

import com.vnco.fusiontech.product.entity.Product;
import com.vnco.fusiontech.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProductController {
    @Autowired
    ProductService productService;

    @RequestMapping("/product/detail/{id}")
    public String detail(Model model, @PathVariable("id")Integer id )
    {
        Product item = productService.getProductById(id);
        model.addAttribute("item",item);
        return "product/detail";
    }
}
