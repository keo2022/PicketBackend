package com.swyg.picketbackend.controller;

import com.swyg.picketbackend.Entity.Heart;
import com.swyg.picketbackend.auth.domain.Member;
import com.swyg.picketbackend.service.HeartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HeartController {
    @Autowired
    private HeartService heartService;

    @PostMapping("/board/list/{boardId}/like") // 좋아요 클릭
    public ResponseEntity<Heart> clickLike(@PathVariable Long boardId, @RequestBody Member member){
        Heart result = heartService.clickLike(boardId, member);
        return (result != null) ?
                ResponseEntity.status(HttpStatus.OK).body(result):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
