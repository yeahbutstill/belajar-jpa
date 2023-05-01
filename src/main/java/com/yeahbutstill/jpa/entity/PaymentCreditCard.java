package com.yeahbutstill.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "payments_credit_card")
public class PaymentCreditCard extends Payment {

    // masking credit card
    @Column(name = "masked_card")
    private String maskedCard;

    @Column(name = "bank")
    private String bank;

}
