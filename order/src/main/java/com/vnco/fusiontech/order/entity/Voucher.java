package com.vnco.fusiontech.order.entity;

import com.vnco.fusiontech.common.constant.DBConstant;
import com.vnco.fusiontech.order.listener.VoucherListener;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.annotations.Formula;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = DBConstant.VOUCHER_TABLE)
@EntityListeners(VoucherListener.class)
public class Voucher {
    private interface FORMULA {
        String VOUCHER_USAGE = "(get_voucher_usage(code))";
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code", nullable = false, unique = true)
    @NotBlank
    private String code;

    @Column(name = "description")
    private String description;

    @Column(name = "discount")
    @NotNull
    @Positive
    private Byte discount;

    @Column(name = "min_order_amount")
    private Double minOrderAmount;

    @Column(name = "max_discount_amount")
    private Double maxDiscountAmount;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    @Column(name = "limit_usage")
    private Integer limitUsage;

    @Column(name = "user_limit_usage")
    private Short userLimitUsage;

    @Formula(FORMULA.VOUCHER_USAGE)
    private Integer usage;
    //
    // private Double getUsagePercent(){
    // return usage != null ? ( usage.doubleValue() / limitUsage) * 100 : 0;
    // }
}
