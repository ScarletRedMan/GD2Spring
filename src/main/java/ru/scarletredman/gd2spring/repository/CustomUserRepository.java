package ru.scarletredman.gd2spring.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import ru.scarletredman.gd2spring.model.User;
import ru.scarletredman.gd2spring.model.dto.UserScoreDTO;
import ru.scarletredman.gd2spring.service.type.FindUserPage;

@Repository
public interface CustomUserRepository {

    void updateSettingsForUser(User user);

    List<UserScoreDTO> getTop100ByStars();

    List<UserScoreDTO> getTop100ByCreatorPoints();

    List<UserScoreDTO> getRelativeTop(User user);

    int getRating(int stars);

    int getRating(User user);

    FindUserPage findUser(String response, int page);
}
