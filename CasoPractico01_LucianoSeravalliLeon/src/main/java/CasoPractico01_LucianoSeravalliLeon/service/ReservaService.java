package CasoPractico01_LucianoSeravalliLeon.service;

import CasoPractico01_LucianoSeravalliLeon.domain.Reserva;
import CasoPractico01_LucianoSeravalliLeon.repository.ReservaRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Transactional(readOnly = true)
    public List<Reserva> getReservas() {
        return reservaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Reserva> getReserva(Integer idReserva) {
        return reservaRepository.findById(idReserva);
    }

    @Transactional
    public void save(Reserva reserva) {
        reservaRepository.save(reserva);
    }

    @Transactional
    public void delete(Integer idReserva) {
        if (!reservaRepository.existsById(idReserva)) {
            throw new IllegalArgumentException("La reserva con ID " + idReserva + " no existe.");
        }
        try {
            reservaRepository.deleteById(idReserva);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("No se puede eliminar la reserva. Tiene datos asociados.", e);
        }
    }
}