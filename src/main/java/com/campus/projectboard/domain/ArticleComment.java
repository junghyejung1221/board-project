package com.campus.projectboard.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;


@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "content"),
        @Index(columnList = "createAt"),
        @Index(columnList = "createBy"),
} )

@EntityListeners(AuditingEntityListener.class)
@Entity
public class ArticleComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter @ManyToOne(optional = false)
    private Article article;  //게시글
    @Setter @Column(nullable = false, length = 500)
    private String content;  //내용

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();


    @CreatedDate
    @Column(nullable = false )
    private LocalDateTime createAt;  //생성일시
    @CreatedBy
    @Column(nullable = false, length = 100)
    private String createBy;  //생성자
    @LastModifiedDate
    @Column(nullable = false )
    private LocalDateTime modifiedAt;  //수정일시
    @LastModifiedBy
    @Column(nullable = false, length = 100)
    private String modifiedBy;  //수정자

    protected ArticleComment() {
    }

    private ArticleComment(Article article, String content) {
        this.article = article;
        this.content = content;
    }

    public static ArticleComment of(Article article, String content) {
        return new ArticleComment(article, content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArticleComment)) return false;
        ArticleComment that = (ArticleComment) o;
        return id !=null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
