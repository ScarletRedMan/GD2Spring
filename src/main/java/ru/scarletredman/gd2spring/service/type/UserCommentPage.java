package ru.scarletredman.gd2spring.service.type;

import java.util.List;
import ru.scarletredman.gd2spring.model.UserComment;

public record UserCommentPage(List<UserComment> comments, int page, long total) {}
