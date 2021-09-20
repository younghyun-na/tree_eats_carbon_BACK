package smu.earthranger.controller;


import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smu.earthranger.dto.ResponseMessage;
import smu.earthranger.dto.follow.FollowResponseDto;
import smu.earthranger.service.FollowService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/follow")
public class FollowController {

    private final FollowService followService;
    private Long userId = 1L;

    //follow/search/?option=0&value="semi"
    @GetMapping("/search")
    public ResponseEntity<FollowResponseDto> searchFollower(
                                           @RequestParam("option") int option,
                                           @RequestParam("value") String value){

        FollowResponseDto dto = followService.findFollowerByOption(userId, option, value);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<?> searchFollowList(){
        List<FollowResponseDto> followList = followService.getFollowList(userId);
        return ResponseEntity.ok(followList);
    }


    @PostMapping("/{memberId}")
    public ResponseEntity<ResponseMessage> addFollower(@PathVariable("memberId") Long memberId){

        followService.followMember(userId, memberId);
        return ResponseEntity.ok(new ResponseMessage(HttpStatus.CREATED, "ok"));
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<ResponseMessage> deleteFollower(@PathVariable("memberId") Long memberId){
        followService.unfollowMember(userId, memberId);
        return ResponseEntity.ok(new ResponseMessage(HttpStatus.NO_CONTENT, "ok"));
    }

}
