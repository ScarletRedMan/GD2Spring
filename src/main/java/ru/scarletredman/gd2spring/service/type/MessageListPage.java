package ru.scarletredman.gd2spring.service.type;

import java.util.List;
import ru.scarletredman.gd2spring.model.Message;

public record MessageListPage(List<Message> messages, int page, long total) {}
