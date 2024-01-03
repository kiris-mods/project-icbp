package dev.tophatcat.projecticbp.entities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

//TODO If fed raw fish, will not attack nearby players or agro for 5m (real world time)
//TODO Make the actual mob not just set up the framework.
public class BallisticPenguin extends Monster implements GeoAnimatable {

    //private static final UniformInt HAPPY_TIMER = TimeUtil.rangeOfSeconds(138, 300);
    //private static final EntityDataAccessor<Integer> DATA_REMAINING_HAPPY_TIME
    //    = SynchedEntityData.defineId(BallisticPenguin.class, EntityDataSerializers.INT);
    //private static final EntityDataAccessor<Boolean> DATA_IS_HAPPY
    //    = SynchedEntityData.defineId(BallisticPenguin.class, EntityDataSerializers.BOOLEAN);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public BallisticPenguin(EntityType<? extends BallisticPenguin> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        //TODO Come back, set this up and sort out speeds.
        //goalSelector.addGoal(4, new AvoidEntityGoal<>(this, PolarBear.class));
        goalSelector.addGoal(5, new RandomStrollGoal(this, 1.0F));
        goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        goalSelector.addGoal(7, new NearestAttackableTargetGoal<>(this, Player.class, true));

    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 30)
            .add(Attributes.MOVEMENT_SPEED, 0.01);
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.9F;
    }

    @Override
    public boolean hurt(DamageSource source, float damageAmount) {
        if (isInvulnerableTo(source)) {
            return false;
        } else {
            Entity entity = source.getEntity();
            if (entity instanceof Player) {
                //resetHappyTimer();
            }
        }
        return super.hurt(source, damageAmount);
    }

    @NotNull
    @Override
    //TODO Come back here!! Need to finish this!
    //When fed fish, don't attack players for about 3 to 5 min afterwards, only accept one fish at a time.
    public InteractionResult interactAt(Player player, Vec3 hitPos, InteractionHand hand) {
        ItemStack item = player.getItemInHand(hand);
        if (hand == InteractionHand.MAIN_HAND) {
            if (item.is(ItemTags.FISHES)) {//&& !isHappy) {
                if (player.getAbilities().instabuild) {
                    item.shrink(1);
                    //isAngry = false;

                }
            }
        }
        return super.interactAt(player, hitPos, hand);
    }

    //Geckolib stuff start
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public double getTick(Object object) {
        return 0;
    }
    //Geckolib stuff end.

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }


}
