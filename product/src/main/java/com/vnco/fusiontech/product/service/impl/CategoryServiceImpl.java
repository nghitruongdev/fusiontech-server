//package com.vnco.fusiontech.product.service.impl;
//
//import com.vnco.fusiontech.common.exception.RecordNotFoundException;
//import com.vnco.fusiontech.product.entity.Category;
//import com.vnco.fusiontech.product.repository.CategoryRepository;
//import com.vnco.fusiontech.product.repository.ProductRepository;
//import com.vnco.fusiontech.product.service.CategoryServiceV1;
//import lombok.AllArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//
//@Service
//@AllArgsConstructor
//public abstract class CategoryServiceImpl implements CategoryServiceV1 {
//
//    @Autowired
//    CategoryRepository categoryRepository;
//    ProductRepository productRepository;
//
//    @Override
//    public Category save(Category category) {
//        return categoryRepository.save(category);
//    }
//
//
//    /* Optional là gì?
//            - Optional là 1 lớp được dùng để giải quyết vấn đề về gtri null
//            - Optional giải quyết vấn đề null bằng cách bọc giá trị của biến trong 1 đối tượng Optional
//                + Nếu gtri của biến là null -> Optional sẽ không có giá trị.
//                + Nếu gtri != null -> Optinal sẽ chứa đối tượng đó.
//            - Optinal sẽ ktra gtri có tồn tại hay không trước khi truy xuất đến nó. Tránh lỗi NullPointerException -> dẽ đọc hơn
//     * */
//    public Category update(Category category, Integer id) {
//        /*
//         *Trong phương thức update:
//         * - Optional được dùng để ktra category có tồn tại hay không:
//         *      + Nếu tồn tại: cập nhật thông tin và lưu vào DB.
//         *      + Nếu ko tồn tại: trả về NotFoundException.
//         * */
//        //!todo: throw service if not found
//        Optional<Category> existingCategory = categoryRepository.findById(id);
//        if (existingCategory.isPresent()) {
//            Category updateCategory = existingCategory.get();
//            updateCategory.setName(category.getName());
//            return categoryRepository.save(updateCategory);
//        } else {
//            throw new RecordNotFoundException();
//        }
//    }
//
//    @Override
//    public void delete(int id) {
//        //!todo: check if category_id is valid
//        //!todo: check if category has any products before deleting
//        // todo: uncomment delete khi xong entity product
////        categoryRepository.deleteById(id);
//        /*
//         * Phương thức sử dụng categoryRepository.findById(id) để tìm kiếm Category với id tương ứng
//         *  +  Nếu Category tồn tại, phương thức tiếp tục kiểm tra xem có sản phẩm nào thuộc về Category này không bằng cách sử dụng productRepository.findByCategoryId(id)
//         *  +  Nếu danh sách sản phẩm không rỗng, phương thức sẽ ném ra một ngoại lệ ConflictException "Cannot delete category with products".
//         *  +  Nếu danh sách sản phẩm rỗng, phương thức sử dụng categoryRepository.deleteById(id) để xóa Category với id tương ứng.
//         * */
//
//
////        Optional<Category> category = categoryRepository.findById(id);
////        if (category.isPresent()) {
////            List<Product> products = productRepository.findByCategoryId(id);
////            if (!products.isEmpty()) {
////                throw new ConflictException("Cannot delete category with products");
////            }
////            categoryRepository.deleteById(id);
////        } else {
////            throw new NotFoundException("Category not found with id: " + id);
////        }
//
//    }
//
//    @Override
//    public Category findById(int id) {
//        /*
//         *Trong phương thức findById:
//         * - Optional được dùng để ktra category có tồn tại hay không:
//         *      + Nếu tồn tại: cập nhật thông tin và lưu vào DB.
//         *      + Nếu ko tồn tại: trả về NotFoundException.
//         * */
//
//        //!todo: throw Exception if not found instead of null
//        Optional<Category> category = categoryRepository.findById(id);
//        if (category.isPresent()) {
//            System.out.println(category);
//            return category.get();
//        } else {
//            throw new RecordNotFoundException();
//        }
//    }
//
//    @Override
//    public List<Category> findAll() {
//        return categoryRepository.findAll();
//    }
//}
