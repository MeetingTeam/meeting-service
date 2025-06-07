package meetingteam.meetingservice.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import meetingteam.meetingservice.dtos.Meeting.CreateMeetingDto;
import meetingteam.meetingservice.dtos.Meeting.ResMeetingDto;
import meetingteam.meetingservice.dtos.Meeting.UpdateMeetingDto;
import meetingteam.meetingservice.services.MeetingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/meeting")
@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
public class MeetingController {
    private final MeetingService meetingService;

    @PostMapping
    public ResponseEntity<ResMeetingDto> createMeeting(
            @RequestBody @Valid CreateMeetingDto meetingDto){
        return ResponseEntity.ok(meetingService.createMeeting(meetingDto));
    }

    @PatchMapping
    public ResponseEntity<Void> updateMeeting(
            @RequestBody @Valid UpdateMeetingDto meetingDto){
        meetingService.updateMeeting(meetingDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reaction/{meetingId}")
    public ResponseEntity<Void> reactMeeting(
            @PathVariable("meetingId") String meetingId,
            @RequestParam(value = "emojiCode", required = false) String emojiCode){
        meetingService.reactMeeting(meetingId, emojiCode);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/cancel/{meetingId}")
    public ResponseEntity<Void> cancelMeeting(
            @PathVariable("meetingId") String meetingId,
            @RequestParam("isCanceled") Boolean isCanceled){
        meetingService.cancelMeeting(meetingId, isCanceled);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<ResMeetingDto>> getMeetingsOfVideoChannel(
            @RequestParam("channelId") String channelId,
            @RequestParam("receivedMeetingNum") Integer receivedMeetingNum){
        return ResponseEntity.ok(meetingService.getVideoChannelMeetings(channelId, receivedMeetingNum));
    }

    @DeleteMapping("/{meetingId}")
    public ResponseEntity<Void> deleteMeeting(
            @PathVariable("meetingId") String meetingId){
        meetingService.deleteMeeting(meetingId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/private/channel/{channelId}")
    public ResponseEntity<Void> deleteByChannelId(
            @PathVariable("channelId") String channelId){
        meetingService.deleteMeetingsByChannelId(channelId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/private/team/{teamId}")
    public ResponseEntity<Void> deleteByTeamId(
            @PathVariable("teamId") String teamId){
        meetingService.deleteMeetingsByTeamId(teamId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/version")
    @PreAuthorize("isAnonymous()")
    public ResponseEntity<String> getVersion(){
        return ResponseEntity.ok("version 3.0");
    }
}
