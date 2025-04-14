package me.hamza.blaze.kits.meta;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Hammzar
 * @since 14.04.2025
 */
@Getter
@Setter
public class KitMechanics {

    private boolean boxing;
    private boolean sumo;
    private boolean regen;
    private boolean hunger;
    private boolean beds;
    private boolean bridges;
    private boolean trapping;
    private boolean pearlFight;
    private boolean battleRush;
    private boolean stickFight;

    public KitMechanics() {
        boxing = false;
        sumo = false;
        regen = false;
        hunger = false;
        beds = false;
        bridges = false;
        trapping = false;
        pearlFight = false;
        battleRush = false;
        stickFight = false;
    }

}
