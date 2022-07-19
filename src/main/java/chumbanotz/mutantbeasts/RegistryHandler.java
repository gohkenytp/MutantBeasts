package chumbanotz.mutantbeasts;

import chumbanotz.mutantbeasts.block.MBBlocks;
import chumbanotz.mutantbeasts.block.MBSkullBlock;
import chumbanotz.mutantbeasts.block.MBWallSkullBlock;
import chumbanotz.mutantbeasts.item.*;
import chumbanotz.mutantbeasts.particles.MBParticleTypes;
import com.google.common.collect.UnmodifiableIterator;
import net.minecraft.block.SkullBlock;
import net.minecraft.block.material.Material;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.entity.EntityClassification;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.HashMap;
import java.util.Map;

@EventBusSubscriber(modid = "mutantbeasts", bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistryHandler {
	public static Map<EntityType<?>, Item> spawnEggs = new HashMap<>();

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		Block block;
		event.getRegistry().registerAll((IForgeRegistry[])new Block[] { block = (Block)(new MBSkullBlock((SkullBlock.Type)MBSkullBlock.Types.MUTANT_SKELETON, BlockBehaviour.Properties.of(Material.DECORATION).strength(1.0F))).setRegistryName("mutantbeasts", "mutant_skeleton_skull"), (Block)(new MBWallSkullBlock((SkullBlock.Type)MBSkullBlock.Types.MUTANT_SKELETON,
				BlockBehaviour.Properties.copy((BlockBehaviour)block).dropsLike(block))).setRegistryName("mutantbeasts", "mutant_skeleton_wall_skull") });
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll((IForgeRegistry[])new Item[] {
				(Item)(new ChemicalXItem(
						defaultProperty().stacksTo(1).rarity(Rarity.EPIC))).setRegistryName("mutantbeasts", "chemical_x"), (Item)(new Item(
				defaultProperty().stacksTo(1))).setRegistryName("mutantbeasts", "creeper_minion_tracker"), (Item)(new CreeperShardItem(
				defaultProperty().durability(32).rarity(Rarity.UNCOMMON))).setRegistryName("mutantbeasts", "creeper_shard"), (Item)(new EndersoulHandItem(
				defaultProperty().durability(240).rarity(Rarity.EPIC).setNoRepair(() -> chumbanotz.mutantbeasts.client.MBItemStackTileEntityRenderer::new))).setRegistryName("mutantbeasts", "endersoul_hand"), (Item)(new HulkHammerItem(
				defaultProperty().durability(64).rarity(Rarity.UNCOMMON))).setRegistryName("mutantbeasts", "hulk_hammer"), (Item)(new Item(
				defaultProperty())).setRegistryName("mutantbeasts", "mutant_skeleton_arms"), (Item)(new Item(
				defaultProperty())).setRegistryName("mutantbeasts", "mutant_skeleton_limb"), (Item)(new Item(
				defaultProperty())).setRegistryName("mutantbeasts", "mutant_skeleton_pelvis"), (Item)(new Item(
				defaultProperty())).setRegistryName("mutantbeasts", "mutant_skeleton_rib"), (Item)(new Item(
				defaultProperty())).setRegistryName("mutantbeasts", "mutant_skeleton_rib_cage"),
				(Item)(new Item(
						defaultProperty())).setRegistryName("mutantbeasts", "mutant_skeleton_shoulder_pad"), (Item)(new ArmorBlockItem((IArmorMaterial)MBArmorMaterial.MUTANT_SKELETON, MBBlocks.MUTANT_SKELETON_SKULL, MBBlocks.MUTANT_SKELETON_WALL_SKULL,
				defaultProperty().rarity(Rarity.UNCOMMON))).setRegistryName("mutantbeasts", "mutant_skeleton_skull"), (Item)(new SkeletonArmorItem((IArmorMaterial)MBArmorMaterial.MUTANT_SKELETON, EquipmentSlotType.CHEST,
				defaultProperty())).setRegistryName("mutantbeasts", "mutant_skeleton_chestplate"), (Item)(new SkeletonArmorItem((IArmorMaterial)MBArmorMaterial.MUTANT_SKELETON, EquipmentSlotType.LEGS,
				defaultProperty())).setRegistryName("mutantbeasts", "mutant_skeleton_leggings"), (Item)(new SkeletonArmorItem((IArmorMaterial)MBArmorMaterial.MUTANT_SKELETON, EquipmentSlotType.FEET,
				defaultProperty())).setRegistryName("mutantbeasts", "mutant_skeleton_boots") });
		build("creeper_minion", EntityType.Builder.of(chumbanotz.mutantbeasts.entity.CreeperMinionEntity::new, EntityClassification.MISC).func_220321_a(0.3F, 0.85F), 894731, 12040119);
		build("mutant_creeper", EntityType.Builder.of(chumbanotz.mutantbeasts.entity.mutant.MutantCreeperEntity::new, EntityClassification.MONSTER).func_220321_a(1.99F, 2.8F), 5349438, 11013646);
		build("mutant_enderman", EntityType.Builder.of(chumbanotz.mutantbeasts.entity.mutant.MutantEndermanEntity::new, EntityClassification.MONSTER).setCustomClientFactory(chumbanotz.mutantbeasts.entity.mutant.MutantEndermanEntity::new).func_220321_a(1.2F, 4.2F), 1447446, 8860812);
		build("mutant_skeleton", EntityType.Builder.of(chumbanotz.mutantbeasts.entity.mutant.MutantSkeletonEntity::new, EntityClassification.MONSTER).setCustomClientFactory(chumbanotz.mutantbeasts.entity.mutant.MutantSkeletonEntity::new).func_220321_a(1.2F, 3.6F), 12698049, 6310217);
		build("mutant_snow_golem", EntityType.Builder.of(chumbanotz.mutantbeasts.entity.mutant.MutantSnowGolemEntity::new, EntityClassification.MISC).func_220321_a(1.15F, 2.3F), 15073279, 16753434);
		build("mutant_zombie", EntityType.Builder.of(chumbanotz.mutantbeasts.entity.mutant.MutantZombieEntity::new, EntityClassification.MONSTER).setCustomClientFactory(chumbanotz.mutantbeasts.entity.mutant.MutantZombieEntity::new).func_220321_a(1.8F, 3.2F), 7969893, 44975);
		build("spider_pig", EntityType.Builder.of(chumbanotz.mutantbeasts.entity.mutant.SpiderPigEntity::new, EntityClassification.CREATURE).func_220321_a(1.4F, 0.9F), 3419431, 15771042);
		for (Item spawnEggItem : spawnEggs.values())
			event.getRegistry().register((IForgeRegistry)spawnEggItem);
	}

	@SubscribeEvent
	public static void registerEntityTypes(RegistryEvent.Register<EntityType<?>> event) {
		for (EntityType<?> entityType : spawnEggs.keySet())
			event.getRegistry().register((IForgeRegistry)entityType);
		event.getRegistry().registerAll((IForgeRegistry[])new EntityType[] { build("body_part", EntityType.Builder.of(chumbanotz.mutantbeasts.entity.BodyPartEntity::new, EntityClassification.MISC).setCustomClientFactory(chumbanotz.mutantbeasts.entity.BodyPartEntity::new).setTrackingRange(4).setUpdateInterval(10).func_220321_a(0.7F, 0.7F)),
				build("chemical_x", EntityType.Builder.of(chumbanotz.mutantbeasts.entity.projectile.ChemicalXEntity::new, EntityClassification.MISC).setCustomClientFactory(chumbanotz.mutantbeasts.entity.projectile.ChemicalXEntity::new).setTrackingRange(10).setUpdateInterval(10).func_220321_a(0.25F, 0.25F)),
				build("creeper_minion_egg", EntityType.Builder.of(chumbanotz.mutantbeasts.entity.CreeperMinionEggEntity::new, EntityClassification.MISC).setCustomClientFactory(chumbanotz.mutantbeasts.entity.CreeperMinionEggEntity::new).setTrackingRange(10).setUpdateInterval(20).func_220321_a(0.5625F, 0.75F)),
				build("endersoul_clone", EntityType.Builder.of(chumbanotz.mutantbeasts.entity.EndersoulCloneEntity::new, EntityClassification.MONSTER).func_220321_a(0.6F, 2.9F)),
				build("endersoul_fragment", EntityType.Builder.of(chumbanotz.mutantbeasts.entity.EndersoulFragmentEntity::new, EntityClassification.MISC).setCustomClientFactory(chumbanotz.mutantbeasts.entity.EndersoulFragmentEntity::new).setTrackingRange(4).setUpdateInterval(10).func_220321_a(0.75F, 0.75F)),
				build("mutant_arrow", EntityType.Builder.of(chumbanotz.mutantbeasts.entity.projectile.MutantArrowEntity::new, EntityClassification.MISC).setCustomClientFactory(chumbanotz.mutantbeasts.entity.projectile.MutantArrowEntity::new).setShouldReceiveVelocityUpdates(false).func_200706_c()),
				build("skull_spirit", EntityType.Builder.of(chumbanotz.mutantbeasts.entity.SkullSpiritEntity::new, EntityClassification.MISC).setCustomClientFactory(chumbanotz.mutantbeasts.entity.SkullSpiritEntity::new).setTrackingRange(10).setUpdateInterval(20).setShouldReceiveVelocityUpdates(false).func_220321_a(0.1F, 0.1F)),
				build("throwable_block", EntityType.Builder.of(chumbanotz.mutantbeasts.entity.projectile.ThrowableBlockEntity::new, EntityClassification.MISC).setCustomClientFactory(chumbanotz.mutantbeasts.entity.projectile.ThrowableBlockEntity::new).setTrackingRange(4).setUpdateInterval(100).func_220321_a(1.0F, 1.0F)) });
	}

	private static <T extends Mob> EntityType<T> build(String name, EntityType.Builder<T> builder, int eggPrimaryColor, int eggSecondaryColor) {
		EntityType<T> entityType = (EntityType)build(name, (EntityType.Builder)builder);
		spawnEggs.put(entityType, (new SpawnEggItem(entityType, eggPrimaryColor, eggSecondaryColor, defaultProperty())).setRegistryName("mutantbeasts", name + "_spawn_egg"));
		return entityType;
	}

	private static <T extends Entity> EntityType<T> build(String name, EntityType.Builder<T> builder) {
		ResourceLocation registryName = MutantBeasts.prefix(name);
		EntityType<T> entityType = builder.build(registryName.toString());
		entityType.setRegistryName(registryName);
		return entityType;
	}

	@SubscribeEvent
	public static void registerBlockEntityTypes(RegistryEvent.Register<BlockEntityType<?>> event) {
		event.getRegistry().register(
				BlockEntityType.Builder.of(chumbanotz.mutantbeasts.tileentity.MBSkullTileEntity::new, new Block[] { MBBlocks.MUTANT_SKELETON_SKULL, MBBlocks.MUTANT_SKELETON_WALL_SKULL }).func_206865_a(null).setRegistryName("mutantbeasts", "skull"));
	}

	@SubscribeEvent
	public static void registerParticleTypes(RegistryEvent.Register<ParticleType<?>> event) {
		event.getRegistry().registerAll((IForgeRegistry[])new ParticleType[] { (ParticleType)(new SimpleParticleType(false))
				.setRegistryName("mutantbeasts", "endersoul"), (ParticleType)(new SimpleParticleType(true))
				.setRegistryName("mutantbeasts", "skull_spirit") });
	}

	@SubscribeEvent
	public static void registerSoundEvents(RegistryEvent.Register<SoundEvent> event) {
		event.getRegistry().registerAll((IForgeRegistry[])new SoundEvent[] {
				createSoundEvent("entity.creeper_minion.ambient"),
				createSoundEvent("entity.creeper_minion.death"),
				createSoundEvent("entity.creeper_minion.hurt"),
				createSoundEvent("entity.creeper_minion.primed"),
				createSoundEvent("entity.creeper_minion_egg.hatch"),
				createSoundEvent("entity.endersoul_clone.death"),
				createSoundEvent("entity.endersoul_clone.teleport"),
				createSoundEvent("entity.endersoul_fragment.explode"),
				createSoundEvent("entity.mutant_creeper.ambient"),
				createSoundEvent("entity.mutant_creeper.charge"),
				createSoundEvent("entity.mutant_creeper.death"),
				createSoundEvent("entity.mutant_creeper.hurt"),
				createSoundEvent("entity.mutant_creeper.primed"),
				createSoundEvent("entity.mutant_enderman.ambient"),
				createSoundEvent("entity.mutant_enderman.death"),
				createSoundEvent("entity.mutant_enderman.hurt"),
				createSoundEvent("entity.mutant_enderman.morph"),
				createSoundEvent("entity.mutant_enderman.scream"),
				createSoundEvent("entity.mutant_enderman.stare"),
				createSoundEvent("entity.mutant_enderman.teleport"),
				createSoundEvent("entity.mutant_skeleton.ambient"),
				createSoundEvent("entity.mutant_skeleton.death"),
				createSoundEvent("entity.mutant_skeleton.hurt"),
				createSoundEvent("entity.mutant_skeleton.step"),
				createSoundEvent("entity.mutant_snow_golem.death"),
				createSoundEvent("entity.mutant_snow_golem.hurt"),
				createSoundEvent("entity.mutant_zombie.ambient"),
				createSoundEvent("entity.mutant_zombie.attack"),
				createSoundEvent("entity.mutant_zombie.death"),
				createSoundEvent("entity.mutant_zombie.grunt"),
				createSoundEvent("entity.mutant_zombie.hurt"),
				createSoundEvent("entity.mutant_zombie.roar"),
				createSoundEvent("entity.spider_pig.ambient"),
				createSoundEvent("entity.spider_pig.death"),
				createSoundEvent("entity.spider_pig.hurt") });
	}

	private static SoundEvent createSoundEvent(String name) {
		ResourceLocation registryName = MutantBeasts.prefix(name);
		return (SoundEvent)(new SoundEvent(registryName)).setRegistryName(registryName);
	}

	@SubscribeEvent
	public static void remapParticleTypes(RegistryEvent.MissingMappings<ParticleType<?>> event) {
		for (UnmodifiableIterator<RegistryEvent.MissingMappings.Mapping<ParticleType<?>>> unmodifiableIterator = event.getMappings().iterator(); unmodifiableIterator.hasNext(); ) {
			RegistryEvent.MissingMappings.Mapping<ParticleType<?>> mapping = unmodifiableIterator.next();
			if (mapping.key.toString().equals("mutantbeasts:large_portal"))
				mapping.remap((IForgeRegistry)MBParticleTypes.ENDERSOUL);
		}
	}

	public static void registerDispenseBehavior() {
		DefaultDispenseItemBehavior defaultdispenseitembehavior = new DefaultDispenseItemBehavior() {
			public ItemStack execute(BlockSource source, ItemStack stack) {
				Direction direction = (Direction)source.getBlockState().getValue((Property) DispenserBlock.FACING);
				EntityType<?> entitytype = ((SpawnEggItem)stack.getItem()).getType(stack.getTag());
				entitytype.spawn(source.getLevel(), stack, null, source.getPos().relative(direction), MobSpawnType.DISPENSER, (direction != Direction.UP), false);
				stack.shrink(1);
				return stack;
			}
		};
		for (Item spawnEggItem : spawnEggs.values())
			DispenserBlock.registerBehavior((ItemLike)spawnEggItem, (DispenseItemBehavior)defaultdispenseitembehavior);
		spawnEggs.clear();
		spawnEggs = null;
	}

	private static Item.Properties defaultProperty() {
		return (new Item.Properties()).tab(MutantBeasts.ITEM_GROUP);
	}
}
