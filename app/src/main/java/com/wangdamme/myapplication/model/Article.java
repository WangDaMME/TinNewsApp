package com.wangdamme.myapplication.model;

import java.util.Objects;

// Pojo class for each Article Returns
public class Article {

    public String author;
    public String content;
    public String description;
    public String publishedAt;
    public String title;
    public String url;
    public String urlToImage;
    // public boolean favorite;
    //  public String source  -- omitted


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return Objects.equals(author, article.author) &&
                Objects.equals(content, article.content) &&
                Objects.equals(description, article.description) &&
                Objects.equals(publishedAt, article.publishedAt) &&
                Objects.equals(title, article.title) &&
                Objects.equals(url, article.url) &&
                Objects.equals(urlToImage, article.urlToImage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, content, description, publishedAt, title, url, urlToImage);
    }

    @Override
    public String toString() {
        return "Article{" +
                "author='" + author + '\'' +
                ", content='" + content + '\'' +
                ", description='" + description + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", urlToImage='" + urlToImage + '\'' +
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