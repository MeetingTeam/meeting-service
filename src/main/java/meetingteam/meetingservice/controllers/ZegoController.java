package meetingteam.meetingservice.controllers;

import lombok.RequiredArgsConstructor;
import meetingteam.meetingservice.dtos.zegocloud.ZegoTokenDto;
import meetingteam.meetingservice.services.ZegoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/zegocloud")
@RequiredArgsConstructor
public class ZegoController {
    private final ZegoService zegoService;

    @GetMapping("/token")
    public ResponseEntity<ZegoTokenDto> generateZegoToken(
            @RequestParam("meetingId") String meetingId){
        return ResponseEntity.ok(zegoService.generateToken(meetingId));
    }
}
