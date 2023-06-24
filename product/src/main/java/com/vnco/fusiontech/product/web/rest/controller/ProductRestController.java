package com.vnco.fusiontech.product.web.rest.controller;

import com.vnco.fusiontech.product.entity.Product;
import com.vnco.fusiontech.product.repository.ProductRepository;
import com.vnco.fusiontech.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin("*")
@RepositoryRestController
@RequiredArgsConstructor
public class ProductRestController {
    
    private final ProductService    productService;
    private final ProductRepository productRepository;
    // lay tat ca san pham

    // lay san pham theo id
//    @GetMapping("/{id}")
//    public Product getProductById(@PathVariable int id) {
//        return productService.getProductById(id);
//    }

    // them moi san pham
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }
    
    // cap nhat san pham theo id
    @PutMapping ("/{id}")
    public Product updateProduct(@PathVariable ("id") Long id, @RequestBody Product product) {
        return productService.updateProduct(product);
    }
    
    // xoa san pham
    @DeleteMapping ("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
    
    @GetMapping ("/products/favoritesByUser")
    public ResponseEntity<?> getFavorites(@RequestParam ("uid") UUID uid) {
        return ResponseEntity.ok(productRepository.findAllByFavorites_Id(uid));
    }
    
    @PostMapping ("/products/{id}/favorites")
    @ResponseStatus (HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> addFavorite(@PathVariable ("id") Long productId, @RequestParam ("uid") UUID uid) {
        productService.addUserFavoriteProduct(productId, uid);
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping ("/products/{id}/favorites")
    @ResponseStatus (HttpStatus.NO_CONTENT)
    public ResponseEntity<?> removeFavorite(@PathVariable ("id") Long productId, @RequestParam ("uid") UUID uid) {
        productService.removeUserFavoriteProduct(productId, uid);
        return ResponseEntity.ok().build();
    }
    
    
    // tim kiem san pham
    //    @GetMapping("/search")
    //    public List<Product> searchProduct(@RequestParam("keyword")String keyword){
    //        List<Product> products = productService.searchProduct(keyword);
    //        return products;
    //    }
    
    
    //    @PostMapping("/products/{productId}/images")
    //    public ResponseEntity<String> uploadProductImages(@PathVariable Long productId,
    //                                                      @RequestParam("images") List<MultipartFile> images) {
    //        // Xử lý lưu trữ các hình ảnh lên Firebase Storage
    //        for (MultipartFile image : images) {
    //            // Upload image lên Firebase Storage và nhận URL của nó
    //            String imageUrl = uploadImageToFirebase(image);
    //
    //            // Lưu URL của hình ảnh vào cơ sở dữ liệu hoặc xử lý khác theo nhu cầu của bạn
    //            // Ví dụ:
    //            ProductImage productImage = new ProductImage();
    //            productImage.setProductId(productId);
    //            productImage.setImageUrl(imageUrl);
    //            productImageRepository.save(productImage);
    //        }
    //
    //        // Trả về thông báo thành công
    //        return ResponseEntity.ok("Images uploaded successfully.");
//    }
//
//    private String uploadImageToFirebase(MultipartFile image) {
//        try {
//            // Tạo tên ngẫu nhiên cho hình ảnh để tránh xung đột tên file
//            String fileName = UUID.randomUUID().toString();
//
//            // Lấy đường dẫn của hình ảnh trong Firebase Storage
//            String firebasePath = "products/" + fileName;
//
//            // Tạo tệp tin tạm trong hệ thống tệp tin
//            File tempFile = File.createTempFile(fileName, ".tmp");
//
//            // Ghi dữ liệu hình ảnh từ MultipartFile vào tệp tin tạm
//            image.transferTo(tempFile);
//
//            // Tạo đối tượng StorageReference để tham chiếu đến Firebase Storage
//            StorageReference storageRef = storage.getReference(firebasePath);
//
//            // Upload hình ảnh lên Firebase Storage
//            UploadTask uploadTask = storageRef.putFile(Uri.fromFile(tempFile));
//
//            // Chờ cho đến khi quá trình upload hoàn thành
//            Tasks.await(uploadTask);
//
//            // Lấy URL của hình ảnh sau khi upload thành công
//            String imageUrl = storageRef.getDownloadUrl().getResult().toString();
//
//            // Xóa tệp tin tạm
//            tempFile.delete();
//
//            return imageUrl;
//        } catch (Exception e) {
//            // Xử lý lỗi nếu có
//            e.printStackTrace();
//            return null;
//        }
//    }





}
