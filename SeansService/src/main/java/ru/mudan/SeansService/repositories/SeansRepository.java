package ru.mudan.SeansService.repositories;

import ru.mudan.SeansService.entity.Seans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeansRepository extends JpaRepository<Seans,Long> {
}
