package server.database;

import commons.Payment;
import commons.Person;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The repository interface of the Payment class.
 */
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByAmount(BigDecimal amount);
    List<Payment> findByReceiver(Person receiver);
    List<Payment> findByPayer(Person payer);
}
