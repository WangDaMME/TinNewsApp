package com.wangdamme.myapplication.model;

import java.util.List;
import java.util.Objects;

public class NewsResponse {

    public Integer totalResult;  // total Result
    public List<Article> articles;  // return a lot
    public String code; //
    public String status;  //"ok"
    public String message;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewsResponse that = (NewsResponse) o;
        return Objects.equals(totalResult, that.totalResult) &&
                Objects.equals(articles, that.articles) &&
                Objects.equals(code, that.code) &&
                Objects.equals(status, that.status) &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalResult, articles, code, status, message);
    }

    @Override
    public String toString() {
        return "NewsResponse{" +
                "totalResult=" + totalResult +
                ", articles=" + articles +
                ", code='" + code + '\'' +
                ", status='" + status + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}


/*
{
   "status":"ok",
   "totalResults":1,
   "articles":[
      {
         "source":{
            "id":"the-hill",
            "name":"The Hill"
         },


         "author":"Alexander Bolton",
         "title":"This is the title",
         "description":"This is the description",
         "url":"https://thehill.com/",
         "urlToImage":"https://thehill.com/test.jpg",
         "publishedAt":"2020-04-16T03:22:24Z",
         "content":"Test content"
      }
   ]
}
 */