package service.java.quotes.models;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class Quote {
    @ApiModelProperty(notes = "Id quote")
    private @Id
    String id;

    @ApiModelProperty(notes = "Link to the source of the quote")
    private String link;

    @ApiModelProperty(notes = "Title quote")
    private String title;

    @ApiModelProperty(notes = "Description quote")
    @Column(length = 65535)
    private String description;

    @ApiModelProperty(notes = "Publication date quote")
    private Date pubDate;

    @ApiModelProperty(notes = "Date added quote")
    private Date createdAt;

    @ApiModelProperty(notes = "Date update quote")
    private Date updatedAt;

    Quote() {

    }

    public Quote(String guid, String link, String title, String description, Date pubDate) {
        this.id = guid;
        this.link = link;
        this.title = title;
        this.description = description;
        this.pubDate = pubDate;
    }

    public String getId() {
        return id;
    }

    public String getLink() {
        return link;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quote quote = (Quote) o;
        return Objects.equals(id, quote.id) &&
                Objects.equals(link, quote.link) &&
                Objects.equals(title, quote.title) &&
                Objects.equals(description, quote.description) &&
                Objects.equals(pubDate, quote.pubDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, link, title, description, pubDate);
    }

    @Override
    public String toString() {
        return "Quote{" +
                "id='" + id + '\'' +
                ", link='" + link + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", pubDate=" + pubDate +
                '}';
    }

    @PrePersist
    public void prePersist() {
        updatedAt = new Date();
        createdAt = new Date();
    }

    @PreUpdate
    public void onPreUpdate() {
        updatedAt = new Date();
    }
}
