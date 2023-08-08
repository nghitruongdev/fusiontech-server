package com.vnco.fusiontech.product.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vnco.fusiontech.common.constant.DBConstant;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Accessors(chain = true)
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = DBConstant.SPEC_TABLE, uniqueConstraints = @UniqueConstraint(columnNames = { "name",
    "specification_value" }))
// @Table(name = DBConstant.SPEC_TABLE)
public class Specification {
  @Id
  @Column (name = "id")
  @GeneratedValue (strategy = GenerationType.IDENTITY)
  private Long   id;
  @Column (name = "name")
  private String name;
  @Column(name = "specification_value")
  private String value;

  @Builder.Default
  @ToString.Exclude
  @ManyToMany(mappedBy = "specifications")
  @JsonIgnore
  Set<Variant> variants = new HashSet<>();

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    Specification that = (Specification) o;

    if (!Objects.equals(id, that.id))
      return false;
    if (!Objects.equals(name, that.name))
      return false;
    return Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (value != null ? value.hashCode() : 0);
    return result;
  }
}
