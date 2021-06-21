package com.socialsitebackend.socialsite.tags;


import com.socialsitebackend.socialsite.exceptions.PostNotFoundException;
import com.socialsitebackend.socialsite.exceptions.TagNotFoundException;
import com.socialsitebackend.socialsite.post.dto.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/tags")
public class TagControler {

    private final TagService tagService;

    @RequestMapping(value = "/getTagPage/{tagName}", method = RequestMethod.GET)
    ResponseEntity<Page<PostResponseDto>> getTagPage(
            @PageableDefault()
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "postCreatedDate", direction = Sort.Direction.DESC)
            }) Pageable pageable, @PathVariable String tagName) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(tagService.getTagPage(pageable, tagName));
        } catch (TagNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }


}
