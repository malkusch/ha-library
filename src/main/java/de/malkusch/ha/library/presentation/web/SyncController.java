package de.malkusch.ha.library.presentation.web;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import de.malkusch.ha.library.application.SyncFindusService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
final class SyncController {

    private final SyncFindusService service;

    @GetMapping("/sync")
    public String sync() throws IOException, InterruptedException {
        service.sync();
        return "OK\n";
    }

}
