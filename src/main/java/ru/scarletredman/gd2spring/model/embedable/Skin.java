package ru.scarletredman.gd2spring.model.embedable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Skin {

    @Column(name = "icon", nullable = false)
    @Min(0)
    private int icon = 1;

    @Column(name = "first_color", nullable = false)
    @Min(0)
    private int firstColor = 0;

    @Column(name = "second_color", nullable = false)
    @Min(0)
    private int secondColor = 3;

    @Column(name = "icon_type", nullable = false)
    @Min(0)
    private int currentIconType = 0;

    @Column(name = "special_skin", nullable = false)
    @Min(0)
    private int specialSkin = 0;

    @Column(name = "acc_icon", nullable = false)
    @Min(0)
    private int accIcon = 1;

    @Column(name = "acc_ship", nullable = false)
    @Min(0)
    private int accShip = 1;

    @Column(name = "acc_ball", nullable = false)
    @Min(0)
    private int accBall = 1;

    @Column(name = "acc_bird", nullable = false)
    @Min(0)
    private int accBird = 1;

    @Column(name = "acc_dart", nullable = false)
    @Min(0)
    private int accDart = 1;

    @Column(name = "acc_robot", nullable = false)
    @Min(0)
    private int accRobot = 1;

    @Column(name = "acc_glow", nullable = false)
    @Min(0)
    private int accGlow = 0;

    @Column(name = "acc_spider", nullable = false)
    @Min(0)
    private int accSpider = 1;

    @Column(name = "acc_explosion", nullable = false)
    @Min(0)
    private int accExplosion = 1;
}
