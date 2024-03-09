package dev.backend.tutor.entities;

import jakarta.persistence.*;

/**
 * Represents a Credit Card entity.
 * This is just a demo class.
 * The real Credit Card will be implemented later
 */

@Entity
@Table(name = "credit_cards")
public class CreditCard {
    @Id
    private String number;

    @ManyToOne
    @JoinColumn(name = "owner_username")
    private Student owner;
    private String cvd;
    private String expirationDate;
}
