package com.prog.screen;

import com.prog.itemOrBlock.data.FlexibleCraftingData;
import com.prog.utils.InventoryUtils;
import com.prog.utils.ScreenUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.CraftingResultSlot;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class FlexibleCraftingScreenHandler extends AbstractRecipeScreenHandler<CraftingInventory> {
    public final Block block;
    public final List<RecipeType<? extends Recipe<CraftingInventory>>> recipeTypes;
    public final int width;
    public final int height;
    public CraftingInventory input;
    public CraftingResultInventory result = new CraftingResultInventory();
    public ScreenHandlerContext context;
    public PlayerEntity player;


    public FlexibleCraftingScreenHandler(FlexibleCraftingData flexibleCraftingData, int syncId, PlayerInventory playerInventory) {
        this(flexibleCraftingData.block.get(), flexibleCraftingData.screenHandlerType.get(), flexibleCraftingData.supportedRecipeTypes, flexibleCraftingData.width, flexibleCraftingData.height, syncId, playerInventory, ScreenHandlerContext.EMPTY);
    }

    public FlexibleCraftingScreenHandler(Block block, ScreenHandlerType<?> screenHandlerType, List<RecipeType<? extends Recipe<CraftingInventory>>> recipeTypes, int width, int height, int syncId, PlayerInventory playerInventory) {
        this(block, screenHandlerType, recipeTypes, width, height, syncId, playerInventory, ScreenHandlerContext.EMPTY);
    }

    public FlexibleCraftingScreenHandler(Block block, ScreenHandlerType<?> screenHandlerType, List<RecipeType<? extends Recipe<CraftingInventory>>> recipeTypes, int width, int height, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(screenHandlerType, syncId);
        this.block = block;
        this.recipeTypes = recipeTypes;
        this.width = width;
        this.height = height;
        this.input = new CraftingInventory(this, width, height);
        this.context = context;
        this.player = playerInventory.player;
        int slotSize = 18;
        int halfSlotSize = 9;
        this.addSlot(new CraftingResultSlot(playerInventory.player, this.input, this.result, 0, 57 + width * halfSlotSize + 42, 17 + height * halfSlotSize - halfSlotSize));

        for (int heightIndex = 0; heightIndex < height; heightIndex++) {
            for (int widthIndex = 0; widthIndex < width; widthIndex++) {
                this.addSlot(new Slot(this.input, widthIndex + heightIndex * width, 57 - width * halfSlotSize + widthIndex * slotSize, 17 + heightIndex * slotSize));
            }
        }

        ScreenUtils.addPlayerInventory(this::addSlot, playerInventory, 8, 16 + 14 + height * slotSize);
    }

    public static ScreenHandlerType.Factory<FlexibleCraftingScreenHandler> factory(Block block, Supplier<ScreenHandlerType<?>> screenHandlerTypeSupplier, List<RecipeType<? extends Recipe<CraftingInventory>>> recipeTypes, int width, int height) {
        return (syncId, playerInventory) -> new FlexibleCraftingScreenHandler(block, screenHandlerTypeSupplier.get(), recipeTypes, width, height, syncId, playerInventory);
    }

    public static ScreenHandlerType.Factory<FlexibleCraftingScreenHandler> factory(FlexibleCraftingData data) {
        return factory(data.block.get(), data.screenHandlerType, data.supportedRecipeTypes, data.width, data.height);
    }

    protected void updateResult(
            ScreenHandler handler, World world, PlayerEntity player, CraftingInventory craftingInventory, CraftingResultInventory resultInventory
    ) {
        if (world.isClient) return;

        ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) player;
        ItemStack itemStack = ItemStack.EMPTY;

        for (RecipeType<? extends Recipe<CraftingInventory>> recipeType : recipeTypes) {
            Optional<? extends Recipe<CraftingInventory>> optionalCraftingRecipe = world.getServer().getRecipeManager().getFirstMatch(recipeType, craftingInventory, world);
           if (optionalCraftingRecipe.isPresent()) {
               Recipe<CraftingInventory> craftingRecipe = (Recipe<CraftingInventory>) optionalCraftingRecipe.get();
               if (resultInventory.shouldCraftRecipe(world, serverPlayerEntity, craftingRecipe)) {
                   itemStack = craftingRecipe.craft(craftingInventory);
               }
           }
       }

        resultInventory.setStack(0, itemStack);
        handler.setPreviousTrackedSlot(0, itemStack);
        serverPlayerEntity.networkHandler.sendPacket(new ScreenHandlerSlotUpdateS2CPacket(handler.syncId, handler.nextRevision(), 0, itemStack));
    }

    @Override
    public void onContentChanged(Inventory inventory) {
        this.context.run((world, pos) -> updateResult(this, world, this.player, this.input, this.result));
    }

    @Override
    public void populateRecipeFinder(RecipeMatcher finder) {
        this.input.provideRecipeInputs(finder);
    }

    @Override
    public void clearCraftingSlots() {
        this.input.clear();
        this.result.clear();
    }

    @Override
    public boolean matches(Recipe<? super CraftingInventory> recipe) {
        return recipe.matches(this.input, this.player.world);
    }

    @Override
    public void close(PlayerEntity player) {
        super.close(player);
        this.context.run((world, pos) -> this.dropInventory(player, this.input));
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return canUse(this.context, player, block);
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        int resultIndex = getCraftingResultSlotIndex();

        int craftingSlotsCount = getCraftingSlotCount();
        int inventoryRows = InventoryUtils.inventoryRows;
        int inventoryColumns = InventoryUtils.inventoryColumns;
        int inventoryCount = InventoryUtils.getInventorySlotsCount();
        int allInventoryLikeSlotsCount = InventoryUtils.getAllSlotsCount();
        int totalSlotsCount = craftingSlotsCount + allInventoryLikeSlotsCount;
        int totalSlotsWithoutHotbarCount = totalSlotsCount - InventoryUtils.hotbarColumns;

        if (slot != null && slot.hasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            if (index == resultIndex) {
                this.context.run((world, pos) -> itemStack2.getItem().onCraft(itemStack2, world, player));
                if (!this.insertItem(itemStack2, craftingSlotsCount, totalSlotsCount, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickTransfer(itemStack2, itemStack);
            } else if (index >= craftingSlotsCount && index < totalSlotsCount) {
                if (!this.insertItem(itemStack2, 1, craftingSlotsCount, false)) {
                    if (index < totalSlotsWithoutHotbarCount) {
                        if (!this.insertItem(itemStack2, totalSlotsWithoutHotbarCount, totalSlotsCount, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (!this.insertItem(itemStack2, craftingSlotsCount, totalSlotsWithoutHotbarCount, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.insertItem(itemStack2, craftingSlotsCount, totalSlotsCount, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }

            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTakeItem(player, itemStack2);
            if (index == 0) {
                player.dropItem(itemStack2, false);
            }
        }

        return itemStack;
    }

    @Override
    public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
        return slot.inventory != this.result && super.canInsertIntoSlot(stack, slot);
    }

    @Override
    public int getCraftingResultSlotIndex() {
        return 0;
    }

    @Override
    public int getCraftingWidth() {
        return this.input.getWidth();
    }

    @Override
    public int getCraftingHeight() {
        return this.input.getHeight();
    }

    @Override
    public int getCraftingSlotCount() {
        return 1 + height * width;
    }

    @Override
    public RecipeBookCategory getCategory() {
        return RecipeBookCategory.CRAFTING;
    }

    @Override
    public boolean canInsertIntoSlot(int index) {
        return index != this.getCraftingResultSlotIndex();
    }
}
