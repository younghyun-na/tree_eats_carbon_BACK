package smu.earthranger.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smu.earthranger.dto.ResponseMessage;
import smu.earthranger.dto.carbon.CarbonRequestDto;
import smu.earthranger.dto.carbon.CarbonResponseInfoDto;
import smu.earthranger.service.CarbonService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/carbon")
public class CarbonController {

    private final CarbonService carbonService;
    private Long userId = 1L;

    @GetMapping
    public ResponseEntity<CarbonResponseInfoDto> getInfo(){
        CarbonResponseInfoDto info = carbonService.getInfo(userId);
        return ResponseEntity.ok(info);
    }

    @PostMapping
    public ResponseEntity<ResponseMessage> saveCarbon(@RequestBody CarbonRequestDto carbon){
        carbonService.saveCarbon(userId, carbon);
        return ResponseEntity.ok(new ResponseMessage(HttpStatus.CREATED,"ok"));
    }
}
