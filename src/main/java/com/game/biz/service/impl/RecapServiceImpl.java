package com.game.biz.service.impl;

import com.game.biz.service.BadgeLegendService;
import com.game.biz.service.BadgeMasterService;
import com.game.biz.service.RecapService;
import com.game.biz.service.ResultatService;
import com.game.biz.service.dto.RecapDTO;
import com.game.biz.service.dto.UserDTO;
import com.game.repository.biz.BadgeLegendRepository;
import com.game.repository.biz.BadgeMasterRepository;
import com.game.repository.biz.ResultatRepository;
import com.game.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class RecapServiceImpl implements RecapService {

    private final Logger log = LoggerFactory.getLogger(RecapServiceImpl.class);

    private final BadgeMasterService badgeMasterService;

    private final BadgeLegendService badgeLegendService;

    private final UserService userService;

    private final ResultatService resultatService;

    public RecapServiceImpl(ResultatRepository resultatRepository, BadgeMasterService badgeMasterService, BadgeLegendService badgeLegendService, UserService userService, ResultatService resultatService) {
        this.badgeMasterService = badgeMasterService;
        this.badgeLegendService = badgeLegendService;
        this.userService = userService;
        this.resultatService = resultatService;
    }

    public List<RecapDTO> fullRecap(){
        List<RecapDTO> retour = new ArrayList<>();

        List<UserDTO> users = userService.findAllUsers();

        users.stream().forEach(userDTO -> {
            retour.add( new RecapDTO(userDTO,
            resultatService.findByUserId(userDTO.getId()),
                (int)badgeMasterService.getNbBadgesMaster(userDTO.getId()),
                (int)badgeLegendService.getNbBadgesLegend(userDTO.getId()) ) );
        });

        return retour;
    }

}
