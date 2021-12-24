package com.sparta.weeklytestspring.controller;

import com.sparta.weeklytestspring.domain.Article;
import com.sparta.weeklytestspring.dto.ArticleCommentRequestDto;
import com.sparta.weeklytestspring.dto.GetArticleDto;
import com.sparta.weeklytestspring.dto.GetArticlesDto;
import com.sparta.weeklytestspring.dto.SetArticleDto;
import com.sparta.weeklytestspring.security.UserDetailsImpl;
import com.sparta.weeklytestspring.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@RequiredArgsConstructor
@RestController
public class ArticleController {

    private final ArticleService articleService;
    private final ModelMapper modelMapper;

    @PostMapping("/article")
    public SetArticleDto.Response setArticle(@ModelAttribute SetArticleDto.Request articleRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        Article article = articleService.setArticle(articleRequestDto, userDetails.getUser());
        SetArticleDto.Response response = modelMapper.map(article, SetArticleDto.Response.class);
        return response;
    }

    @GetMapping("/articles")
    public List<GetArticlesDto.Response> getArticles(@RequestParam(required = false) String searchTag,@RequestParam Integer page, @RequestParam Integer size){
        List<Article> articles = articleService.getArticles(searchTag);
        List<GetArticlesDto.Response> response = modelMapper.map(articles, new TypeToken<List<GetArticlesDto.Response>>() {}.getType());
        //어...
        return response;
    }

    @GetMapping("/article/{id}")
    public GetArticleDto.Response getArticle(@PathVariable Long id){
        Article article = articleService.getArticle(id);
        GetArticleDto.Response response = modelMapper.map(article, GetArticleDto.Response.class);
        return response;
    }


    @PostMapping("/article/comment")
    public void setArticleComment(@RequestBody ArticleCommentRequestDto articleCommentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        articleService.setArticleComment(articleCommentRequestDto);
    }

//    @GetMapping("/api/article")
//    public Page<Article> getProducts(
//            @RequestParam("page") int page,
//            @RequestParam("size") int size,
//            @RequestParam("sortBy") String sortBy
//    ) {
//        page = page - 1;
//        return articleService.getProducts(page , size, sortBy);
//    }
}
