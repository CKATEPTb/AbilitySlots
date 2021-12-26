package ru.ckateptb.abilityslots.user;

import lombok.Getter;
import org.bukkit.entity.Player;
import ru.ckateptb.abilityslots.ability.conditional.*;
import ru.ckateptb.abilityslots.ability.info.AbilityInformation;
import ru.ckateptb.abilityslots.board.AbilityBoard;
import ru.ckateptb.abilityslots.config.AbilitySlotsConfig;
import ru.ckateptb.abilityslots.energy.EnergyBar;
import ru.ckateptb.abilityslots.service.AbilityInstanceService;
import ru.ckateptb.abilityslots.service.AbilityService;

public class PlayerAbilityUser extends LivingEntityAbilityUser {
    private final AbilityBoard abilityBoard;
    @Getter
    private final EnergyBar energyBar;
    private final CompositeAbilityConditional abilityBindConditional = new CompositeAbilityConditional();

    public PlayerAbilityUser(Player livingEntity, AbilitySlotsConfig config, AbilityService abilityService, AbilityInstanceService abilityInstanceService) {
        super(livingEntity);
        this.abilityBoard = new AbilityBoard(this, config, abilityService);
        this.energyBar = new EnergyBar(this, config);
        this.abilityBindConditional.add(
                new CategoryAbilityConditional(),
                new EnabledAbilityConditional(),
                new CanBindToSlotAbilityConditional(),
                new PermissionAbilityConditional()
        );
        abilityInstanceService.createPassives(this);
    }

    public int getHeldItemSlot() {
        return getEntity().getInventory().getHeldItemSlot() + 1;
    }

    public boolean canBind(AbilityInformation ability) {
        return this.abilityBindConditional.matches(this, ability);
    }

    @Override
    public AbilityInformation getSelectedAbility() {
        return slotContainer.getAbility(getHeldItemSlot());
    }

    @Override
    public void updateAbilityBoard() {
        this.abilityBoard.update();
    }

    @Override
    public void enableAbilityBoard() {
        this.abilityBoard.setEnabled(true);
    }

    @Override
    public void disableAbilityBoard() {
        this.abilityBoard.setEnabled(false);
    }

    @Override
    public boolean isAbilityBoardEnabled() {
        return this.abilityBoard.isEnabled();
    }

    @Override
    public double getEnergy() {
        return super.getEnergy();
    }

    @Override
    public void setEnergy(double value) {
        super.setEnergy(value);
    }

    @Override
    public double getMaxEnergy() {
        return super.getMaxEnergy();
    }

    @Override
    public void updateEnergyBar() {
        this.energyBar.update();
    }

    @Override
    public void enableEnergyBar() {
        this.energyBar.setEnabled(true);
    }

    @Override
    public void disableEnergyBar() {
        this.energyBar.setEnabled(false);
    }

    @Override
    public boolean isEnergyBarEnabled() {
        return this.energyBar.isEnabled();
    }

    @Override
    public Player getEntity() {
        return (Player) livingEntity;
    }
}
