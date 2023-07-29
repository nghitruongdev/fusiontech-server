package com.vnco.fusiontech.product.entity;

import com.vnco.fusiontech.common.constant.DBConstant;
import com.vnco.fusiontech.common.entity.FirebaseImage;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Type;

import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings("serial")
@Accessors(chain = true)
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = DBConstant.BRAND_TABLE)
public class Brand implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    // @NaturalId
    private String name;

    @Type(JsonType.class)
    @Column(columnDefinition = "json")
    private FirebaseImage image;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Brand brand))
            return false;

        if (!Objects.equals(id, brand.id))
            return false;
        return Objects.equals(name, brand.name);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
