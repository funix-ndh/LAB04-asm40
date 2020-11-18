package com.funix.lab04.asm40.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.funix.lab04.asm40.constraint.ValidImage;
import com.funix.lab04.asm40.model.Product;
import com.funix.lab04.asm40.repository.ProductRepositoryInterface;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.val;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepositoryInterface productRepositoryInterface;

    @GetMapping
    public ModelAndView index() {
        val productList = productRepositoryInterface.findAllByOrderByCreatedAtDesc();
        return new ModelAndView("product-list", "productList", productList);
    }

    @GetMapping("/create-product")
    public ModelAndView createProduct() {
        return new ModelAndView("create-product", "createProductRequest", new CreateProductRequest());
    }

    @PostMapping("/create-product")
    public String createProduct(
            @Valid final CreateProductRequest createProductRequest,
            final BindingResult bindingResult
    ) throws IOException {
        if (bindingResult.hasErrors()) {
            return "create-product";
        }
        val filePath = String.format("%s-%s", UUID.randomUUID(),
                                     createProductRequest.getProductImage().getOriginalFilename());
        // write file
        val filepath = Paths.get(new ClassPathResource("static/images").getFile().getAbsolutePath(), filePath);
        try (val os = Files.newOutputStream(filepath)) {
            os.write(createProductRequest.getProductImage().getBytes());
        }
        productRepositoryInterface.save(Product.builder()
                                               .name(createProductRequest.getProductName())
                                               .shortDescription(createProductRequest.getShortDescription())
                                               .detailDescription(createProductRequest.getDetailDescription())
                                               .price(createProductRequest.getPrice())
                                               .specialPrice(createProductRequest.getSpecialPrice())
                                               .imgUrl(filePath)
                                               .createdAt(LocalDateTime.now())
                                               .build());
        return "redirect:/";
    }

    @Data
    static class CreateProductRequest {
        @NotBlank
        @Size(max = 100)
        private String productName;

        @NotBlank
        @Size(max = 200)
        private String shortDescription;

        @NotBlank
        private String detailDescription;

        @NotNull
        @Min(1)
        private Double price;

        @Min(1)
        private Double specialPrice;

        @NotNull
        @ValidImage
        private MultipartFile productImage;
    }
}
