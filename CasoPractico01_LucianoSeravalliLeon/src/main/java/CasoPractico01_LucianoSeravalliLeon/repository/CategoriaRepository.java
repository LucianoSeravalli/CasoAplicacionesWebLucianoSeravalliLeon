package CasoPractico01_LucianoSeravalliLeon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import CasoPractico01_LucianoSeravalliLeon.domain.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

}