package ru.mudan.SeansService.facade;

import ru.mudan.SeansService.dto.SeansDTO;
import ru.mudan.SeansService.entity.Seans;
import org.springframework.stereotype.Component;

@Component
public class SeansFacade {
    public SeansDTO seansToSeansDTO(Seans seans){
        SeansDTO seansDTO = new SeansDTO();
        seansDTO.setId(seans.getId());
        seansDTO.setTitle(seans.getTitle());
        seansDTO.setUserId(seans.getUserId());
        seansDTO.setDoctorId(seans.getDoctorId());
        return seansDTO;
    }
}
