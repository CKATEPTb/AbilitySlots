/*
 * Copyright (c) 2022 CKATEPTb <https://github.com/CKATEPTb>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package ru.ckateptb.abilityslots.user;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.Nullable;
import ru.ckateptb.abilityslots.ability.Ability;
import ru.ckateptb.tablecloth.collision.collider.RayCollider;
import ru.ckateptb.tablecloth.math.ImmutableVector;

import java.util.function.Predicate;

public interface AbilityTarget extends AbilityTargetEntity {

    static AbilityTargetEntity of(Entity entity) {
        return () -> entity;
    }

    static AbilityTarget of(LivingEntity entity) {
        return () -> entity;
    }

    LivingEntity getEntity();

    default void damage(double amount, Ability ability) {
        damage(amount, ability, false);
    }

    default void damage(double amount, Ability ability, boolean ignoreNoDamageTicks) {
        LivingEntity entity = getEntity();
        if (ignoreNoDamageTicks) {
            entity.setNoDamageTicks(0);
        }
        entity.damage(amount, ability.getUser().getEntity());
    }

    default boolean isSneaking() {
        return !(getEntity() instanceof Player player) || player.isSneaking();
    }

    /**
     * Attempt to find a possible block source that matches the given predicate.
     *
     * @param range the max range to check
     * @return the source Vector3d
     */

    default ImmutableVector findPosition(double range) {
        return findPosition(range, block -> true);
    }

    default ImmutableVector findPosition(double range, boolean ignoreLiquids) {
        return findPosition(range, ignoreLiquids, block -> true);
    }

    default ImmutableVector findPosition(double range, boolean ignoreLiquids, Predicate<Block> blockFilter) {
        return findPosition(range, ignoreLiquids, entity -> true, blockFilter);
    }


    default ImmutableVector findPosition(double range, Predicate<Block> blockFilter) {
        return findPosition(range, entity -> true, blockFilter);
    }

    default ImmutableVector findPosition(double range, Predicate<Entity> entityFilter, Predicate<Block> blockFilter) {
        return findPosition(range, false, entityFilter, blockFilter);
    }

    default ImmutableVector findPosition(double range, boolean ignoreLiquids, Predicate<Entity> entityFilter, Predicate<Block> blockFilter) {
        return findPosition(range, true, ignoreLiquids, entityFilter, blockFilter);
    }

    default ImmutableVector findPosition(double range, boolean ignorePassable, boolean ignoreLiquids, Predicate<Entity> entityFilter, Predicate<Block> blockFilter) {
        return findPosition(range, false, ignorePassable, ignoreLiquids, entityFilter, blockFilter);
    }

    default ImmutableVector findPosition(double range, boolean ignoreBlocks, boolean ignorePassable, boolean ignoreLiquids, Predicate<Entity> entityFilter, Predicate<Block> blockFilter) {
        return findPosition(range, false, ignoreBlocks, ignorePassable, ignoreLiquids, entityFilter, blockFilter);
    }

    default ImmutableVector findPosition(double range, double raySize) {
        return findPosition(range, raySize, block -> true);
    }

    default ImmutableVector findPosition(double range, double raySize, Predicate<Block> blockFilter) {
        return findPosition(range, raySize, entity -> true, blockFilter);
    }

    default ImmutableVector findPosition(double range, double raySize, Predicate<Entity> entityFilter, Predicate<Block> blockFilter) {
        return findPosition(range, raySize, false, entityFilter, blockFilter);
    }

    default ImmutableVector findPosition(double range, double raySize, boolean ignoreLiquids, Predicate<Entity> entityFilter, Predicate<Block> blockFilter) {
        return findPosition(range, raySize, true, ignoreLiquids, entityFilter, blockFilter);
    }

    default ImmutableVector findPosition(double range, double raySize, boolean ignorePassable, boolean ignoreLiquids, Predicate<Entity> entityFilter, Predicate<Block> blockFilter) {
        return findPosition(range, raySize, false, ignorePassable, ignoreLiquids, entityFilter, blockFilter);
    }

    default ImmutableVector findPosition(double range, double raySize, boolean ignoreBlocks, boolean ignorePassable, boolean ignoreLiquids, Predicate<Entity> entityFilter, Predicate<Block> blockFilter) {
        return findPosition(range, raySize, false, ignoreBlocks, ignorePassable, ignoreLiquids, entityFilter, blockFilter);
    }

    default ImmutableVector findPosition(double range, boolean ignoreEntity, boolean ignoreBlocks, boolean ignorePassable, boolean ignoreLiquids, Predicate<Entity> entityFilter, Predicate<Block> blockFilter) {
        return findPosition(range, 0, ignoreEntity, ignoreBlocks, ignorePassable, ignoreLiquids, entityFilter, blockFilter);
    }

    /**
     * Attempt to find a possible block source that matches the given entityFilter.
     *
     * @param range         the max range to check
     * @param entityFilter  the entityFilter to check
     * @param ignoreLiquids – true to scan through liquids
     * @return the source Vector3d
     */
    default ImmutableVector findPosition(double range, double raySize, boolean ignoreEntity, boolean ignoreBlocks, boolean ignorePassable, boolean ignoreLiquids, Predicate<Entity> entityFilter, Predicate<Block> blockFilter) {
        LivingEntity source = getEntity();
        return new ImmutableVector(new RayCollider(source, range, raySize).getPosition(ignoreEntity, ignoreBlocks, ignoreLiquids, ignorePassable, entityFilter.and(entity -> entity != source), blockFilter.and(block -> !block.getType().isAir())).orElse(getDirection().normalize().multiply(range)));
    }

    default @Nullable Block findBlock(double range) {
        return findBlock(range, 0);
    }

    default @Nullable Block findBlock(double range, double raySize) {
        return findBlock(range, raySize, false);
    }

    default @Nullable Block findBlock(double range, double raySize, boolean ignoreLiquid) {
        return findBlock(range, raySize, ignoreLiquid, true);
    }

    default @Nullable Block findBlock(double range, double raySize, boolean ignoreLiquid, boolean ignorePassable) {
        return findBlock(range, raySize, ignoreLiquid, ignorePassable, block -> true);
    }

    default @Nullable Block findBlock(double range, boolean ignoreLiquid) {
        return findBlock(range, ignoreLiquid, true);
    }

    default @Nullable Block findBlock(double range, boolean ignoreLiquid, boolean ignorePassable) {
        return findBlock(range, ignoreLiquid, ignorePassable, block -> true);
    }

    default @Nullable Block findBlock(double range, boolean ignoreLiquid, boolean ignorePassable, Predicate<Block> filter) {
        return findBlock(range, 0, ignoreLiquid, ignorePassable, filter);
    }

    /**
     * Attempt to find a possible block source that matches the given filter.
     *
     * @param range  the max range to check
     * @param filter the filter to check
     * @return the source block if one was found, null otherwise
     */
    default @Nullable Block findBlock(double range, double raySize, boolean ignoreLiquid, boolean ignorePassable, Predicate<Block> filter) {
        return new RayCollider(getEntity(), range, raySize).getBlock(ignoreLiquid, ignorePassable, filter.and(block -> !block.getType().isAir())).orElse(null);
    }

    default @Nullable LivingEntity findLivingEntity(double range, double raySize) {
        return findLivingEntity(range, raySize, false, entity -> true);
    }

    default @Nullable LivingEntity findLivingEntity(double range) {
        return findLivingEntity(range, entity -> true);
    }

    default @Nullable LivingEntity findLivingEntity(double range, Predicate<LivingEntity> predicate) {
        return findLivingEntity(range, 0, false, predicate);
    }

    /**
     * Attempt to find a possible block source that matches the given predicate.
     *
     * @param range     the max range to check
     * @param predicate the predicate to check
     * @return the source LivingEntity if one was found, null otherwise
     */
    default @Nullable LivingEntity findLivingEntity(double range, double raySize, boolean ignoreBlock, Predicate<LivingEntity> predicate) {
        LivingEntity livingEntity = getEntity();
        if (!ignoreBlock) {
            Block block = findBlock(range, raySize, true);
            if (block != null) {
                range = new ImmutableVector(block.getLocation().toCenterLocation()).distance(getCenterLocation());
            }
        }
        return (LivingEntity) new RayCollider(livingEntity, range, raySize).getEntity(entity -> entity instanceof LivingEntity target && target != livingEntity && predicate.test(target)).orElse(null);
    }

    /**
     * Attempt to find a possible block source that matches the given predicate.
     *
     * @param range        the max range to check
     * @param ignoreBlocks – true to scan through blocks
     * @return the source Entity if one was found, null otherwise
     */
    default @Nullable Entity getTargetEntity(int range, boolean ignoreBlocks) {
        LivingEntity entity = getEntity();
        return entity.getTargetEntity(range, ignoreBlocks);
    }

    default ImmutableVector getEyeLocation() {
        return new ImmutableVector(getEntity().getEyeLocation());
    }

    default ImmutableVector getDirection() {
        return new ImmutableVector(getEntity().getEyeLocation().getDirection());
    }
}
