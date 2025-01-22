/*
 * Oh look, a cute friendly penguin... OH! LOOK, A PENGUIN CHARGING AT US!!! Project Intercontinental Ballistic Penguin!
 * Copyright (C) KiriCattus 2013 - 2025
 * https://github.com/kiris-mods/project-icbp/blob/dev/LICENSE.md
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301
 * USA
 */
package dev.tophatcat.projecticbp.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

//TODO If fed raw fish, will not attack nearby players or agro for 5m (real world time)
public class BallisticPenguin extends Monster implements GeoEntity {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("idle");
    private static final RawAnimation WALK = RawAnimation.begin().thenLoop("walk");
    private static final RawAnimation TRANSITION_TO_ATTACK = RawAnimation.begin().thenPlay("transition_to_attack");
    private static final RawAnimation ATTACK = RawAnimation.begin().thenPlay("attack");

    private boolean IS_ATTACKING;
    // Stay friendly for around 5 or 10 minutes.
    private static final UniformInt PERSISTENT_FRIENDLY_TIME = TimeUtil.rangeOfSeconds(300, 600);
    // The remaining time the Penguin will be friendly for.
    private int remainingPersistentFriendlyTime;
    private boolean IS_ANGRY;

    public BallisticPenguin(EntityType<? extends BallisticPenguin> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        //TODO Maybe move to brains if anyone wants to help?
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(2, new RandomStrollGoal(this, 0.8F));
        goalSelector.addGoal(3, new AvoidEntityGoal<>(this, PolarBear.class, 6.0F, 1.0, 1.2));
        goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 30.0)
            .add(Attributes.MOVEMENT_SPEED, 0.25)
            .add(Attributes.FOLLOW_RANGE, 20.0);
    }

    public static boolean checkSpawnRules(EntityType<? extends BallisticPenguin> type, LevelAccessor accessor,
                                          MobSpawnType spawnReason, BlockPos pos, RandomSource random) {
        return accessor.getDifficulty() == Difficulty.PEACEFUL;
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float damageAmount) {
        if (isInvulnerableTo(source)) {
            return false;
        } else {
            if (source.getEntity() instanceof Player) {
                //resetHappyTimer();
                //transformIntoAttackMode();
                //attackPlayer();
            }
        }
        return super.hurt(source, damageAmount);
    }

    @NotNull
    @Override
    //TODO Come back here!! Need to finish this!
    //When fed fish, don't attack players for about 3 to 5 min afterwards, only accept one fish at a time.
    public InteractionResult interactAt(Player player, @NotNull Vec3 hitPos, @NotNull InteractionHand hand) {
        ItemStack item = player.getItemInHand(hand);
        if (hand == InteractionHand.MAIN_HAND) {
            if (item.is(ItemTags.FISHES) && IS_ANGRY) {
                if (player.getAbilities().instabuild) {
                    item.shrink(1);
                    IS_ANGRY = false;
                }
            }
        }
        return super.interactAt(player, hitPos, hand);
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    //Geckolib stuff start
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(walkAndIdleController(this));
        //controllers.add(attackController(this));
    }

    private <T extends BallisticPenguin & GeoAnimatable> AnimationController<T> walkAndIdleController(T entity) {
        return new AnimationController<T>(entity, "Walk/Idle", 4, state -> {
            if (state.isMoving() && !IS_ATTACKING) {
                return state.setAndContinue(WALK);
            }
            else if (!IS_ATTACKING) {
                return state.setAndContinue(IDLE);
            }
            return null;
        });
    }

    private <T extends BallisticPenguin & GeoAnimatable> AnimationController<T> attackController(T entity) {
        return new AnimationController<T>(entity, "attack", 0, state -> {
            if (IS_ATTACKING) {
                return state.setAndContinue(ATTACK);
            }
            return null;
        });
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
