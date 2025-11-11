package airport.repository;

import airport.model.Aeroporto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AeroportoRepository extends JpaRepository<Aeroporto, Long> {
}
