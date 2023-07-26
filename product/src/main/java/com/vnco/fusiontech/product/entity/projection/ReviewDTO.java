package com.vnco.fusiontech.product.entity.projection;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vnco.fusiontech.product.entity.proxy.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
        private Long id;
        private int rating;
        private String comment;

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        private LocalDateTime createdAt;

        private User user;

        public void setCreatedAt(Instant createdAt) {
                this.createdAt = LocalDateTime.ofInstant(createdAt, ZoneId.systemDefault());
        }

}
