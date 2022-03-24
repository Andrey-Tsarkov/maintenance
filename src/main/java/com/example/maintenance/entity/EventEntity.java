package com.example.maintenance.entity;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;

@Entity
@Table(name = "event")
public class EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Positive(message = "Должно быть задано")
    @NotNull(message = "Должно быть задано")
    @Column(name = "action_id", nullable = false)
    private Integer actionId;

    @PositiveOrZero
    @NotNull
    @Column(name = "mileage", columnDefinition = "integer default 0")
    private Integer mileage = 0;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull
    @Column(name = "event_date", nullable = false)
    private LocalDate eventDate;

    @ManyToOne
    @JoinColumn(name="action_id", referencedColumnName="id", insertable=false, updatable=false)
    @NotFound(action = NotFoundAction.IGNORE)
    private ActionEntity actionEntity;

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    public Integer getActionId() {
        return actionId;
    }

    public void setActionId(Integer actionId) {
        this.actionId = actionId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ActionEntity getActionEntity() {
        return actionEntity;
    }

    public void setActionEntity(ActionEntity actionEntity) {
        this.actionEntity = actionEntity;
    }
}