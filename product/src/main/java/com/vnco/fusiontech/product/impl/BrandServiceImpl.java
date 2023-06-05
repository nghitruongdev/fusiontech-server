package com.vnco.fusiontech.product.impl;

import com.vnco.fusiontech.common.exception.RecordNotFoundException;
import com.vnco.fusiontech.product.entity.Brand;
import com.vnco.fusiontech.product.repository.BrandRepository;
import com.vnco.fusiontech.product.repository.ProductRepository;
import com.vnco.fusiontech.product.service.BrandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BrandServiceImpl implements BrandService {
    @Autowired
    BrandRepository brandRepository;
    ProductRepository productRepository;

    @Override
    public Brand save(Brand brand) {
        return brandRepository.save(brand);
    }

    /* Optional là gì?
            - Optional là 1 lớp được dùng để giải quyết vấn đề về gtri null
            - Optional giải quyết vấn đề null bằng cách bọc giá trị của biến trong 1 đối tượng Optional
                + Nếu gtri của biến là null -> Optional sẽ không có giá trị.
                + Nếu gtri != null -> Optinal sẽ chứa đối tượng đó.
            - Optinal sẽ ktra gtri có tồn tại hay không trước khi truy xuất đến nó. Tránh lỗi NullPointerException -> dẽ đọc hơn
     * */
    @Override
    public Brand update(Brand brand, Integer id) {
        /*
         *Trong phương thức update:
         * - Optional được dùng để ktra brand có tồn tại hay không:
         *      + Nếu tồn tại: cập nhật thông tin và lưu vào DB.
         *      + Nếu ko tồn tại: trả về NotFoundException.
         * */
        Optional<Brand> existingBrand = brandRepository.findById(id);
        if (existingBrand.isPresent()) {
            Brand updateBrand = existingBrand.get();
            updateBrand.setName(brand.getName());
            return brandRepository.save(updateBrand);
        } else {
            throw new RecordNotFoundException();
        }
    }

    @Override
    public void delete(int id) {
//        brandRepository.deleteById(id);

        /*
         * Phương thức sử dụng brandRepository.findById(id) để tìm kiếm Brand với id tương ứng
         *  +  Nếu Category tồn tại, phương thức tiếp tục kiểm tra xem có sản phẩm nào thuộc về Brand này không bằng cách sử dụng productRepository.findByCategoryId(id)
         *  +  Nếu danh sách sản phẩm không rỗng, phương thức sẽ ném ra một ngoại lệ ConflictException "Cannot delete Brand with products".
         *  +  Nếu danh sách sản phẩm rỗng, phương thức sử dụng brandRepository.deleteById(id) để xóa Category với id tương ứng.
         * */

//        Optional<Brand> brand = brandRepository.findById(id);
//        if (brand.isPresent()){
//            List<Product> products = productRepository.findByBrandId(id);
//            if (!products.isEmpty()){
//                throw new ConflictException("Cannot delete brand with products");
//            }
//            brandRepository.deleteById(id);
//        }else {
//            throw new NotFoundException("Brand not found with id: "+id);
//        }
    }

    @Override
    public Brand findById(int id) {
//        return brandRepository.findById(id).orElse(null);

        /*
         *Trong phương thức findById:
         * - Optional được dùng để ktra brand có tồn tại hay không:
         *      + Nếu tồn tại: cập nhật thông tin và lưu vào DB.
         *      + Nếu ko tồn tại: trả về NotFoundException.
         * */
        Optional<Brand> brand = brandRepository.findById(id);
        if (brand.isPresent()) {
            log.debug("sai roi: {} ",brand);
            return brand.get();
        } else {
            throw new RecordNotFoundException();
        }
    }

    @Override
    public List<Brand> findAll() {
        return brandRepository.findAll();
    }
}
