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

import dev.tophatcat.projecticbp.registry.BallisticMemoryTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.Unit;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.AvoidEntity;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.InvalidateAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRandomLookTarget;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyPlayersSensor;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

//TODO If fed raw fish, will not attack nearby players or agro for 5m (real world time)
public class BallisticPenguin extends Monster implements GeoEntity, SmartBrainOwner<BallisticPenguin> {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("idle");
    private static final RawAnimation WALK = RawAnimation.begin().thenLoop("walk");
    private static final RawAnimation TRANSITION_TO_ATTACK = RawAnimation.begin().thenPlay("transition_to_attack");
    private static final RawAnimation ATTACK = RawAnimation.begin().thenPlay("attack");

    private boolean IS_ATTACKING;
    // Stay friendly for around 5 or 10 minutes.
    private static final UniformInt PERSISTENT_FRIENDLY_TIME = TimeUtil.rangeOfSeconds(300, 600);

    public BallisticPenguin(EntityType<? extends BallisticPenguin> type, Level level) {
        super(type, level);
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
            if (item.is(ItemTags.FISHES) && !BrainUtils.hasMemory(this, BallisticMemoryTypes.EATEN_FISH.get())) {
                if (!player.getAbilities().instabuild) {
                    item.shrink(1);
                }
                BrainUtils.setForgettableMemory(this, BallisticMemoryTypes.EATEN_FISH.get(), Unit.INSTANCE, PERSISTENT_FRIENDLY_TIME.sample(this.random) * 20); // Set random time, in ticks, so multiplied by 20
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

    // Brain stuff start
    @Override
    protected void customServerAiStep() {
        tickBrain(this); // Make brain tick on the server
    }

    @Override
    protected Brain.@NotNull Provider<?> brainProvider() {
        return new SmartBrainProvider<>(this); // Replace Brain Provider with SmartBrainLib version
    }

    @Override
    public List<? extends ExtendedSensor<? extends BallisticPenguin>> getSensors() {
        return List.of( // Add Sensors to scan for stuff we find interesting
            new NearbyPlayersSensor<>(),
            new NearbyLivingEntitySensor<>(),
            new HurtBySensor<BallisticPenguin>() // Keep track of attacks by players
                .setPredicate((damageSource, mob) -> damageSource.getEntity() instanceof Player)
        );
    }

    @Override
    public BrainActivityGroup<? extends BallisticPenguin> getCoreTasks() {
        return BrainActivityGroup.coreTasks( // High priority tasks we always want to be doing
            new SetAttackTarget<BallisticPenguin>(false) // If there is no attack target, set an attack target
                .targetFinder(penguin -> BrainUtils.getMemory(penguin, MemoryModuleType.NEAREST_VISIBLE_PLAYER)) // Change what memory we get the attack target from
                .startCondition(BallisticPenguin::isAngry), // Only set an attack target if isAngry()
            new LookAtTarget<>(), // If we have a look target, look at target
            new MoveToWalkTarget<>() // If we have a move target, move to it
        );
    }

    @Override
    public BrainActivityGroup<? extends BallisticPenguin> getFightTasks() {
        return BrainActivityGroup.fightTasks( // Combat tasks
            new InvalidateAttackTarget<>() // Make sure the target is still valid, and we haven't been failing to path to it for too long
            // Here is where we would launch at the enemy
        );
    }

    @SuppressWarnings("unchecked")
    @Override
    public BrainActivityGroup<? extends BallisticPenguin> getIdleTasks() {
        return BrainActivityGroup.idleTasks( // Fallback tasks
            new FirstApplicableBehaviour<>( // Try these in order, until you find one to do
                new AvoidEntity<>() // If there is a polar bear nearby, run away
                    .avoiding(e->e.getType() == EntityType.POLAR_BEAR) // Polar bears only
                    .speedModifier(1.8f) // Run away at this speed modifier
                    .noCloserThan(7f) // How close can we get before running away
                    .stopCaringAfter(12f), // How far to get before stopping
                new OneRandomBehaviour<>( // Else, do one of these
                    new SetRandomLookTarget<>(), // Look around randomly
                    new SetRandomWalkTarget<>(), // Set a random nearby walk target
                    new Idle<>().runFor(e -> e.getRandom().nextInt(20, 40)) // Do nothing, for 1 to 2 seconds
                )
            )
        );
    }

    public boolean isAngry() {
        return !BrainUtils.hasMemory(this, BallisticMemoryTypes.EATEN_FISH.get());
    }
}
