package com.vnco.fusiontech.common.web.request.mail;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.util.StringUtils;

import java.util.List;

@Getter
@Setter
public class OrderItemDTO {
    private static ObjectMapper mapper = new ObjectMapper();
    private  Long         id;
    private String       name;
    private String       sku;
    private List<String> images;
    private Double       price;
    private Byte      quantity;
    private  Byte      discount;
    
    @SneakyThrows
    public void setImages(String images) {
        if(!StringUtils.hasText(images)){
           return;
        }
       List<String> imageList =  mapper.readValue(String.valueOf(images).getBytes(), List.class);
        this.images = imageList;
    }
}
