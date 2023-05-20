package Medium.DeFam.app.bean;

import java.io.Serializable;

public class GongGaoBean implements Serializable {
     private String id;//":6,
     private String title;//":"如何在DeFam平台上赚取积分和藏宝图",
     private String content;//":"<p>在DeFam平台成为行业领袖，赚取藏宝图寻宝<\/p>\n<p> <\/p>",
     private String sort;//":1,
     private String display;//":1,
     private String created_at;//":"2023-02-06 10:29:45",
     private String updated_at;//":

     public String getId() {
          return id;
     }

     public void setId(String id) {
          this.id = id;
     }

     public String getTitle() {
          return title;
     }

     public void setTitle(String title) {
          this.title = title;
     }

     public String getContent() {
          return content;
     }

     public void setContent(String content) {
          this.content = content;
     }

     public String getSort() {
          return sort;
     }

     public void setSort(String sort) {
          this.sort = sort;
     }

     public String getDisplay() {
          return display;
     }

     public void setDisplay(String display) {
          this.display = display;
     }

     public String getCreated_at() {
          return created_at;
     }

     public void setCreated_at(String created_at) {
          this.created_at = created_at;
     }

     public String getUpdated_at() {
          return updated_at;
     }

     public void setUpdated_at(String updated_at) {
          this.updated_at = updated_at;
     }
}
