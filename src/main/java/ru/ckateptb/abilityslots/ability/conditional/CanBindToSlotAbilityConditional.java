package ru.ckateptb.abilityslots.ability.conditional;

import ru.ckateptb.abilityslots.ability.info.AbilityInformation;
import ru.ckateptb.abilityslots.user.AbilityUser;

public class CanBindToSlotAbilityConditional implements AbilityConditional {
    @Override
    public boolean matches(AbilityUser user, AbilityInformation ability) {
        return ability != null && ability.isCanBindToSlot();
    }
}
