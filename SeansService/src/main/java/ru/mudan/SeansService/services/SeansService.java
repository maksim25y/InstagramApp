package ru.mudan.SeansService.services;

import ru.mudan.SeansService.dto.SeansDTO;
import ru.mudan.SeansService.entity.Seans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mudan.SeansService.repositories.SeansRepository;

import java.util.List;

@Service
@Transactional
public class SeansService {
    private final SeansRepository seansRepository;
    @Autowired
    public SeansService(SeansRepository seansRepository) {
        this.seansRepository = seansRepository;
    }
    public Seans createSeans(SeansDTO seansDTO) {
        Seans seans = new Seans();
        seans.setTitle(seansDTO.getTitle());
        seans.setDoctorId(seansDTO.getDoctorId());
        seans.setUserId(seansDTO.getUserId());
        return seansRepository.save(seans);
    }

    public List<Seans> getAll() {
        return seansRepository.findAll();
    }
}
