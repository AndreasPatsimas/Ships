package com.papei.pms.ships.services;

import com.papei.pms.ships.dto.AnfrDto;
import org.springframework.stereotype.Service;

@Service
public interface AnfrService {

    AnfrDto fetchById(String id);
}
