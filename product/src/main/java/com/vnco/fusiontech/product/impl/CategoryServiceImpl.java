package com.vnco.fusiontech.product.impl;

<<<<<<< HEAD
import com.vnco.fusiontech.common.exception.RecordNotFoundException;
import com.vnco.fusiontech.product.entity.Category;
import com.vnco.fusiontech.product.repository.CategoryRepository;
import com.vnco.fusiontech.product.repository.ProductRepository;
import com.vnco.fusiontech.product.service.CategoryService;
import lombok.AllArgsConstructor;
=======
import com.vnco.fusiontech.product.entity.Category;
import com.vnco.fusiontech.product.repository.CategoryRepository;
import com.vnco.fusiontech.product.service.CategoryService;
>>>>>>> 81600d8 (rebase to dev)
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
<<<<<<< HEAD
import java.util.Optional;


@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    //!todo: remove @Autowired
    //!todo: convert to private final CategoryRepository ....
    //!todo: add annotation @RequiredArgsConstructor or @AllArgsConstructor
    @Autowired
    CategoryRepository categoryRepository;
    ProductRepository productRepository;
=======

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
>>>>>>> 81600d8 (rebase to dev)

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

<<<<<<< HEAD

    /* Optional là gì?
            - Optional là 1 lớp được dùng để giải quyết vấn đề về gtri null
            - Optional giải quyết vấn đề null bằng cách bọc giá trị của biến trong 1 đối tượng Optional
                + Nếu gtri của biến là null -> Optional sẽ không có giá trị.
                + Nếu gtri != null -> Optinal sẽ chứa đối tượng đó.
            - Optinal sẽ ktra gtri có tồn tại hay không trước khi truy xuất đến nó. Tránh lỗi NullPointerException -> dẽ đọc hơn
     * */
    public Category update(Category category, Integer id) {
        /*
         *Trong phương thức update:
         * - Optional được dùng để ktra category có tồn tại hay không:
         *      + Nếu tồn tại: cập nhật thông tin và lưu vào DB.
         *      + Nếu ko tồn tại: trả về NotFoundException.
         * */
        //!todo: throw service if not found
        Optional<Category> existingCategory = categoryRepository.findById(id);
        if (existingCategory.isPresent()) {
            Category updateCategory = existingCategory.get();
            updateCategory.setName(category.getName());
            return categoryRepository.save(updateCategory);
        } else {
            throw new RecordNotFoundException();
        }
=======
    public Category update(Category category, Integer id) {
        /*
        - tìm category dựa vào id -> nếu tìm thấy sẽ lưu vào existingCategory, nếu không thì existingCategory sẽ null
        - ktra existingCategory khác null hay không
            + Khác: category_id đã tồn tại trong DB -> lấy thông tin của category_id này bằng setName
            + Null: ko tìm thấy category trong DB và trả về null
        * */
        Category existingCategory = categoryRepository.findById(id).orElse(null);
        if (existingCategory != null) {
            existingCategory.setName(category.getName());
            return categoryRepository.save(existingCategory);
        }
        return null;
>>>>>>> 81600d8 (rebase to dev)
    }

    @Override
    public void delete(int id) {
<<<<<<< HEAD
        //!todo: check if category_id is valid
        //!todo: check if category has any products before deleting
        // todo: uncomment delete khi xong entity product
//        categoryRepository.deleteById(id);
        /*
         * Phương thức sử dụng categoryRepository.findById(id) để tìm kiếm Category với id tương ứng
         *  +  Nếu Category tồn tại, phương thức tiếp tục kiểm tra xem có sản phẩm nào thuộc về Category này không bằng cách sử dụng productRepository.findByCategoryId(id)
         *  +  Nếu danh sách sản phẩm không rỗng, phương thức sẽ ném ra một ngoại lệ ConflictException "Cannot delete category with products".
         *  +  Nếu danh sách sản phẩm rỗng, phương thức sử dụng categoryRepository.deleteById(id) để xóa Category với id tương ứng.
         * */


//        Optional<Category> category = categoryRepository.findById(id);
//        if (category.isPresent()) {
//            List<Product> products = productRepository.findByCategoryId(id);
//            if (!products.isEmpty()) {
//                throw new ConflictException("Cannot delete category with products");
//            }
//            categoryRepository.deleteById(id);
//        } else {
//            throw new NotFoundException("Category not found with id: " + id);
//        }
=======
        categoryRepository.deleteById(id);
>>>>>>> 81600d8 (rebase to dev)

    }

    @Override
    public Category findById(int id) {
<<<<<<< HEAD
        /*
         *Trong phương thức findById:
         * - Optional được dùng để ktra category có tồn tại hay không:
         *      + Nếu tồn tại: cập nhật thông tin và lưu vào DB.
         *      + Nếu ko tồn tại: trả về NotFoundException.
         * */

        //!todo: throw Exception if not found instead of null
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            System.out.println(category);
            return category.get();
        } else {
            throw new RecordNotFoundException();
        }
=======
        return categoryRepository.findById(id).orElse(null);
>>>>>>> 81600d8 (rebase to dev)
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}
