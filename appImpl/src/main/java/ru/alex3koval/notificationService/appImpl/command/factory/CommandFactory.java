package ru.alex3koval.notificationService.appImpl.command.factory;

import ru.alex3koval.notificationService.domain.command.Command;

public interface CommandFactory<COMMAND extends Command<?>, COMMAND_DTO> {
    COMMAND create(COMMAND_DTO dto);
}
