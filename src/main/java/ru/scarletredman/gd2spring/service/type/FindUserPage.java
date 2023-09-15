package ru.scarletredman.gd2spring.service.type;

import java.util.List;
import ru.scarletredman.gd2spring.model.dto.UserScoreDTO;

public record FindUserPage(List<UserScoreDTO> users, int page, long total) {}
