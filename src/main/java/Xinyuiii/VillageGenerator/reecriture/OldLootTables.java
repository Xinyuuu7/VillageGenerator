package Xinyuiii.VillageGenerator.reecriture;

import com.seedfinding.mcfeature.loot.LootPool;
import com.seedfinding.mcfeature.loot.LootTable;
import com.seedfinding.mcfeature.loot.entry.ItemEntry;
import com.seedfinding.mcfeature.loot.function.SetCountFunction;
import com.seedfinding.mcfeature.loot.item.Items;
import com.seedfinding.mcfeature.loot.roll.UniformRoll;

public class OldLootTables {
    public static final LootTable VILLAGE_BLACKSMITH_CHEST = new LootTable(
            new LootPool(new UniformRoll(3.0F, 8.0F),
                    new ItemEntry(Items.DIAMOND, 3).apply(version -> SetCountFunction.uniform(1.0F, 3.0F)),
                    new ItemEntry(Items.IRON_INGOT, 10).apply(version -> SetCountFunction.uniform(1.0F, 5.0F)),
                    new ItemEntry(Items.GOLD_INGOT, 5).apply(version -> SetCountFunction.uniform(1.0F, 3.0F)),
                    new ItemEntry(Items.BREAD, 15).apply(version -> SetCountFunction.uniform(1.0F, 3.0F)),
                    new ItemEntry(Items.APPLE, 15).apply(version -> SetCountFunction.uniform(1.0F, 3.0F)),
                    new ItemEntry(Items.IRON_PICKAXE, 5),
                    new ItemEntry(Items.IRON_SWORD, 5),
                    new ItemEntry(Items.IRON_CHESTPLATE, 5),
                    new ItemEntry(Items.IRON_HELMET, 5),
                    new ItemEntry(Items.IRON_LEGGINGS, 5),
                    new ItemEntry(Items.IRON_BOOTS, 5),
                    new ItemEntry(Items.OBSIDIAN, 5).apply(version -> SetCountFunction.uniform(3.0F, 7.0F)),
                    new ItemEntry(Items.OAK_SAPLING, 5).apply(version -> SetCountFunction.uniform(3.0F, 7.0F)),
                    new ItemEntry(Items.SADDLE, 3),
                    new ItemEntry(Items.IRON_HORSE_ARMOR),
                    new ItemEntry(Items.GOLDEN_HORSE_ARMOR),
                    new ItemEntry(Items.DIAMOND_HORSE_ARMOR))
    );
}