package com.vnco.fusiontech.product.entity;

import com.vnco.fusiontech.common.constant.DBConstant;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
@Table(name = DBConstant.CATEGORY_TABLE)
public class Category implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(name = "name")
    private String name;
    @NotBlank
    @NaturalId(mutable = true)
    @Column(name = "slug")
    private String slug;
    @Column(name = "description")
    private String description;

    // @Type(JsonType.class)
    // @Column(columnDefinition = "json")
    @Column(name = "image")
    private String image;

    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "parent_id")
    // @ToString.Exclude
    // private Category parent;

    @Type(JsonType.class)
    @Column(name = "specifications",columnDefinition = "json")
    private List<String> specifications;

    @OneToMany(mappedBy = "category")
    @Builder.Default
    @ToString.Exclude
    private List<Product> products = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Category category))
            return false;

        if (!Objects.equals(id, category.id))
            return false;
        return Objects.equals(slug, category.slug);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (slug != null ? slug.hashCode() : 0);
        return result;
    }
}
