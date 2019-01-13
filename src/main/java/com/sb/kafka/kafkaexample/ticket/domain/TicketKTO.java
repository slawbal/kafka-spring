package com.sb.kafka.kafkaexample.ticket.domain;

import java.io.Serializable;

public class TicketKTO implements Serializable {
    private String id;
    private String title;
    private String content;

    public TicketKTO(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public TicketKTO() {
    }

    public TicketKTO(String title) {
        this.title = title;
    }

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

    @Override
    public String toString() {
        return "TicketKTO{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TicketKTO ticketKTO = (TicketKTO) o;

        if (id != null ? !id.equals(ticketKTO.id) : ticketKTO.id != null) return false;
        if (title != null ? !title.equals(ticketKTO.title) : ticketKTO.title != null) return false;
        return content != null ? content.equals(ticketKTO.content) : ticketKTO.content == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }
}
